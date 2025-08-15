package com.ifba.web.iot.api.spring.controller.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO para exibir dados do cliente com informações mascaradas.
 */
@Data
@AllArgsConstructor
public class ClienteView {

  private String nomeMascarado;
  private String emailMascarado;

}