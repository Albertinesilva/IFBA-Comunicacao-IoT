package com.ifba.web.iot.api.spring.controller.dto.view;

import com.ifba.web.iot.api.spring.model.SensorData;

import lombok.Getter;

/**
 * DTO (Data Transfer Object) utilizado para encapsular a resposta
 * da criação de uma nova leitura de sensor.
 */
@Getter
public class SensorView {

  /** Mensagem de retorno para o usuário, incluindo alertas ou confirmações. */
  private String message;

  /** Mensagem específica sobre o protocolo utilizado (AMQP ou MQTT). */
  private String protocolo;

  /** Dados do sensor que foi salvo. */
  private SensorData data;

  /**
   * Construtor completo que permite definir a mensagem principal,
   * os dados do sensor e a mensagem do protocolo.
   *
   * @param message Mensagem de retorno geral.
   * @param data Dados do sensor.
   * @param protocolo Mensagem referente ao protocolo de envio.
   */
  public SensorView(String message, SensorData data, String protocolo) {
    this.message = message;
    this.data = data;
    this.protocolo = protocolo;
  }

}
