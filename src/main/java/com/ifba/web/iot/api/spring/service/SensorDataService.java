package com.ifba.web.iot.api.spring.service;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ifba.web.iot.api.spring.amqp.AmqpPublisher;
import com.ifba.web.iot.api.spring.controller.dto.update.SensorUpdateDTO;
import com.ifba.web.iot.api.spring.controller.dto.view.SensorView;
import com.ifba.web.iot.api.spring.model.Alert;
import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.model.Usuario;
import com.ifba.web.iot.api.spring.mqtt.MqttPublisher;
import com.ifba.web.iot.api.spring.mqtt.MqttToAmqpBridge;
import com.ifba.web.iot.api.spring.repository.SensorDataRepository;
import com.ifba.web.iot.api.spring.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * **Serviço de Gerenciamento de Dados de Sensores**
 *
 * <p>
 * Esta classe de serviço (`@Service`) centraliza a lógica de negócio
 * relacionada aos dados dos sensores.
 * Ela coordena a persistência dos dados, a detecção de alertas, e a comunicação
 * assíncrona com outros sistemas através dos protocolos MQTT e AMQP.
 * </p>
 *
 * <p>
 * A anotação `@RequiredArgsConstructor` do Lombok gera um construtor com os
 * campos
 * `final`, facilitando a injeção de dependências de forma concisa.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlertService alertService;
    private final MqttPublisher mqttPublisher;
    private final AmqpPublisher amqpPublisher;
    private final MqttToAmqpBridge mqttToAmqpBridge;

    /**
     * **Busca Todos os Registros de Sensores**
     *
     * <p>
     * Recupera todos os registros de dados de sensores persistidos no banco de
     * dados.
     * Este método é marcado como somente leitura (`@Transactional(readOnly =
     * true)`) para
     * otimizar o desempenho de consultas.
     * </p>
     *
     * @return Uma `List` de objetos {@link SensorData} representando todos os
     *         registros.
     */
    @Transactional(readOnly = true)
    public List<SensorData> findAll() {
        return sensorDataRepository.findAll();
    }

    /**
     * **SOBRECARGA 1: Salva e processa dados a partir de uma requisição de API.**
     * <p>
     * Este método é usado por endpoints da API. Ele usa o `Principal` para
     * encontrar
     * o usuário autenticado e, em seguida, chama a lógica de processamento
     * principal.
     * </p>
     *
     * @param sensorData O objeto de dados do sensor.
     * @param principal  O objeto de segurança do usuário autenticado.
     * @return Um {@link Triple} com a mensagem de alerta, os dados salvos e a
     *         mensagem do protocolo.
     */
    @Transactional
    public Triple<String, SensorData, String> saveAndProcess(SensorData sensorData, Principal principal) {
        if (principal == null) {
            log.error("❌ Erro: Tentativa de criar sensor sem usuário autenticado.");
            throw new AccessDeniedException("Usuário não autenticado. Acesso negado.");
        }

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new AccessDeniedException("Usuário não encontrado. Acesso negado."));

        return processAndSave(sensorData, usuario);
    }

    /**
     * **SOBRECARGA 2: Salva e processa dados a partir de um agendador interno.**
     * <p>
     * Este método é usado por processos internos, como o `SensorScheduler`, que já
     * fornecem o objeto de usuário.
     * </p>
     *
     * @param sensorData O objeto de dados do sensor.
     * @param usuario    O objeto do usuário a ser associado aos dados.
     * @return Um {@link Triple} com a mensagem de alerta, os dados salvos e a
     *         mensagem do protocolo.
     */
    @Transactional
    public Triple<String, SensorData, String> saveAndProcess(SensorData sensorData, Usuario usuario) {
        if (usuario == null) {
            log.error("❌ Erro: Tentativa de processar dados com usuário nulo.");
            throw new IllegalArgumentException("O usuário não pode ser nulo para esta operação.");
        }
        return processAndSave(sensorData, usuario);
    }

    /**
     * **Método Privado para a Lógica Principal de Salvamento e Processamento.**
     * <p>
     * Este método encapsula toda a lógica de negócio principal, evitando duplicação
     * de código entre os métodos públicos.
     * </p>
     *
     * @param sensorData O objeto de dados do sensor.
     * @param usuario    O objeto do usuário a ser associado.
     * @return Um {@link Triple} com a mensagem de alerta, os dados salvos e a
     *         mensagem do protocolo.
     */
    private Triple<String, SensorData, String> processAndSave(SensorData sensorData, Usuario usuario) {
        log.info("📥 Iniciando o salvamento e processamento dos dados do sensor...");

        sensorData.setUsuario(usuario);
        log.info("👤 Associando a leitura ao usuário: {}", usuario.getNome());

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

        String alertMessage = verificarAlerta(sensorData);

        // Salvar alerta no banco de dados, se houver
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

        SensorData saved = sensorDataRepository.save(sensorData);
        log.info("💾 Dados do sensor salvos com sucesso. ID: {}", saved.getId());

        // Publicação dos dados conforme tipo do sensor
        String protocoloMsg = null;
        if ("temperatura".equals(sensor)) {
            protocoloMsg = mqttPublisher.publish(saved);
            log.info("📡 Dados de temperatura publicados via MQTT:\n{}", saved);
            mqttToAmqpBridge.forwardToQueue(saved);
        } else {
            protocoloMsg = amqpPublisher.publish(saved);
            log.info("📡 Dados publicados via AMQP:\n{}", saved);
        }

        log.info("✅ Finalizado o processo de salvamento e publicação dos dados do sensor.");
        return Triple.of(alertMessage, saved, protocoloMsg);
    }

    /**
     * **Busca um Registro de Sensor por ID**
     *
     * <p>
     * Encontra uma entidade {@link SensorData} no banco de dados a partir de seu
     * ID.
     * Este método é transacional e retorna `null` se o registro não for encontrado.
     * </p>
     *
     * @param id O ID do registro de sensor a ser buscado.
     * @return O objeto {@link SensorData} correspondente ao ID, ou `null` se não
     *         existir.
     */
    @Transactional
    public SensorData findById(Long id) {
        return sensorDataRepository.findById(id).orElse(null);
    }

    /**
     * **Verifica a Propriedade de um Sensor**
     *
     * <p>
     * Este é o mecanismo de defesa contra o IDOR. Ele consulta o repositório para
     * verificar se o sensor com o ID fornecido pertence ao usuário especificado.
     * </p>
     *
     * @param username O nome de usuário do proprietário em potencial.
     * @param sensorId O ID do sensor a ser verificado.
     * @return {@code true} se o sensor pertencer ao usuário, {@code false} caso
     *         contrário.
     */
    @Transactional(readOnly = true)
    public boolean isUserSensorOwner(String username, Long sensorId) {
        // Encontra o sensor pelo ID.
        Optional<SensorData> sensorOptional = sensorDataRepository.findById(sensorId);

        // Se o sensor existir, verifica se o nome de usuário do proprietário
        // (acessado através da entidade 'Usuario') corresponde ao nome de usuário
        // fornecido.
        if (sensorOptional.isPresent()) {
            SensorData sensor = sensorOptional.get();
            // Acessa o objeto 'Usuario' e obtém o nome de usuário para a comparação.
            return sensor.getUsuario().getEmail().equals(username);
        }

        // Se o sensor não for encontrado, ele não pertence ao usuário.
        return false;
    }

    /**
     * **Atualiza os Dados de um Sensor Existente**
     *
     * <p>
     * Este método agora recebe o nome de usuário autenticado do controller para
     * garantir que a operação seja segura. Ele valida a propriedade do sensor
     * antes de prosseguir com a atualização.
     * </p>
     *
     * @param currentUsername O nome de usuário do usuário autenticado.
     * @param id              O ID do sensor a ser atualizado.
     * @param updatedDTO      O DTO {@link SensorUpdateDTO} contendo os novos dados
     *                        do sensor.
     * @return O objeto {@link SensorView} com os dados atualizados, ou {@code null}
     *         se o
     *         registro original não for encontrado ou se o usuário não for o
     *         proprietário.
     */
    @Transactional
    public SensorView update(String currentUsername, Long id, SensorUpdateDTO updatedDTO) {
        // 🚨 Mecanismo de defesa: verifica a propriedade do sensor.
        // A lógica de bloqueio de acesso não autorizado deve estar no controller,
        // mas é essencial que o serviço forneça o método de verificação.
        if (!isUserSensorOwner(currentUsername, id)) {
            // Retorna null para sinalizar ao controller que a operação falhou
            // por falta de autorização.
            return null;
        }

        SensorData existingData = sensorDataRepository.findById(id).orElse(null);
        if (existingData != null) {
            existingData.setSensor(updatedDTO.getSensor());
            existingData.setValor(updatedDTO.getValor());
            SensorData savedData = sensorDataRepository.save(existingData);
            return new SensorView("Dados atualizados com sucesso", savedData, "HTTP");
        }
        return null;
    }

    /**
     * **Busca o Registro de Sensor Mais Recente**
     *
     * <p>
     * Recupera o registro de sensor mais recente do banco de dados, ordenando-o
     * pela data e hora de registro em ordem decrescente.
     * </p>
     *
     * @return O objeto {@link SensorData} com o timestamp mais recente,
     *         ou `null` se o banco de dados não contiver registros.
     */
    public SensorData findLatest() {
        return sensorDataRepository.findFirstByOrderByTimestampDesc().orElse(null);
    }

    /**
     * **Verifica a Ocorrência de Alertas**
     *
     * <p>
     * Este método utilitário checa se os dados de um sensor excedem limites
     * pré-definidos. Ele é chamado por outros métodos de serviço para
     * determinar a necessidade de gerar uma mensagem de alerta.
     * </p>
     *
     * @param data O objeto {@link SensorData} contendo os dados a serem
     *             verificados.
     * @return Uma `String` com a mensagem de alerta se os limites forem excedidos,
     *         ou `null` caso os dados estejam dentro do normal.
     */
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
