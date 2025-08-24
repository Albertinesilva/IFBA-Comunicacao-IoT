package com.ifba.web.iot.api.spring.service;

import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.model.Usuario;
import com.ifba.web.iot.api.spring.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * **Componente de Agendamento para Simulação de Dados de Sensores**
 *
 * <p>
 * Esta classe `SensorScheduler` é um componente Spring agendado (`@Component`).
 * Sua principal responsabilidade é simular a coleta de dados de sensores,
 * gerando
 * novas leituras de forma periódica e automática. Ele foi projetado para
 * testar e demonstrar o fluxo de persistência de dados no sistema
 * de back-end sem a necessidade de uma fonte de dados externa em tempo real.
 * </p>
 *
 * <p>
 * Para evitar erros de violação de integridade de dados (`NOT NULL constraint
 * violation`),
 * ele garante que cada leitura gerada seja associada a uma entidade `Usuario`
 * válida. Um "usuário mock" é criado ou recuperado durante a inicialização para
 * este fim.
 * </p>
 */
@Component
public class SensorScheduler {

  private final SensorDataService sensorService;
  private final UsuarioRepository usuarioRepository;
  private final Random random = new Random();

  // Cache para a entidade 'Usuario' mock. Evita a necessidade de buscá-la
  // no banco de dados a cada execução agendada, otimizando o desempenho.
  private Usuario mockUser;

  // Lista fixa de tipos de sensores para garantir uma ordem de geração
  // consistente e alternada.
  private static final List<String> SENSOR_TYPES = Arrays.asList("temperatura", "umidade", "luminosidade");

  // Contador atômico para rastrear o índice do sensor. O uso de `AtomicInteger`
  // assegura que as operações de leitura e incremento são thread-safe,
  // o que é crucial em um ambiente de agendamento multi-threaded.
  private final AtomicInteger sensorIndex = new AtomicInteger(0);

  /**
   * **Injeção de Dependências**
   *
   * <p>
   * Construtor que utiliza a injeção de dependências do Spring para
   * receber instâncias de `SensorDataService` e `UsuarioRepository`.
   * Isso desacopla a classe de suas dependências, facilitando testes
   * unitários e a manutenção do código.
   * </p>
   *
   * @param sensorService     O serviço responsável pela lógica de negócio de
   *                          dados de sensores.
   * @param usuarioRepository O repositório para acesso à entidade `Usuario`.
   */
  @Autowired
  public SensorScheduler(SensorDataService sensorService, UsuarioRepository usuarioRepository) {
    this.sensorService = sensorService;
    this.usuarioRepository = usuarioRepository;
  }

  /**
   * **Configuração Inicial do Usuário Mock**
   *
   * <p>
   * Método anotado com `@PostConstruct`. Isso garante que ele será executado
   * uma única vez, logo após a inicialização das dependências do bean.
   * Sua função é garantir que haja um `Usuario` no banco de dados para associar
   * às leituras simuladas.
   * </p>
   *
   * <p>
   * Ele tenta buscar um usuário com ID 1. Se não encontrar, um novo usuário
   * com nome "Usuário Mock" é criado e persistido. Essa estratégia evita
   * a criação de usuários duplicados a cada reinicialização da aplicação.
   * </p>
   */
  @PostConstruct
  public void setupMockUser() {
    this.mockUser = usuarioRepository.findById(1L).orElseGet(() -> {
      Usuario newUser = new Usuario();
      newUser.setNome("Usuário Mock");
      newUser.setEmail("mock.user@example.com");
      newUser.setSenha("123456");
      return usuarioRepository.save(newUser);
    });
  }

  /**
   * **Gerador de Leituras Agendado**
   *
   * <p>
   * Método agendado para ser executado a cada 10.000 milissegundos (10 segundos),
   * conforme definido por `@Scheduled(fixedRate = 10000)`. Ele encapsula a
   * lógica de geração de dados de sensores, alternando entre os tipos definidos.
   * </p>
   *
   * <p>
   * A cada execução, ele:
   * </p>
   * <ul>
   * <li>Obtém o próximo tipo de sensor da lista de forma cíclica.</li>
   * <li>Gera um valor e uma unidade aleatórios baseados no tipo do sensor.</li>
   * <li>Cria uma nova instância de `SensorData`.</li>
   * <li>**Associa o `mockUser` à nova leitura
   * (`newSensorData.setUsuario(mockUser)`).** Esta é a etapa crucial que resolve
   * o erro de violação de integridade.</li>
   * <li>Chama o `SensorDataService` para persistir a nova leitura no banco de
   * dados.</li>
   * </ul>
   */
  @Scheduled(fixedRate = 10000)
  public void gerarLeituraAlternada() {
    // Checagem de segurança: garante que o `mockUser` foi inicializado antes
    // de tentar usá-lo, prevenindo um `NullPointerException`.
    if (this.mockUser == null) {
      System.err.println("Erro: Usuário mock não inicializado. Verifique a configuração `@PostConstruct`.");
      return;
    }

    int currentIndex = sensorIndex.getAndIncrement();
    String sensorType = SENSOR_TYPES.get(currentIndex % SENSOR_TYPES.size());

    double valor;
    String unidade;

    switch (sensorType) {
      case "temperatura":
        valor = 20 + random.nextDouble() * 15; // 20 a 35 °C
        unidade = "°C";
        break;
      case "umidade":
        valor = 30 + random.nextDouble() * 70; // 30 a 100 %
        unidade = "%";
        break;
      case "luminosidade":
        valor = 100 + random.nextDouble() * 900; // 100 a 1000 lux
        unidade = "lux";
        break;
      default:
        // Retorna em caso de tipo de sensor desconhecido para evitar erros de runtime.
        return;
    }

    SensorData newSensorData = new SensorData(sensorType, valor, unidade);
    // A linha abaixo é a solução para o problema de persistência.
    newSensorData.setUsuario(mockUser);

    sensorService.saveAndProcess(newSensorData, mockUser);
  }
}
