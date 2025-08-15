package com.ifba.web.iot.api.spring.controller.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de login JWT.
 */
@Getter
@Setter
public class LoginForm {

  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email inválido")
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  private String senha;
}
