package com.ifba.web.iot.api.spring.controller.dto.update;

import lombok.Data;

@Data
public class SensorUpdateDTO {

  private String sensor;
  private double valor;
}
