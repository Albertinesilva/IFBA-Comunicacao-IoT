package com.ifba.web.iot.api.spring.controller.dto.view;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para resposta de login JWT.
 */
@Getter
@Setter
public class LoginView {
  private String token;

  public LoginView(String token) {
    this.token = token;
  }
}