package com.ifba.web.iot.api.spring.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa os dados capturados por um sensor em um determinado momento.
 */
@Entity
public class SensorData {

    /**
     * Identificador único da leitura do sensor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipo do sensor (ex: temperatura, umidade, luminosidade).
     */
    private String sensor;

    /**
     * Valor registrado pelo sensor.
     */
    private double valor;

    /**
     * Unidade de medida do valor registrado (ex: °C, %, lux).
     */
    private String unidade;

    /**
     * Momento em que a leitura foi realizada.
     */
    private LocalDateTime timestamp;

    /**
     * Construtor padrão necessário para o JPA.
     */
    public SensorData() {
    }

    /**
     * Construtor que inicializa o tipo do sensor e o valor, atribuindo o timestamp atual.
     *
     * @param sensor Tipo do sensor.
     * @param valor  Valor da leitura.
     */
    public SensorData(String sensor, double valor) {
        this.sensor = sensor;
        this.valor = valor;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Retorna o ID da leitura.
     *
     * @return ID da leitura.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID da leitura.
     *
     * @param id Novo ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o tipo do sensor.
     *
     * @return Tipo do sensor.
     */
    public String getSensor() {
        return sensor;
    }

    /**
     * Define o tipo do sensor.
     *
     * @param sensor Tipo do sensor.
     */
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    /**
     * Retorna o valor da leitura do sensor.
     *
     * @return Valor da leitura.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor da leitura do sensor.
     *
     * @param valor Valor da leitura.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Retorna a unidade de medida da leitura.
     *
     * @return Unidade de medida.
     */
    public String getUnidade() {
        return unidade;
    }

    /**
     * Define a unidade de medida da leitura.
     *
     * @param unidade Unidade de medida.
     */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * Retorna o momento em que a leitura foi registrada.
     *
     * @return Timestamp da leitura.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Define o momento da leitura.
     *
     * @param timestamp Novo timestamp.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
