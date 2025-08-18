package com.ifba.web.iot.api.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifba.web.iot.api.spring.model.SensorData;

/**
 * Interface de repositório para a entidade {@link SensorData}.
 * 
 * Estende {@link JpaRepository} para fornecer métodos CRUD e consultas
 * prontas para uso com a entidade {@link SensorData}.
 */
@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {}
