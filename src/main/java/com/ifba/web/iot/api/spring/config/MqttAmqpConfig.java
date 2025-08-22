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

/**
 * Classe de configuração Spring para integração com MQTT e o ecossistema Spring
 * Integration.
 * <p>
 * Esta classe é responsável por configurar e gerenciar os componentes
 * necessários
 * para que a aplicação se conecte a um broker MQTT e receba mensagens de um
 * tópico específico.
 * Ela define beans para a fábrica de clientes MQTT, o canal de entrada de
 * mensagens
 * e o adaptador de canal que escuta o tópico MQTT.
 * </p>
 */
@Configuration
public class MqttAmqpConfig {

  private static final Logger logger = LoggerFactory.getLogger(MqttAmqpConfig.class);

  /**
   * URL do broker MQTT, injetada a partir das propriedades da aplicação.
   */
  @Value("${mqtt.url}")
  private String mqttUrl;

  /**
   * Nome de usuário para autenticação no broker MQTT.
   */
  @Value("${mqtt.username}")
  private String mqttUser;

  /**
   * Senha para autenticação no broker MQTT.
   */
  @Value("${mqtt.password}")
  private String mqttPass;

  /**
   * Tópico MQTT para o qual o adaptador de entrada irá se inscrever.
   */
  @Value("${mqtt.topic}")
  private String mqttTopic;

  /**
   * Define um bean para o canal de mensagens de entrada.
   * <p>
   * Este canal é o destino para o qual o
   * {@link MqttPahoMessageDrivenChannelAdapter}
   * enviará as mensagens recebidas do broker MQTT. Um {@link DirectChannel}
   * é usado para garantir que a mensagem seja entregue a um único assinante.
   * </p>
   * 
   * @return O canal de mensagens de entrada.
   */
  @Bean
  public MessageChannel mqttInputChannel() {
    return new DirectChannel();
  }

  /**
   * Define um bean para a fábrica de clientes MQTT.
   * <p>
   * Configura as opções de conexão do cliente MQTT, como a URL do servidor,
   * nome de usuário e senha, utilizando as propriedades da aplicação.
   * </p>
   * 
   * @return A fábrica de clientes MQTT configurada.
   */
  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    logger.info("⚙️ Configurando a fábrica de clientes MQTT com URL: {}", mqttUrl);
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    options.setServerURIs(new String[] { mqttUrl });
    options.setUserName(mqttUser);
    options.setPassword(mqttPass.toCharArray());
    factory.setConnectionOptions(options);
    return factory;
  }

  /**
   * Define um bean para o adaptador de canal de entrada acionado por mensagens
   * MQTT.
   * <p>
   * Este adaptador se conecta ao broker MQTT, se inscreve no tópico configurado
   * e transfere as mensagens recebidas para o canal de entrada.
   * </p>
   * 
   * @return O adaptador de entrada configurado.
   */
  @Bean
  public MqttPahoMessageDrivenChannelAdapter inbound() {
    logger.info("⚙️ Configurando o adaptador de entrada MQTT para o tópico: {}", mqttTopic);
    MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("my-spring-client-id",
        mqttClientFactory(), mqttTopic);
    adapter.setCompletionTimeout(5000);
    adapter.setQos(1);
    adapter.setOutputChannel(mqttInputChannel());
    return adapter;
  }
}
