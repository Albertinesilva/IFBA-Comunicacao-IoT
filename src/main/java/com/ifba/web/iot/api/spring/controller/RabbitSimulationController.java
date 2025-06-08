package com.ifba.web.iot.api.spring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifba.web.iot.api.spring.rabbitmq.simulation.InMemoryRabbitListener;
import com.ifba.web.iot.api.spring.rabbitmq.simulation.InMemoryRabbitTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST que simula o envio e o recebimento de mensagens
 * via RabbitMQ utilizando uma implementação em memória.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rabbit")
public class RabbitSimulationController {

  private final InMemoryRabbitTemplate rabbitTemplate;
  private final InMemoryRabbitListener rabbitListener;

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

    log.info("📤 Enviando mensagem para a fila 'minha-fila': {}", msg);

    // Dá um tempinho para o listener "consumir"
    Thread.sleep(200);

    String received = rabbitListener.getLastReceivedMessage();
    log.info("📨 Última mensagem processada pelo listener: {}", received);

    // Retorna a última mensagem recebida pelo listener
    return "Mensagem enviada: " + msg + "\nMensagem recebida pelo listener: " + received;
  }

}
