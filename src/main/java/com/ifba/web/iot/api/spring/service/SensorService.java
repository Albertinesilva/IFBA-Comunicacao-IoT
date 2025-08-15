package com.ifba.web.iot.api.spring.service;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifba.web.iot.api.spring.amqp.AmqpPublisher;
import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.mqtt.MqttPublisher;
import com.ifba.web.iot.api.spring.mqtt.MqttToAmqpBridge;
import com.ifba.web.iot.api.spring.repository.SensorDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável pelo gerenciamento de dados dos sensores.
 * <p>
 * Fornece métodos para:
 * <ul>
 * <li>Consulta de todos os registros de sensores</li>
 * <li>Persistência de dados de sensores</li>
 * <li>Publicação de dados via MQTT e AMQP</li>
 * <li>Detecção de alertas (ex.: temperatura elevada)</li>
 * </ul>
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorDataRepository repository;
    private final MqttPublisher mqttPublisher;
    private final AmqpPublisher amqpPublisher;
    private final MqttToAmqpBridge mqttToAmqpBridge;

    /**
     * Retorna todos os registros de dados dos sensores.
     *
     * @return Lista de {@link SensorData} contendo todos os registros persistidos.
     */
    @Transactional(readOnly = true)
    public List<SensorData> findAll() {
        return repository.findAll();
    }

    /**
     * Salva os dados de um sensor, define unidade de medida conforme o tipo,
     * verifica alertas (ex.: temperatura elevada) e publica os dados via MQTT ou
     * AMQP.
     *
     * @param sensorData Dados do sensor a serem persistidos.
     * @return {@link Triple} contendo:
     *         <ol>
     *         <li>Mensagem de alerta (caso exista, ou {@code null})</li>
     *         <li>Objeto {@link SensorData} persistido</li>
     *         <li>Mensagem de protocolo retornada pelo publicador (MQTT ou
     *         AMQP)</li>
     *         </ol>
     */
    @Transactional
    public Triple<String, SensorData, String> saveSensorData(SensorData sensorData) {
        log.info("📥 Iniciando o salvamento dos dados do sensor...");

        String sensor = sensorData.getSensor();
        double valor = sensorData.getValor();
        sensorData.setTimestamp(LocalDateTime.now());

        // Define unidade de medida conforme tipo de sensor
        switch (sensor) {
            case "temperatura":
                sensorData.setUnidade("C");
                break;
            case "umidade":
                sensorData.setUnidade("%");
                break;
            case "luminosidade":
                sensorData.setUnidade("lx");
                break;
            default:
                log.warn("⚠️ Tipo de sensor desconhecido: {}", sensor);
        }

        log.debug("📊 Dados recebidos: {}", sensorData);

        String alertMessage = null;
        String protocoloMsg = null;

        // Verificação de alerta para temperatura elevada
        if ("temperatura".equals(sensor) && valor > 30) {
            alertMessage = "🌡️ Alerta! Temperatura elevada detectada no campo. Verifique as condições da lavoura.";
            log.warn("{} Valor: {} {}", alertMessage, valor, sensorData.getUnidade());
        }

        SensorData saved = repository.save(sensorData);
        log.info("💾 Dados do sensor salvos com sucesso. ID: {}", saved.getId());

        // Publicação dos dados conforme tipo do sensor
        if ("temperatura".equals(sensor)) {
            protocoloMsg = mqttPublisher.publish(saved);
            log.info("📡 Dados de temperatura publicados via MQTT:\n{}", saved);
            mqttToAmqpBridge.forwardToQueue(saved);
        } else if ("umidade".equals(sensor) || "luminosidade".equals(sensor)) {
            protocoloMsg = amqpPublisher.publish(saved);
            log.info("📡 Dados publicados via AMQP:\n{}", saved);
        }

        log.info("✅ Finalizado o processo de salvamento e publicação dos dados do sensor.");

        return Triple.of(alertMessage, saved, protocoloMsg);
    }

}
