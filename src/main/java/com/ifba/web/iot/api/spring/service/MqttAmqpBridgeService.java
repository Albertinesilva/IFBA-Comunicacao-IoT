package com.ifba.web.iot.api.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class MqttAmqpBridgeService {

  private static final Logger logger = LoggerFactory.getLogger(MqttAmqpBridgeService.class);

  private final AmqpTemplate amqpTemplate;

  @Value("${amqp.exchange}")
  private String amqpExchange;

  @Value("${amqp.routing.key}")
  private String amqpRoutingKey;

  public MqttAmqpBridgeService(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  // Este método é o 'listener'. Ele é ativado sempre que uma mensagem chega no
  // canal mqttInputChannel.
  @ServiceActivator(inputChannel = "mqttInputChannel")
  public void handleMqttMessage(String payload) {
    logger.info("Mensagem MQTT recebida: " + payload);

    // Simula o processamento do dado (aqui você faria a validação, transformação,
    // etc.)
    // E então, envia a mensagem para a fila AMQP.
    logger.info("Encaminhando mensagem para a fila AMQP...");
    amqpTemplate.convertAndSend(amqpExchange, amqpRoutingKey, payload);
    logger.info("Mensagem enviada com sucesso para a fila AMQP.");
  }
}