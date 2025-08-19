package com.ifba.web.iot.api.spring.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ifba.web.iot.api.spring.model.SensorData;
import com.ifba.web.iot.api.spring.service.SensorDataService;

import java.util.Random;

/**
 * Classe de configuração responsável por inicializar dados fictícios
 * na aplicação ao ser iniciada. Utiliza sensores simulados com valores
 * aleatórios para teste e desenvolvimento.
 */
@Configuration
public class DataInitializer {

  /**
   * Bean do tipo {@link CommandLineRunner} que insere dados simulados de sensores
   * ao iniciar a aplicação. Gera 10 leituras para cada tipo de sensor: temperatura,
   * umidade e luminosidade.
   *
   * @param sensorService Serviço responsável por salvar os dados dos sensores.
   * @return CommandLineRunner que executa a carga de dados ao iniciar a aplicação.
   */
  @Bean
  public CommandLineRunner loadData(SensorDataService sensorService) {
    return args -> {
      Random random = new Random();

      for (int i = 0; i < 10; i++) {
        sensorService.saveSensorData(new SensorData("temperatura", 20 + random.nextDouble() * 15));
        sensorService.saveSensorData(new SensorData("umidade", 40 + random.nextDouble() * 20));
        sensorService.saveSensorData(new SensorData("luminosidade", 300 + random.nextDouble() * 100));
      }
    };
  }
}
