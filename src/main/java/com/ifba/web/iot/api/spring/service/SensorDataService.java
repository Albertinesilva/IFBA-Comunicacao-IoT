package com.ifba.web.iot.api.spring.service;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifba.web.iot.api.spring.amqp.AmqpPublisher;
import com.ifba.web.iot.api.spring.model.Alert;
import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.mqtt.MqttPublisher;
import com.ifba.web.iot.api.spring.mqtt.MqttToAmqpBridge;
import com.ifba.web.iot.api.spring.repository.SensorDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Serviço responsável pelo gerenciamento de dados dos sensores.
 * <p>
 * Fornece métodos para:
 * <ul>
 * <li>Consulta de todos os registros de sensores</li>
 * <li>Persistência de dados de sensores</li>
 * <li>Publicação de dados via MQTT e AMQP</li>
 * <li>Detecção de alertas (ex.: temperatura elevada)</li>
 * </ul>
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDatarepository;
    private final AlertService alertService;
    private final MqttPublisher mqttPublisher;
    private final AmqpPublisher amqpPublisher;
    private final MqttToAmqpBridge mqttToAmqpBridge;

    /**
     * Retorna todos os registros de dados dos sensores.
     *
     * @return Lista de {@link SensorData} contendo todos os registros persistidos.
     */
    @Transactional(readOnly = true)
    public List<SensorData> findAll() {
        return sensorDatarepository.findAll();
    }

    /**
     * Salva os dados de um sensor, define unidade de medida conforme o tipo,
     * verifica alertas e publica os dados via MQTT ou AMQP.
     *
     * @param sensorData Dados do sensor a serem persistidos.
     * @return {@link Triple} contendo:
     *         <ol>
     *         <li>Mensagem de alerta (caso exista, ou {@code null})</li>
     *         <li>Objeto {@link SensorData} persistido</li>
     *         <li>Mensagem de protocolo retornada pelo publicador (MQTT ou
     *         AMQP)</li>
     *         </ol>
     */
    @Transactional
    public Triple<String, SensorData, String> saveSensorData(SensorData sensorData) {
        log.info("📥 Iniciando o salvamento dos dados do sensor...");

        String sensor = Objects.requireNonNull(sensorData.getSensor(), "O tipo do sensor não pode ser nulo.");
        double valor = sensorData.getValor();
        sensorData.setTimestamp(LocalDateTime.now());

        // Define unidade de medida conforme tipo de sensor
        switch (sensor) {
            case "temperatura":
                sensorData.setUnidade("°C");
                break;
            case "umidade":
                sensorData.setUnidade("%");
                break;
            case "luminosidade":
                sensorData.setUnidade("lux");
                break;
            default:
                log.warn("⚠️ Tipo de sensor desconhecido: {}", sensor);
        }

        log.debug("📊 Dados recebidos: {}", sensorData);

        String alertMessage = null;

        // Verificação de alertas
        if ("temperatura".equals(sensor) && valor > 30) {
            alertMessage = "🌡️ Alerta! Temperatura elevada detectada. Verifique as condições da lavoura.";
        } else if ("umidade".equals(sensor) && (valor < 20 || valor > 80)) {
            alertMessage = "💧 Alerta! Nível de umidade fora do ideal. Ajuste a irrigação.";
        } else if ("luminosidade".equals(sensor) && valor < 200) {
            alertMessage = "💡 Alerta! Baixo nível de luminosidade detectado. Acione as luzes auxiliares.";
        }

        // Salvar alerta no banco de dados, se houver e se a funcionalidade estiver
        // ativada
        if (alertMessage != null) {
            log.warn("⚠️ Alerta gerado: {}", alertMessage);
            if (alertService.isAlertSavingEnabled()) {
                Alert alert = new Alert(sensor, valor, sensorData.getUnidade(), alertMessage);
                alertService.saveAlert(alert);
                log.info("💾 Alerta salvo no banco de dados.");
            } else {
                log.info("🛑 Salvamento de alertas desativado. Alerta não persistido.");
            }
        } else {
            log.info("✅ Nenhum alerta necessário. Dados dentro dos parâmetros normais.");
        }

        SensorData saved = sensorDatarepository.save(sensorData);
        log.info("💾 Dados do sensor salvos com sucesso. ID: {}", saved.getId());

        // Publicação dos dados conforme tipo do sensor
        String protocoloMsg = null;
        if ("temperatura".equals(sensor)) {
            protocoloMsg = mqttPublisher.publish(saved);
            log.info("📡 Dados de temperatura publicados via MQTT:\n{}", saved);
            // Removido a chamada de 'save' duplicada e desnecessária
            mqttToAmqpBridge.forwardToQueue(saved);
        } else {
            protocoloMsg = amqpPublisher.publish(saved);
            log.info("📡 Dados publicados via AMQP:\n{}", saved);
        }

        log.info("✅ Finalizado o processo de salvamento e publicação dos dados do sensor.");
        return Triple.of(alertMessage, saved, protocoloMsg);
    }

    /**
     * Busca o último registro de dados do sensor com base no timestamp.
     * <p>
     * Este método utiliza o repositório de dados do sensor para buscar o
     * registro mais recente no banco de dados.
     * </p>
     *
     * @return O objeto SensorData mais recente, ou null se nenhum dado for
     *         encontrado.
     */
    public SensorData findLatest() {
        return sensorDatarepository.findFirstByOrderByTimestampDesc().orElse(null);
    }

    public String verificarAlerta(SensorData data) {
        String alertMessage = null;

        if ("temperatura".equals(data.getSensor()) && data.getValor() > 30) {
            alertMessage = "🌡️ Alerta! Temperatura elevada detectada.";
        } else if ("umidade".equals(data.getSensor()) && (data.getValor() < 20 || data.getValor() > 80)) {
            alertMessage = "💧 Alerta! Umidade baixa detectada.";
        } else if ("luminosidade".equals(data.getSensor()) && data.getValor() < 200) {
            alertMessage = "💡 Alerta! Baixo nível de luminosidade detectado. Acione as luzes auxiliares.";
        }
        return alertMessage;
    }

}