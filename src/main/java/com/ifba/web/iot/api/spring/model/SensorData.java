package com.ifba.web.iot.api.spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sensor_data")
public class SensorData implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * Construtor que inicializa o tipo do sensor e o valor, atribuindo o timestamp
     * atual.
     *
     * @param sensor Tipo do sensor.
     * @param valor  Valor da leitura.
     */
    public SensorData(String sensor, double valor) {
        this.sensor = sensor;
        this.valor = valor;
        this.timestamp = LocalDateTime.now();
    }

    public SensorData(String sensor, double temperatura, String unidade) {
        this.sensor = sensor;
        this.valor = temperatura;
        this.unidade = unidade;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "\n--- SensorData ---" +
                "\nID         : " + id +
                "\nSensor     : " + sensor +
                "\nValor      : " + valor +
                "\nUnidade    : " + unidade +
                "\nTimestamp  : " + timestamp +
                "\n-------------------";
    }

}
