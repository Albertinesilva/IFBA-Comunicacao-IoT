package com.ifba.web.iot.api.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por atuar como uma ponte (bridge) entre mensagens MQTT e
 * o RabbitMQ (AMQP).
 * <p>
 * Este serviço escuta as mensagens que chegam em um tópico MQTT, as processa e
 * as encaminha para um exchange do RabbitMQ usando uma chave de roteamento
 * específica.
 * A anotação
 * {@link org.springframework.integration.annotation.ServiceActivator}
 * vincula este serviço a um canal de entrada do Spring Integration.
 * </p>
 */
@Service
public class MqttAmqpBridgeService {

  private static final Logger logger = LoggerFactory.getLogger(MqttAmqpBridgeService.class);

  private final RabbitTemplate rabbitTemplate;

  /**
   * Nome do exchange do RabbitMQ para onde a mensagem será enviada.
   * Injetado a partir das propriedades da aplicação.
   */
  @Value("${amqp.exchange}")
  private String amqpExchange;

  /**
   * Chave de roteamento para o RabbitMQ, utilizada para rotear a mensagem
   * para a fila correta. Injetada a partir das propriedades da aplicação.
   */
  @Value("${amqp.routing.key}")
  private String amqpRoutingKey;

  /**
   * Construtor da classe, injetando o {@link AmqpTemplate}.
   * <p>
   * O {@link AmqpTemplate} é uma interface do Spring que facilita as operações
   * com RabbitMQ, como o envio de mensagens.
   * </p>
   *
   * @param amqpTemplate A instância de {@link AmqpTemplate} fornecida pelo
   *                     Spring.
   */
  public MqttAmqpBridgeService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  /**
   * Este método é ativado sempre que uma nova mensagem chega ao tópico MQTT.
   * Ele lê a mensagem e a redireciona para o RabbitMQ.
   *
   * A anotação @ServiceActivator indica que este método é um consumidor
   * de mensagens, neste caso, do canal de entrada MQTT (mqttInboundChannel).
   *
   * @param message A mensagem recebida do tópico MQTT.
   */
  @ServiceActivator(inputChannel = "mqttInputChannel") // <-- CORREÇÃO AQUI
  public void handleMqttMessage(Message<?> message) {
    try {
      // Extrai o payload (o conteúdo) da mensagem MQTT.
      Object payload = message.getPayload();
      String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);

      logger.info("⚡ MENSAGEM MQTT RECEBIDA do tópico '{}'. Redirecionando para o RabbitMQ...", topic);

      // Envia o payload da mensagem MQTT para o RabbitMQ usando o exchange e a
      // routing key.
      rabbitTemplate.convertAndSend(amqpExchange, amqpRoutingKey, payload.toString());

      logger.info("✅ MENSAGEM REDIRECIONADA com sucesso para o RabbitMQ. Payload: {}", payload);
    } catch (Exception e) {
      logger.error("❌ Erro ao processar mensagem MQTT e enviar para o RabbitMQ: {}", e.getMessage());
    }
  }
}
