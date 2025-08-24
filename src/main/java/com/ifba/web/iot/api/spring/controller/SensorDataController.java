package com.ifba.web.iot.api.spring.controller;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ifba.web.iot.api.spring.amqp.AmqpPublisher;
import com.ifba.web.iot.api.spring.controller.dto.update.SensorUpdateDTO;
import com.ifba.web.iot.api.spring.controller.dto.view.SensorView;
import com.ifba.web.iot.api.spring.model.Alert;
import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.mqtt.MqttPublisher;
import com.ifba.web.iot.api.spring.service.AlertService;
import com.ifba.web.iot.api.spring.service.SensorDataService;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST responsável pelo gerenciamento das leituras de sensores.
 */
@Slf4j
@RestController
@RequestMapping("/api/sensores")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorService;

    @Autowired
    private AmqpPublisher amqpPublisher;

    @Autowired
    private MqttPublisher mqttPublisher;

    @Autowired
    private AlertService alertService;

    /**
     * Retorna todas as leituras de sensores registradas no sistema.
     *
     * @return Lista de objetos {@link SensorData}.
     */
    @GetMapping
    public List<SensorData> getAll() {
        return sensorService.findAll();
    }

    /**
     * Retorna o histórico de todos os alertas registrados no sistema.
     * <p>
     * Este endpoint utiliza o serviço {@link AlertService} para buscar todos
     * os registros da tabela de alertas.
     * </p>
     *
     * @return Lista de objetos {@link Alert}.
     */
    @GetMapping("/alertas")
    public List<Alert> getAllAlerts() {
        return alertService.findAll();
    }

    /**
     * Define se o salvamento de alertas deve ser ativado ou desativado.
     * <p>
     * Este endpoint utiliza um PUT para alterar o estado de salvamento de alertas
     * para `true` (ativado) ou `false` (desativado).
     * </p>
     *
     * @param status O novo status do salvamento de alertas.
     * @return {@link ResponseEntity} com uma mensagem de confirmação.
     */
    @PutMapping("/alertas/status/{status}")
    public ResponseEntity<String> setAlertSavingStatus(@PathVariable boolean status) {
        alertService.setAlertSavingEnabled(status);
        String message = status ? "✅ Salvamento de alertas ativado." : "🛑 Salvamento de alertas desativado.";
        log.info(message);
        return ResponseEntity.ok(message);
    }

    /**
     * Cria uma nova leitura de sensor, processa possíveis alertas e envia
     * a leitura via protocolo apropriado (AMQP ou MQTT).
     *
     * @param sensorData Dados da leitura do sensor recebidos no corpo da
     *                   requisição.
     * @return {@link ResponseEntity} com uma mensagem de resposta e os dados
     *         registrados.
     */
    @PostMapping
    public ResponseEntity<SensorView> create(@RequestBody SensorData sensorData, Principal principal) {
        log.info("📥 Recebida solicitação para criação de dados do sensor...");

        Triple<String, SensorData, String> result = sensorService.saveAndProcess(sensorData, principal);
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
     * Manipula a requisição HTTP PUT para atualizar um sensor existente.
     * <p>
     * Este endpoint permite a atualização de um sensor específico identificado pelo
     * seu ID.
     * Ele extrai o ID do usuário autenticado para fins de logging de segurança.
     *
     * <p>
     * <b>Vulnerabilidade de Segurança (IDOR)</b>
     * <p>
     * Como a lógica de autorização para garantir que o usuário só possa atualizar
     * seus
     * próprios sensores está ausente, este método possui uma vulnerabilidade
     * de IDOR (Insecure Direct Object Reference). Um atacante poderia usar
     * o token de um usuário para atualizar o sensor de outro usuário,
     * simplesmente adivinhando ou alterando o ID na URL.
     *
     * @param id        O ID do sensor a ser atualizado. É extraído do caminho da
     *                  URL.
     * @param updateDTO O objeto de transferência de dados (DTO) contendo as
     *                  informações
     *                  atualizadas do sensor.
     * @return Um {@link org.springframework.http.ResponseEntity} com o
     *         {@link br.com.example.view.SensorView}
     *         atualizado e o status HTTP 200 (OK) se a atualização for
     *         bem-sucedida.
     *         Retorna um status HTTP 404 (Not Found) se o sensor com o ID fornecido
     *         não for
     *         encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SensorView> update(@PathVariable Long id, @RequestBody SensorUpdateDTO updateDTO) {
        // Obtém o nome de usuário (ID) do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // 🚨 LOG PARA A SIMULAÇÃO DO ATAQUE
        log.info("🚨 Simulação de Ataque: Usuário '{}' tentando atualizar o sensor com ID: {}", currentUsername, id);

        // A lógica para encontrar e atualizar é encapsulada no serviço.
        // A VULNERABILIDADE AINDA ESTÁ AQUI, pois o ID do usuário logado NÃO É PASSADO.
        SensorView updatedView = sensorService.update(id, updateDTO);

        if (updatedView == null) {
            log.warn("Sensor com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        log.info("✅ Simulação de Ataque: Sensor com ID {} do USUÁRIO ALVO atualizado com sucesso por '{}'.", id,
                currentUsername);
        return ResponseEntity.ok(updatedView);
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

    // Endpoint para retornar os últimos dados com alerta
    @GetMapping("/latest")
    public Map<String, Object> getLatest() {
        SensorData latest = sensorService.findLatest();
        Map<String, Object> response = new HashMap<>();
        if (latest != null) {
            String alerta = sensorService.verificarAlerta(latest);

            // Formata o valor para duas casas decimais antes de enviar para o front-end
            DecimalFormat df = new DecimalFormat("#.##");
            String valorFormatado = df.format(latest.getValor());

            response.put("sensor", latest.getSensor());
            response.put("valor", valorFormatado);
            response.put("unidade", latest.getUnidade());
            response.put("alertMessage", alerta);
            response.put("timestamp", latest.getTimestamp());
        }
        return response;
    }
}
