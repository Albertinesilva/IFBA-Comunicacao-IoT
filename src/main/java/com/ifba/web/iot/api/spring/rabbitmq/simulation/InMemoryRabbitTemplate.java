package com.ifba.web.iot.api.spring.rabbitmq.simulation;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * Simula um template do RabbitMQ para envio e recebimento de mensagens
 * usando uma estrutura em memória.
 * 
 * Esta classe mantém um mapa de filas nomeadas em memória para permitir
 * testes e simulações sem dependência de um servidor RabbitMQ real.
 */
@Service
public class InMemoryRabbitTemplate {

  /** Mapa que armazena filas nomeadas com mensagens em memória. */
  private Map<String, Queue<String>> queues = new ConcurrentHashMap<>();

  /**
   * Envia uma mensagem para a fila especificada. Caso a fila ainda não exista,
   * ela será criada automaticamente.
   * 
   * @param queueName o nome da fila
   * @param message   a mensagem a ser enviada
   */
  public void send(String queueName, String message) {
    queues.computeIfAbsent(queueName, k -> new ConcurrentLinkedQueue<>()).add(message);
  }

  /**
   * Recebe e remove a próxima mensagem da fila especificada, ou retorna
   * {@code null}
   * se a fila estiver vazia ou não existir.
   * 
   * @param queueName o nome da fila
   * @return a próxima mensagem da fila ou {@code null}
   */
  public String receive(String queueName) {
    Queue<String> queue = queues.get(queueName);
    return (queue != null) ? queue.poll() : null;
  }
}
