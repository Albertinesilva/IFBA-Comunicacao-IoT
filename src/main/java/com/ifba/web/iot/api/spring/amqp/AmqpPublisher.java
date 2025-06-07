package com.ifba.web.iot.api.spring.amqp;

import org.springframework.stereotype.Component;

import com.ifba.web.iot.api.spring.model.SensorData;

/**
 * Componente responsável por simular o envio de dados de sensores 
 * via protocolo AMQP (como RabbitMQ) no contexto de uma aplicação 
 * de agricultura inteligente.
 */
@Component
public class AmqpPublisher {

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
                msg = "AMQP >> Temperatura registrada no solo: " + data.getValor() + " " + data.getUnidade();
                break;
            case "umidade":
                msg = "AMQP >> Umidade do ar monitorada: " + data.getValor() + " " + data.getUnidade();
                break;
            case "luminosidade":
                msg = "AMQP >> Nível de luz solar captado: " + data.getValor() + " " + data.getUnidade();
                break;
            default:
                msg = "AMQP >> Leitura enviada: " + data.getSensor() + " - " + data.getValor() + " "
                        + data.getUnidade();
        }

        System.out.println(msg);
        return msg;
    }
}
