package com.ifba.web.iot.api.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MqttAmqpConfig {

  private static final Logger logger = LoggerFactory.getLogger(MqttAmqpConfig.class);

  @Value("${mqtt.url}")
  private String mqttUrl;

  @Value("${mqtt.username}")
  private String mqttUser;

  @Value("${mqtt.password}")
  private String mqttPass;

  @Value("${mqtt.topic}")
  private String mqttTopic;

  // Configura o canal de entrada para mensagens MQTT
  @Bean
  public MessageChannel mqttInputChannel() {
    return new DirectChannel();
  }

  // Configura a fábrica de clientes MQTT.
  // Usando MqttConnectOptions para definir a URI do servidor.
  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    options.setServerURIs(new String[] { mqttUrl }); // O método setServerURIs() está na classe MqttConnectOptions
    options.setUserName(mqttUser);
    options.setPassword(mqttPass.toCharArray());
    factory.setConnectionOptions(options); // Passa as opções de conexão para a fábrica
    return factory;
  }

  // Adaptador que se conecta ao broker MQTT e encaminha as mensagens para o canal
  // de entrada
  @Bean
  public MqttPahoMessageDrivenChannelAdapter inbound() {
    MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("my-spring-client-id",
        mqttClientFactory(), mqttTopic);
    adapter.setCompletionTimeout(5000);
    adapter.setQos(1);
    adapter.setOutputChannel(mqttInputChannel());
    return adapter;
  }
}