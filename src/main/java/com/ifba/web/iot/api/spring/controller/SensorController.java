package com.ifba.web.iot.api.spring.controller;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifba.web.iot.api.spring.amqp.AmqpPublisher;
import com.ifba.web.iot.api.spring.controller.dto.view.SensorResponse;
import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.mqtt.MqttPublisher;
import com.ifba.web.iot.api.spring.service.SensorService;

import java.util.List;

/**
 * Controlador REST responsável pelo gerenciamento das leituras de sensores.
 */
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorService service;

    @Autowired
    private AmqpPublisher amqpPublisher;

    @Autowired
    private MqttPublisher mqttPublisher;

    /**
     * Retorna todas as leituras de sensores registradas no sistema.
     *
     * @return Lista de objetos {@link SensorData}.
     */
    @GetMapping
    public List<SensorData> getAll() {
        return service.findAll();
    }

    /**
     * Cria uma nova leitura de sensor, processa possíveis alertas e envia
     * a leitura via protocolo apropriado (AMQP ou MQTT).
     *
     * @param sensorData Dados da leitura do sensor recebidos no corpo da requisição.
     * @return {@link ResponseEntity} com uma mensagem de resposta e os dados registrados.
     */
    @PostMapping
    public ResponseEntity<SensorResponse> create(@RequestBody SensorData sensorData) {
        Triple<String, SensorData, String> result = service.saveSensorData(sensorData);

        String alertMessage = result.getLeft();
        SensorData data = result.getMiddle();
        String protocoloMsg = result.getRight();

        String finalMessage = (alertMessage != null)
                ? alertMessage + "\n" + protocoloMsg
                : "✅ Leitura registrada com sucesso na fazenda.";

        return ResponseEntity.ok(new SensorResponse(finalMessage, data, protocoloMsg));
    }

    /**
     * Envia uma leitura de sensor manualmente via protocolo AMQP.
     *
     * @param sensorData Dados da leitura do sensor a ser enviada.
     * @return {@link ResponseEntity} com confirmação do envio via AMQP.
     */
    @PostMapping("/enviar/amqp")
    public ResponseEntity<String> enviarAmqp(@RequestBody SensorData sensorData) {
        amqpPublisher.publish(sensorData);
        return ResponseEntity.ok("📡 Enviado via AMQP com sucesso.");
    }

    /**
     * Envia uma leitura de sensor manualmente via protocolo MQTT.
     *
     * @param sensorData Dados da leitura do sensor a ser enviada.
     * @return {@link ResponseEntity} com confirmação do envio via MQTT.
     */
    @PostMapping("/enviar/mqtt")
    public ResponseEntity<String> enviarMqtt(@RequestBody SensorData sensorData) {
        mqttPublisher.publish(sensorData);
        return ResponseEntity.ok("📡 Enviado via MQTT com sucesso.");
    }

}
