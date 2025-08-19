package com.ifba.web.iot.api.spring.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ifba.web.iot.api.spring.model.SensorData;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simula a coleta periódica e alternada de dados de sensores.
 * <p>
 * Utiliza um agendamento fixo para gerar leituras de um sensor por vez,
 * seguindo a ordem: temperatura, umidade e luminosidade.
 * </p>
 */
@Component
public class SensorScheduler {

  private final SensorDataService sensorService;
  private final Random random = new Random();

  // Lista de tipos de sensores que serão gerados em ordem
  private static final List<String> SENSOR_TYPES = Arrays.asList("temperatura", "umidade", "luminosidade");

  // Contador atômico para garantir que o acesso ao índice é seguro em ambiente
  // multi-thread
  private final AtomicInteger sensorIndex = new AtomicInteger(0);

  public SensorScheduler(SensorDataService sensorService) {
    this.sensorService = sensorService;
  }

  /**
   * Simula a coleta de dados de um sensor por vez, a cada 10 segundos,
   * seguindo uma ordem alternada (temperatura, umidade, luminosidade, etc.).
   * <p>
   * A cada execução, um novo valor aleatório é gerado para o sensor da vez
   * e o {@link SensorDataService} é chamado para processá-lo e salvá-lo.
   * </p>
   */
  @Scheduled(fixedRate = 10000) // 10 segundos
  public void gerarLeituraAlternada() {
    // Obter o índice atual e avançar para o próximo na lista de sensores
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
        // Caso um tipo de sensor desconhecido seja adicionado à lista
        return;
    }

    sensorService.saveSensorData(new SensorData(sensorType, valor, unidade));

  }
}