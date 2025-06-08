package com.ifba.web.iot.api.spring.controller.dto;

import com.ifba.web.iot.api.spring.model.SensorData;

/**
 * DTO (Data Transfer Object) utilizado para encapsular a resposta
 * da criação de uma nova leitura de sensor.
 */
public class SensorResponse {

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
  public SensorResponse(String message, SensorData data, String protocolo) {
    this.message = message;
    this.data = data;
    this.protocolo = protocolo;
  }

  /**
   * Construtor alternativo que define apenas a mensagem e os dados do sensor.
   *
   * @param finalMessage Mensagem de retorno geral.
   * @param data Dados do sensor.
   */
  public SensorResponse(String finalMessage, SensorData data) {
    this.message = finalMessage;
    this.data = data;
  }

  /**
   * Retorna a mensagem de retorno para o usuário.
   *
   * @return Mensagem de resposta.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Retorna a mensagem sobre o protocolo utilizado.
   *
   * @return Mensagem do protocolo (AMQP ou MQTT).
   */
  public String getProtocolo() {
    return protocolo;
  }

  /**
   * Retorna os dados do sensor salvos.
   *
   * @return Dados do sensor.
   */
  public SensorData getData() {
    return data;
  }
}
