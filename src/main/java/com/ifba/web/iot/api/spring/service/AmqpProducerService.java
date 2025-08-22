package com.ifba.web.iot.api.spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifba.web.iot.api.spring.model.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Serviço responsável por enviar mensagens para a fila do RabbitMQ.
 */
@Component
public class AmqpProducerService {

  private static final Logger logger = LoggerFactory.getLogger(AmqpProducerService.class);

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  // A chave de roteamento (routing key) deve ser a mesma da fila do consumidor.
  private final String queueName;

  /**
   * Construtor da classe, injetando as dependências e a configuração da fila.
   *
   * @param rabbitTemplate O utilitário do Spring para enviar mensagens ao
   *                       RabbitMQ.
   * @param objectMapper   O utilitário do Jackson para converter objetos em JSON.
   * @param queueName      O nome da fila para a qual a mensagem será enviada,
   *                       lido do arquivo de propriedades.
   */
  public AmqpProducerService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper,
      @org.springframework.beans.factory.annotation.Value("${amqp.queue}") String queueName) {
    this.rabbitTemplate = rabbitTemplate;
    this.objectMapper = objectMapper;
    this.queueName = queueName;
  }

  /**
   * Converte um objeto de alerta em JSON e o envia para a fila do RabbitMQ.
   *
   * @param alert O objeto de alerta a ser enviado.
   */
  public void sendAlertMessage(Alert alert) {
    try {
      // Converte o objeto Alert para uma string JSON.
      String jsonPayload = objectMapper.writeValueAsString(alert);

      // Envia a string JSON para a fila especificada.
      rabbitTemplate.convertAndSend(queueName, jsonPayload);

      logger.info("📦 MENSAGEM ENVIADA PARA O RABBITMQ! Payload: " + jsonPayload);
    } catch (JsonProcessingException e) {
      logger.error("❌ Erro ao serializar o objeto Alert para JSON: {}", e.getMessage());
    }
  }
}

/**
 * Componente de teste que envia uma mensagem de teste para a fila do RabbitMQ
 * assim que a aplicação é iniciada.
 */
@Component
class RabbitMqTestRunner implements CommandLineRunner {

  private final AmqpProducerService producerService;

  /**
   * Construtor, injetando o serviço produtor.
   *
   * @param producerService O serviço produtor de mensagens.
   */
  public RabbitMqTestRunner(AmqpProducerService producerService) {
    this.producerService = producerService;
  }

  /**
   * Método executado ao iniciar a aplicação.
   * Cria um objeto Alert com os dados de teste e o envia para a fila.
   *
   * @param args Argumentos da linha de comando.
   * @throws Exception Se ocorrer um erro durante a execução.
   */
  @Override
  public void run(String... args) throws Exception {
    // Cria um novo objeto Alert com os dados de teste fornecidos.
    Alert testAlert = new Alert("temperatura", 32.5, "°C", "Temperatura do reator acima do limite");

    // Envia o alerta para a fila.
    producerService.sendAlertMessage(testAlert);
  }
}
