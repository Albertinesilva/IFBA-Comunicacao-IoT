package com.ifba.web.iot.api.spring.mqtt;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.repository.SensorDataRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Componente responsável por simular o envio de dados de sensores via protocolo
 * MQTT.
 */
@Slf4j
@Component
public class MqttPublisher {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    /**
     * Publica os dados do sensor simulando o envio via MQTT.
     *
     * @param data Objeto {@link SensorData} contendo as informações do sensor a
     *             serem enviadas.
     * @return Mensagem formatada indicando o envio dos dados.
     */
    public String publish(SensorData data) {
        String msg = "📡 MQTT >> Enviando dados de " + data.getSensor() +
                " para o sistema de monitoramento da fazenda: " +
                data.getValor() + " " + data.getUnidade();
        data.setTimestamp(LocalDateTime.now());
        sensorDataRepository.save(data);
        log.info(data.toString() + "\n" + msg);
        return msg;
    }
}