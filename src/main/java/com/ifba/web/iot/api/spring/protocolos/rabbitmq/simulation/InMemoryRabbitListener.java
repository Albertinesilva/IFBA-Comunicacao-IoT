package com.ifba.web.iot.api.spring.protocolos.rabbitmq.simulation;

import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Simula um listener do RabbitMQ que escuta mensagens de uma fila na memória.
 * 
 * Essa classe consome mensagens da fila "minha-fila" criada pela
 * {@link InMemoryRabbitTemplate},
 * imprime no console e armazena a última mensagem recebida para possível consulta.
 */
@Slf4j
@Service
public class InMemoryRabbitListener {

  private final InMemoryRabbitTemplate rabbitTemplate;

  /** Armazena a última mensagem recebida da fila. */
  private volatile String lastReceivedMessage = null;

  /**
   * Construtor que inicia um listener em uma thread separada para verificar mensagens
   * na fila "minha-fila" de forma contínua.
   * 
   * @param rabbitTemplate o template simulado para envio e recebimento de mensagens
   */
  public InMemoryRabbitListener(InMemoryRabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;

    log.info("📦 InMemoryRabbitListener iniciado. Aguardando mensagens na fila: 'minha-fila'...");

    Executors.newSingleThreadExecutor().submit(() -> {
      while (true) {
        String msg = rabbitTemplate.receive("minha-fila");
        if (msg != null) {
          log.info("📨 Mensagem recebida da fila 'minha-fila': {}", msg);
          lastReceivedMessage = msg; // guarda para retornar depois
          // Aqui pode processar a mensagem como quiser
        }
        Thread.sleep(100);
      }
    });
  }

  /**
   * Retorna a última mensagem recebida da fila.
   * 
   * @return a última mensagem recebida ou {@code null} se nenhuma foi processada ainda
   */
  public String getLastReceivedMessage() {
    return lastReceivedMessage;
  }
}
