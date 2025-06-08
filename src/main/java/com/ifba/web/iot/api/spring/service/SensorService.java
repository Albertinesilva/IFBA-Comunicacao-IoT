package com.ifba.web.iot.api.spring.service;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import com.ifba.web.iot.api.spring.amqp.AmqpPublisher;
import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.mqtt.MqttPublisher;
import com.ifba.web.iot.api.spring.repository.SensorDataRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável pela lógica de negócios relacionada aos dados dos sensores.
 */
@Service
public class SensorService {

    private final SensorDataRepository repository;
    private final MqttPublisher mqttPublisher;
    private final AmqpPublisher amqpPublisher;

    /**
     * Construtor da classe SensorService.
     *
     * @param repository     Repositório para persistência dos dados dos sensores.
     * @param mqttPublisher  Publicador MQTT para envio de dados.
     * @param amqpPublisher  Publicador AMQP para envio de dados.
     */
    public SensorService(SensorDataRepository repository, MqttPublisher mqttPublisher, AmqpPublisher amqpPublisher) {
        this.repository = repository;
        this.mqttPublisher = mqttPublisher;
        this.amqpPublisher = amqpPublisher;
    }

    /**
     * Retorna todos os registros de dados dos sensores.
     *
     * @return Lista de {@link SensorData}
     */
    public List<SensorData> findAll() {
        return repository.findAll();
    }

    /**
     * Salva os dados de um sensor, define unidade de medida conforme o tipo de sensor,
     * aplica lógica de alerta para temperatura elevada e publica os dados via MQTT ou AMQP.
     *
     * @param sensorData Dados do sensor a serem salvos.
     * @return Triple contendo:
     *         - Mensagem de alerta (caso haja),
     *         - Dados salvos,
     *         - Mensagem de protocolo (retorno do publicador).
     */
    public Triple<String, SensorData, String> saveSensorData(SensorData sensorData) {
        String sensor = sensorData.getSensor();
        double valor = sensorData.getValor();
        sensorData.setTimestamp(LocalDateTime.now());

        if ("temperatura".equals(sensor)) {
            sensorData.setUnidade("C");
        } else if ("umidade".equals(sensor)) {
            sensorData.setUnidade("%");
        } else if ("luminosidade".equals(sensor)) {
            sensorData.setUnidade("lx");
        }

        String alertMessage = null;
        String protocoloMsg = null;

        if ("temperatura".equals(sensor) && valor > 30) {
            alertMessage = "🌡️ Alerta! Temperatura elevada detectada no campo. Verifique as condições da lavoura.";
            System.out.println(alertMessage);
        }

        SensorData saved = repository.save(sensorData);

        if ("temperatura".equals(sensor)) {
            protocoloMsg = mqttPublisher.publish(saved);
        } else if ("umidade".equals(sensor) || "luminosidade".equals(sensor)) {
            protocoloMsg = amqpPublisher.publish(saved);
        }

        return Triple.of(alertMessage, saved, protocoloMsg);
    }

}
