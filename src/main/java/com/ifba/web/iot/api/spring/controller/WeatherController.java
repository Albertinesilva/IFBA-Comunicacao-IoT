package com.ifba.web.iot.api.spring.controller;

import com.ifba.web.iot.api.spring.controller.dto.view.WeatherView;
import com.ifba.web.iot.api.spring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para interagir com a API de clima.
 */
@Slf4j
@RestController
@RequestMapping("/api/clima")
@RequiredArgsConstructor
public class WeatherController {

  private final WeatherService weatherService;

  /**
   * Retorna os dados de clima para uma cidade, buscando por nome ou por ID.
   * <p>
   * Este endpoint utiliza parâmetros de consulta para evitar ambiguidades.
   * Exemplo: /api/clima?city=Sao Paulo ou /api/clima?id=3448439
   *
   * @param city O nome da cidade (opcional).
   * @param id   O ID da cidade (opcional).
   * @return {@link ResponseEntity} com os dados de clima ou um erro se a
   *         requisição falhar.
   */
  @GetMapping
  public ResponseEntity<WeatherView> getWeather(
      @RequestParam(required = false) String city,
      @RequestParam(required = false) String id) {

    log.info("📥 Recebida solicitação de clima. Parâmetros - city: {}, id: {}", city, id);

    WeatherView weatherData = null;

    if (city != null) {
      weatherData = weatherService.getWeatherDataByCity(city);
      if (weatherData != null) {
        log.info("✅ Dados de clima enviados com sucesso para a cidade: {}", city);
      } else {
        log.error("❌ Não foi possível obter os dados de clima para a cidade: {}", city);
        return ResponseEntity.status(500).build();
      }
    } else if (id != null) {
      weatherData = weatherService.getWeatherDataById(id);
      if (weatherData != null) {
        log.info("✅ Dados de clima enviados com sucesso para o ID: {}", id);
      } else {
        log.error("❌ Não foi possível obter os dados de clima para o ID: {}", id);
        return ResponseEntity.status(500).build();
      }
    } else {
      log.warn("⚠️ Nenhum parâmetro de busca (city ou id) foi fornecido.");
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(weatherData);
  }

}
