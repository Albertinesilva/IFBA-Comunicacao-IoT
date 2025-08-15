package com.ifba.web.iot.api.spring.controller.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para registro de um novo cliente.
 */
@Data
public class RegisterForm {

  @NotBlank(message = "O nome é obrigatório")
  private String nome;

  @NotBlank(message = "O email é obrigatório")
  @Email(message = "Email inválido")
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String senha;
}