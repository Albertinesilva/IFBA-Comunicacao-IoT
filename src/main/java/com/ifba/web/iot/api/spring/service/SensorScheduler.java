package com.ifba.web.iot.api.spring.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ifba.web.iot.api.spring.model.SensorData;

import java.util.Random;

@Component
public class SensorScheduler {

  private final SensorService sensorService;
  private final Random random = new Random();

  public SensorScheduler(SensorService sensorService) {
    this.sensorService = sensorService;
  }

  /**
   * Simula a coleta periódica de dados de sensores a cada 10 segundos.
   */
  @Scheduled(fixedRate = 10000) // 10 segundos
  public void gerarLeiturasPeriodicas() {
    // Temperatura
    double temperatura = 20 + random.nextDouble() * 15; // 20 a 35
    sensorService.saveSensorData(new SensorData("temperatura", temperatura, "°C"));

    // Umidade
    double umidade = 30 + random.nextDouble() * 70; // 30 a 100
    sensorService.saveSensorData(new SensorData("umidade", umidade, "%"));

    // Luminosidade
    double luminosidade = 100 + random.nextDouble() * 900; // 100 a 1000 lux
    sensorService.saveSensorData(new SensorData("luminosidade", luminosidade, "lux"));
  }
}
