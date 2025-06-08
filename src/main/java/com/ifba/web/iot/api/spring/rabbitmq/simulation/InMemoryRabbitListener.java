package com.ifba.web.iot.api.spring.rabbitmq.simulation;

import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

/**
 * Simula um listener do RabbitMQ que escuta mensagens de uma fila na memória.
 * 
 * Essa classe consome mensagens da fila "minha-fila" criada pela
 * {@link InMemoryRabbitTemplate},
 * imprime no console e armazena a última mensagem recebida para possível
 * consulta.
 */
@Service
public class InMemoryRabbitListener {

  private final InMemoryRabbitTemplate rabbitTemplate;

  /** Armazena a última mensagem recebida da fila. */
  private volatile String lastReceivedMessage = null;

  /**
   * Construtor que inicia um listener em uma thread separada para verificar
   * mensagens
   * na fila "minha-fila" de forma contínua.
   * 
   * @param rabbitTemplate o template simulado para envio e recebimento de
   *                       mensagens
   */
  public InMemoryRabbitListener(InMemoryRabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;

    Executors.newSingleThreadExecutor().submit(() -> {
      while (true) {
        String msg = rabbitTemplate.receive("minha-fila");
        if (msg != null) {
          System.out.println("Recebi mensagem: " + msg);
          lastReceivedMessage = msg; // guarda para retornar depois
          // Aqui você pode processar a mensagem como quiser
        }
        Thread.sleep(100);
      }
    });
  }

  /**
   * Retorna a última mensagem recebida da fila.
   * 
   * @return a última mensagem recebida ou {@code null} se nenhuma foi processada
   *         ainda
   */
  public String getLastReceivedMessage() {
    return lastReceivedMessage;
  }
}
