package com.ifba.web.iot.api.spring.mqtt;

import org.springframework.stereotype.Component;

import com.ifba.web.iot.api.spring.model.SensorData;

/**
 * Componente responsável por simular o envio de dados de sensores via protocolo
 * MQTT.
 */
@Component
public class MqttPublisher {

    /**
     * Publica os dados do sensor simulando o envio via MQTT.
     *
     * @param data Objeto {@link SensorData} contendo as informações do sensor a
     *             serem enviadas.
     * @return Mensagem formatada indicando o envio dos dados.
     */
    public String publish(SensorData data) {
        String msg = "MQTT >> Enviando dados de " + data.getSensor() +
                " para o sistema de monitoramento da fazenda: " +
                data.getValor() + " " + data.getUnidade();
        System.out.println(msg);
        return msg;
    }
}