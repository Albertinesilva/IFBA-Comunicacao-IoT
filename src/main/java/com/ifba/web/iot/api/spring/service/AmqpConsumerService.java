package com.ifba.web.iot.api.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifba.web.iot.api.spring.model.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por consumir mensagens da fila do RabbitMQ.
 * <p>
 * Este serviço ouve por mensagens em uma fila, converte o payload JSON em uma
 * entidade {@link Alert} e delega a lógica de persistência para um serviço de
 * dados.
 * </p>
 */
@Service
public class AmqpConsumerService {

  private static final Logger logger = LoggerFactory.getLogger(AmqpConsumerService.class);
  private final ObjectMapper objectMapper;
  private final AlertService alertService;

  /**
   * Construtor da classe, injetando as dependências necessárias.
   *
   * @param objectMapper O utilitário do Jackson para conversão de JSON para
   *                     objeto.
   * @param alertService O serviço responsável por salvar os alertas no banco de
   *                     dados.
   */
  public AmqpConsumerService(ObjectMapper objectMapper, AlertService alertService) {
    this.objectMapper = objectMapper;
    this.alertService = alertService;
  }

  /**
   * Recebe e processa as mensagens da fila do RabbitMQ.
   * <p>
   * O método é ativado pelo Spring quando uma nova mensagem é recebida. Ele tenta
   * converter a string de payload em um objeto {@link Alert} e, se for
   * bem-sucedido,
   * chama o {@link AlertService} para salvar o alerta.
   * </p>
   * 
   * @param payload O conteúdo da mensagem recebida em formato JSON como uma
   *                String.
   */
  @RabbitListener(queues = "${amqp.queue}")
  public void receiveMessage(String payload) {
    logger.info(">>>📥 MENSAGEM RECEBIDA DO RABBITMQ! Payload: " + payload);

    try {
      // Converte a string JSON recebida para um objeto Alert.
      Alert newAlert = objectMapper.readValue(payload, Alert.class);

      // Delega a lógica de salvamento para o AlertService.
      alertService.saveAlert(newAlert);
      logger.info("✅ Alerta salvo com sucesso no banco de dados.");
    } catch (Exception e) {
      logger.error("❌ Erro ao converter JSON ou salvar o alerta: {}", e.getMessage());
    }
  }
}
