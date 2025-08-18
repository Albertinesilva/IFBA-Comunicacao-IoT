package com.ifba.web.iot.api.spring.controller.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO para mapear a resposta JSON da API do OpenWeather.
 * <p>
 * O DTO captura apenas os campos relevantes da resposta para evitar
 * o excesso de dados.
 * </p>
 */
@Getter
@Setter
public class WeatherView {

    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private String name;

    @Getter
    @Setter
    public static class Main {
        private double temp;
        private int humidity;
    }

    @Getter
    @Setter
    public static class Weather {
        private String description;
    }

    @Getter
    @Setter
    public static class Wind {
        private double speed;
    }
}