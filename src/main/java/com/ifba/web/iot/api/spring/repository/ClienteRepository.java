package com.ifba.web.iot.api.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ifba.web.iot.api.spring.model.Cliente;

/**
 * Repositório JPA para operações de persistência da entidade {@link Cliente}.
 * <p>
 * Fornece métodos padrão de CRUD através do {@link JpaRepository} e permite
 * consultas customizadas, como buscar um cliente pelo email.
 * </p>
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  /**
   * Busca um cliente pelo seu email.
   *
   * @param email Email do cliente a ser pesquisado.
   * @return {@link Optional} contendo o cliente caso encontrado, ou vazio caso
   *         contrário.
   */
  Optional<Cliente> findByEmail(String email);
}
