package com.ifba.web.iot.api.spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
     * Relacionamento de "muitos-para-um" com a entidade Usuario.
     * Múltiplas leituras de sensor podem pertencer a um único usuário.
     * A anotação @JoinColumn define a chave estrangeira 'usuario_id' na tabela
     * 'sensor_data'.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

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
