package com.ifba.web.iot.api.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifba.web.iot.api.spring.model.Alert;
import com.ifba.web.iot.api.spring.repository.AlertRepository;

import lombok.Getter;
import lombok.Setter;

@Service
public class AlertService {

  @Getter
  @Setter
  private boolean isAlertSavingEnabled = true; // Ativado por padrão

  @Autowired
  private AlertRepository alertRepository;

  public Alert saveAlert(Alert alert) {
    return alertRepository.save(alert);
  }

  public List<Alert> findAll() {
    return alertRepository.findAll();
  }

  public boolean isAlertActive(Long id) {
    return true;
  }
}
