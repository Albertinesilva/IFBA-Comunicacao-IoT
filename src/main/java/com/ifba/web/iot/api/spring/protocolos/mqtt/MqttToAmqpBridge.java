package com.ifba.web.iot.api.spring.protocolos.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.protocolos.rabbitmq.simulation.InMemoryRabbitTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MqttToAmqpBridge {

  @Autowired
  private InMemoryRabbitTemplate rabbitTemplate;

  /**
   * Encaminha os dados publicados via MQTT para a fila "minha-fila" do RabbitMQ
   * simulado.
   *
   * @param data Dados do sensor recebidos do MQTT.
   */
  public void forwardToQueue(SensorData data) {
    String message = String.format("Sensor: %s | Valor: %.2f %s | Timestamp: %s",
        data.getSensor(), data.getValor(), data.getUnidade(), data.getTimestamp());

    log.info("🔄 Encaminhando dados do MQTT para RabbitMQ (fila 'minha-fila')...");
    rabbitTemplate.send("minha-fila", message);
    log.info("✅ Dados do sensor '{}' enviados para a fila RabbitMQ.", data.getSensor());
  }
}
