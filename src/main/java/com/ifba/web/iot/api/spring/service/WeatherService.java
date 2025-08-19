package com.ifba.web.iot.api.spring.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ifba.web.iot.api.spring.controller.dto.view.WeatherView;

/**
 * Serviço responsável por interagir com a API externa do OpenWeather.
 * <p>
 * Este serviço busca dados de clima e converte a resposta para um formato
 * que possa ser consumido pela aplicação interna.
 * </p>
 */
@Slf4j
@Service
public class WeatherService {

  // URL base da API do OpenWeather, lida do application.properties
  @Value("${openweathermap.api.url}")
  private String apiUrl;

  // Chave de API, lida do application.properties
  @Value("${openweathermap.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * Busca os dados de clima para uma cidade específica.
   *
   * @param city O nome da cidade para buscar os dados.
   * @return Um objeto {@link WeatherResponse} com os dados relevantes.
   */
  public WeatherView getWeatherDataByCity(String city) {
    log.info("🌐 Buscando dados de clima para a cidade: {}", city);

    try {
      // Constrói a URL da requisição com os parâmetros.
      // O UriComponentsBuilder irá codificar os espaços automaticamente.
      UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
          .queryParam("q", city)
          .queryParam("appid", apiKey)
          .queryParam("units", "metric") // Para obter temperatura em Celsius
          .queryParam("lang", "pt_br"); // Para obter a descrição em Português

      String url = builder.toUriString();

      // Faz a requisição HTTP e mapeia a resposta para o DTO
      WeatherView response = restTemplate.getForObject(url, WeatherView.class);

      if (response != null) {
        log.info("✅ Dados de clima recebidos com sucesso. Temperatura: {} °C", response.getMain().getTemp());
      } else {
        log.warn("❌ Nenhuma resposta recebida da API do OpenWeather.");
      }

      return response;
    } catch (Exception e) {
      log.error("❌ Erro ao buscar dados de clima da API do OpenWeather: {}", e.getMessage());
      return null;
    }
  }

  /**
   * Busca os dados de clima para uma cidade específica usando o ID da cidade.
   *
   * @param id O ID da cidade para buscar os dados.
   * @return Um objeto {@link WeatherResponse} com os dados relevantes.
   */
  public WeatherView getWeatherDataById(String id) {
    log.info("🌐 Buscando dados de clima para o ID da cidade: {}", id);

    try {
      // Constrói a URL da requisição com o parâmetro 'id' em vez de 'q'.
      UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
          .queryParam("id", id)
          .queryParam("appid", apiKey)
          .queryParam("units", "metric") // Para obter temperatura em Celsius
          .queryParam("lang", "pt_br"); // Para obter a descrição em Português

      String url = builder.toUriString();

      // Faz a requisição HTTP e mapeia a resposta para o DTO
      WeatherView response = restTemplate.getForObject(url, WeatherView.class);

      if (response != null) {
        log.info("✅ Dados de clima recebidos com sucesso. Temperatura: {} °C", response.getMain().getTemp());
      } else {
        log.warn("❌ Nenhuma resposta recebida da API do OpenWeather.");
      }

      return response;
    } catch (Exception e) {
      log.error("❌ Erro ao buscar dados de clima da API do OpenWeather: {}", e.getMessage());
      return null;
    }
  }

}