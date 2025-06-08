<h1 align="center">🌾 Projeto IoT - Monitoramento de Sensores na Agricultura</h1>

## 📘 Disciplina: Tópicos Avançados em WEB I

## 📌 Introdução

Este projeto é uma aplicação backend desenvolvida em Java com Spring Boot para o monitoramento de dados de sensores em ambientes agrícolas. A aplicação coleta, armazena e publica informações de sensores de temperatura, umidade e luminosidade, simulando a comunicação via protocolos MQTT e RabbitMQ (AMQP). O sistema visa facilitar a gestão e análise em tempo real das condições ambientais para otimizar processos agrícolas.

---

## 🎯 Objetivo

O principal objetivo do projeto é criar uma plataforma robusta que permita:

- 🌡️ Coleta e armazenamento de dados de sensores ambientais.
- 🔄 Simulação de comunicação com protocolos MQTT e RabbitMQ.
- 🌐 Exposição de APIs REST para envio e consulta de dados.
- 🔐 Implementação de segurança para rotas sensíveis.
- ⚙️ Facilitar integrações futuras com sistemas externos.

---

## 🏗️ Arquitetura

📂 O projeto está organizado em pacotes seguindo boas práticas:

```java
projeto/
├── amqp/
│ └── AmqpPublisher.java # Publicador AMQP
├── config/
│ └── DataInitializer.java # Inicialização de dados
├── controller/
│ ├── dto/
│ │ └── SensorResponse.java # DTO de resposta dos sensores
│ ├── RabbitSimulationController.java # Controlador para simulação RabbitMQ
│ └── SensorController.java # Controlador de sensores
├── model/
│ └── SensorData.java # Modelo dos dados de sensores
├── mqtt/
│ └── MqttPublisher.java # Publicador MQTT
├── rabbitmq/
│ └── simulation/
│ ├── InMemoryRabbitListener.java # Listener RabbitMQ simulado
│ └── InMemoryRabbitTemplate.java # Template RabbitMQ simulado
├── repository/
│ └── SensorDataRepository.java # Repositório para acesso a dados
├── security/
│ └── SecurityConfig.java # Configuração de segurança
├── service/
│ └── SensorService.java # Regras de negócio para sensores
└── IoTApplication.java # Classe principal de inicialização
├── resources/
│ ├── application.properties # Configurações do H2


[🧑‍💻 Frontend ou Cliente REST (Postman, Angular, etc.)]
                     |
                     ▼
 [🔐 SensorController (com Auth + HTTP Endpoint)]
                     |
                     ▼
 [🧠 SensorService (Regras de negócio / Encaminhamento)]
                     |
                     ▼
 [💬 SensorResponse DTO (dto/view - Mensagem + Protocolo + SensorData)]
                     |
                     ▼
 [💾 SensorDataRepository (JPA CRUD - Simulado com H2)]
                     |
                     ▼
 [🗃️ Banco de Dados (Simulado - H2, PostgreSQL...)]

          ↙                            ↘
 [📡 MqttPublisher (Simulado)]   [📨 AmqpPublisher (Simulado)]
        |                               |
        ▼                               ▼
 [📶 Mosquitto Broker (Simulado)]   [🐇 RabbitMQ Broker (Simulado)]

      ↘                               ↙
[📥 RabbitSimulationController (Mock de Broker)]
                     |
                     ▼
 [🧪 InMemoryRabbitTemplate → InMemoryRabbitListener (Simulação completa)]

```

---

## 🛠️ Tecnologias Utilizadas

- ☕ **Java 17+**
- 🌱 **Spring Boot 3.5.0**
- 🔐 **Spring Security**
- 📡 **MQTT (simulado)**
- 📬 **RabbitMQ/AMQP (simulado)**
- 🧵 **Concurrent Collections**
- 🐘 **Maven**
- 📫 **Postman**

---

## 🚀 Funcionalidades Principais

- 📥 Envio/recebimento de mensagens via RabbitMQ (em memória).
- 📤 Publicação de dados via MQTT.
- 🔍 APIs REST para sensores.
- 🔐 Segurança com autenticação básica.
- 🧩 Arquitetura modular e extensível.

---

## ▶️ Como Executar

1. 📂 Clone este repositório:

git clone https:

2. ⚙️ Configure Java 17+ e Maven.

3. 🏃 Execute o projeto:

4. 🧪 Teste as APIs com Postman:

- `POST http://localhost:8080/api/rabbit/send?msg=HelloRabbit`

5. 📄 Verifique os logs para visualizar mensagens recebidas.

---## 📚 Documentação

| 🌎 LinkedIn                                                              | 👨‍💻 **Autor**                                                                 |
| ------------------------------------------------------------------------ | ---------------------------------------------------------------------------- |
| [LinkedIn](https://www.linkedin.com/in/albert-backend-java-spring-boot/) | [Albert Silva](https://www.linkedin.com/in/albert-backend-java-spring-boot/) |
