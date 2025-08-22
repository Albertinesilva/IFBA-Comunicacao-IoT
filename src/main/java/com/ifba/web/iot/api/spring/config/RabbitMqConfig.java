package com.ifba.web.iot.api.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração Spring para a integração com RabbitMQ.
 * <p>
 * Esta classe define e configura os componentes essenciais do RabbitMQ, como a
 * fila,
 * o exchange e o binding, que são necessários para que a aplicação se comunique
 * com o broker de mensagens. As informações de configuração são obtidas do
 * arquivo de propriedades da aplicação.
 * </p>
 */
@Configuration
public class RabbitMqConfig {

  private static final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

  /**
   * Nome do exchange do RabbitMQ, injetado a partir das propriedades da
   * aplicação.
   */
  @Value("${amqp.exchange}")
  private String amqpExchange;

  /**
   * Nome da fila do RabbitMQ, injetado a partir das propriedades da aplicação.
   */
  @Value("${amqp.queue}")
  private String amqpQueue;

  /**
   * Chave de roteamento usada para o binding entre a fila e o exchange.
   */
  @Value("${amqp.routing.key}")
  private String amqpRoutingKey;

  /**
   * Define um bean para a fila do RabbitMQ.
   * <p>
   * Cria e retorna uma instância de {@link org.springframework.amqp.core.Queue}
   * com o nome
   * especificado nas propriedades, indicando que ela não é exclusiva.
   * </p>
   * 
   * @return A fila do RabbitMQ.
   */
  @Bean
  Queue queue() {
    logger.info("⚙️ Configurando a fila RabbitMQ: {}", amqpQueue);
    return new Queue(amqpQueue, false);
  }

  /**
   * Define um bean para o exchange do RabbitMQ.
   * <p>
   * Cria e retorna uma instância de
   * {@link org.springframework.amqp.core.DirectExchange}
   * com o nome especificado nas propriedades. Um Direct Exchange roteia mensagens
   * para as filas com base na chave de roteamento exata.
   * </p>
   * 
   * @return O exchange do RabbitMQ.
   */
  @Bean
  DirectExchange exchange() {
    logger.info("⚙️ Configurando o exchange RabbitMQ: {}", amqpExchange);
    return new DirectExchange(amqpExchange);
  }

  /**
   * Define um bean para o binding do RabbitMQ.
   * <p>
   * Cria um binding que liga a fila ao exchange usando a chave de roteamento.
   * Isso garante que as mensagens enviadas para o exchange com a chave de
   * roteamento correta sejam entregues à fila.
   * </p>
   * 
   * @param queue    O bean da fila a ser ligada.
   * @param exchange O bean do exchange a ser ligado.
   * @return O binding configurado.
   */
  @Bean
  Binding binding(Queue queue, DirectExchange exchange) {
    logger.info("⚙️ Configurando o binding RabbitMQ entre a fila e o exchange.");
    return BindingBuilder.bind(queue).to(exchange).with(amqpRoutingKey);
  }
}
