package com.ifba.web.iot.api.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entidade que representa um Cliente com dados pessoais sensíveis.
 * A senha é armazenada criptografada, e o nome/email podem ser mascarados
 * para evidenciar conformidade com LGPD.
 */

/**
 * Entidade que representa um cliente da aplicação.
 * Contém dados pessoais e credenciais para login.
 */
@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String senha;
}
