package com.ifba.web.iot.api.spring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifba.web.iot.api.spring.rabbitmq.simulation.InMemoryRabbitListener;
import com.ifba.web.iot.api.spring.rabbitmq.simulation.InMemoryRabbitTemplate;

/**
 * Controlador REST que simula o envio e o recebimento de mensagens
 * via RabbitMQ utilizando uma implementação em memória.
 */
@RestController
@RequestMapping("/api/rabbit")
public class RabbitSimulationController {

  private final InMemoryRabbitTemplate rabbitTemplate;
  private final InMemoryRabbitListener rabbitListener;

  /**
   * Construtor que injeta os componentes de simulação de RabbitMQ.
   *
   * @param rabbitTemplate instância do template que simula envio de mensagens
   * @param rabbitListener instância do listener que simula o recebimento de mensagens
   */
  public RabbitSimulationController(InMemoryRabbitTemplate rabbitTemplate, InMemoryRabbitListener rabbitListener) {
    this.rabbitTemplate = rabbitTemplate;
    this.rabbitListener = rabbitListener;
  }

  /**
   * Endpoint que envia uma mensagem para a fila simulada e retorna a
   * mensagem recebida pelo listener.
   *
   * @param msg a mensagem a ser enviada
   * @return mensagem confirmando o envio e a recepção pelo listener
   * @throws InterruptedException caso ocorra uma interrupção durante o {@code Thread.sleep}
   */
  @PostMapping("/send")
  public String sendMessage(@RequestParam String msg) throws InterruptedException {
    rabbitTemplate.send("minha-fila", msg);

    // Dá um tempinho para o listener "consumir"
    Thread.sleep(200);

    // Retorna a última mensagem recebida pelo listener
    return "Mensagem enviada: " + msg + "\nMensagem recebida pelo listener: " + rabbitListener.getLastReceivedMessage();
  }
}
