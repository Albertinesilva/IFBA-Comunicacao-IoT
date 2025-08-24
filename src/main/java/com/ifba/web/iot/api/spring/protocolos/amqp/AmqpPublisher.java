package com.ifba.web.iot.api.spring.protocolos.amqp;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.repository.SensorDataRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Componente responsável por simular o envio de dados de sensores 
 * via protocolo AMQP (como RabbitMQ) no contexto de uma aplicação 
 * de agricultura inteligente.
 */
@Slf4j
@Component
public class AmqpPublisher {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    /**
     * Publica os dados do sensor simulando uma mensagem AMQP.
     * Dependendo do tipo de sensor, uma mensagem personalizada é construída 
     * e exibida no console.
     *
     * @param data Objeto {@link SensorData} contendo as informações do sensor.
     * @return A mensagem formatada enviada via AMQP (exibida no console).
     */
    public String publish(SensorData data) {
        String msg;
        switch (data.getSensor()) {
            case "temperatura":
                msg = "📡 AMQP >> Temperatura registrada no solo: " + data.getValor() + " " + data.getUnidade();
                break;
            case "umidade":
                msg = "📡 AMQP >> Umidade do ar monitorada: " + data.getValor() + " " + data.getUnidade();
                break;
            case "luminosidade":
                msg = "📡 AMQP >> Nível de luz solar captado: " + data.getValor() + " " + data.getUnidade();
                break;
            default:
                msg = "📡 AMQP >> Leitura enviada: " + data.getSensor() + " - " + data.getValor() + " "
                        + data.getUnidade();
        }
        data.setTimestamp(LocalDateTime.now());
        sensorDataRepository.save(data);
        log.info(data.toString() + "\n" + msg);
        return msg;
    }
}
