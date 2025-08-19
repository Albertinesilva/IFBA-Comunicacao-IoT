package com.ifba.web.iot.api.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidade que representa um alerta gerado por anomalia nos dados de um sensor.
 * Os alertas são persistidos no banco de dados para um histórico de eventos.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "alerts")
public class Alert implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Identificador único do alerta.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Tipo do sensor que gerou o alerta (ex: temperatura, umidade).
   */
  private String sensor;

  /**
   * Valor do sensor no momento do alerta.
   */
  private double valor;

  /**
   * Unidade de medida do valor (ex: °C, %).
   */
  private String unidade;

  /**
   * Mensagem descritiva do alerta.
   */
  private String alertMessage;

  /**
   * Momento em que o alerta foi registrado.
   */
  private LocalDateTime timestamp;

  /**
   * Construtor para criar uma instância de alerta com as informações do sensor
   * e a mensagem.
   *
   * @param sensor       O tipo do sensor.
   * @param valor        O valor do sensor no momento do alerta.
   * @param unidade      A unidade de medida do valor.
   * @param alertMessage A mensagem descritiva do alerta.
   */
  public Alert(String sensor, double valor, String unidade, String alertMessage) {
    this.sensor = sensor;
    this.valor = valor;
    this.unidade = unidade;
    this.alertMessage = alertMessage;
    this.timestamp = LocalDateTime.now();
  }
}
