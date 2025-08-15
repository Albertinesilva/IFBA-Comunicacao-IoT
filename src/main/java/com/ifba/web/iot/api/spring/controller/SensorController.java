package com.ifba.web.iot.api.spring.controller;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifba.web.iot.api.spring.amqp.AmqpPublisher;
import com.ifba.web.iot.api.spring.controller.dto.view.SensorView;
import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.mqtt.MqttPublisher;
import com.ifba.web.iot.api.spring.service.SensorService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Controlador REST responsável pelo gerenciamento das leituras de sensores.
 */
@Slf4j
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
    public ResponseEntity<SensorView> create(@RequestBody SensorData sensorData) {
        log.info("📥 Recebida solicitação para criação de dados do sensor...");

        Triple<String, SensorData, String> result = service.saveSensorData(sensorData);
        log.info("📌 Tipo: {} | Valor: {} | Unidade (pré-processamento): {}",
                sensorData.getSensor(), sensorData.getValor(), sensorData.getUnidade());
                
        String alertMessage = result.getLeft();
        SensorData data = result.getMiddle();
        String protocoloMsg = result.getRight();

        if (alertMessage != null) {
            log.warn("⚠️ Alerta gerado após análise dos dados: {}", alertMessage);
        } else {
            log.info("✅ Nenhum alerta necessário. Dados dentro dos parâmetros normais.");
        }

        log.info("💾 Dados processados e salvos com sucesso. ID: {}, Unidade: {}, Valor: {}",
                data.getId(), data.getUnidade(), data.getValor());
        log.info("📡 Mensagem publicada via protocolo: {}", protocoloMsg);

        String finalMessage = (alertMessage != null)
                ? alertMessage
                : "✅ Leitura registrada com sucesso na fazenda.";

        log.info("📤 Mensagem final de resposta: {}", finalMessage);

        return ResponseEntity.ok(new SensorView(finalMessage, data, protocoloMsg));
    }

    /**
     * Envia uma leitura de sensor manualmente via protocolo AMQP.
     *
     * @param sensorData Dados da leitura do sensor a ser enviada.
     * @return {@link ResponseEntity} com confirmação do envio via AMQP.
     */
    @PostMapping("/enviar/amqp")
    public ResponseEntity<String> enviarAmqp(@RequestBody SensorData sensorData) {
        log.info("📥 Recebida solicitação para envio de dados do sensor via AMQP..."); 
        return ResponseEntity.ok(amqpPublisher.publish(sensorData));
    }

    /**
     * Envia uma leitura de sensor manualmente via protocolo MQTT.
     *
     * @param sensorData Dados da leitura do sensor a ser enviada.
     * @return {@link ResponseEntity} com confirmação do envio via MQTT.
     */
    @PostMapping("/enviar/mqtt")
    public ResponseEntity<String> enviarMqtt(@RequestBody SensorData sensorData) {
        log.info("📥 Recebida solicitação para envio de dados do sensor via MQTT...");
        return ResponseEntity.ok(mqttPublisher.publish(sensorData));
    }

}
