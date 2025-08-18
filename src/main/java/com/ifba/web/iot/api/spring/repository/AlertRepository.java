package com.ifba.web.iot.api.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifba.web.iot.api.spring.model.Alert;

/**
 * Interface de repositório para a entidade {@link Alert}.
 * <p>
 * Estende {@link JpaRepository} para fornecer métodos CRUD prontos para uso
 * com a entidade {@link Alert}.
 */
@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
}