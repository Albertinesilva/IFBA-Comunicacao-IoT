<h1 align="center">🌾 Projeto IoT - Monitoramento de Sensores na Agricultura</h1>

## 📘 Disciplina: Tópicos Avançados em WEB I

## 📌 Introdução
<div align="justify">

Este projeto é uma aplicação backend desenvolvida em `Java` com `Spring Boot` para o monitoramento de dados de `sensores` em ambientes agrícolas. A aplicação coleta, armazena e publica informações de sensores como temperatura, umidade e luminosidade, simulando a comunicação via protocolos **AMQP (RabbitMQ)** e **MQTT**.
Trata-se de uma **simulação educacional** de um sistema IoT (Internet das Coisas), que representa o envio e recebimento de dados de sensores em uma fazenda inteligente. Os protocolos AMQP (RabbitMQ) e MQTT são amplamente utilizados em aplicações reais para comunicação entre dispositivos IoT e servidores, e aqui são simulados com o objetivo de facilitar o aprendizado e a demonstração do funcionamento desses sistemas sem a necessidade de infraestrutura real.
</div>

---

## 🎯 Objetivo

Demonstrar, de forma prática e simplificada, como funcionaria um backend de um sistema IoT para monitoramento de sensores em uma fazenda, com:

📡 Simulação da comunicação IoT:

- Envio de mensagens via RabbitMQ (AMQP).

- Envio de mensagens via MQTT.

🌡️ Leitura e registro de sensores:

- Simulação da leitura de sensores como temperatura, umidade e luminosidade.

- Registro das leituras com possível geração de alertas.

- Coleta e armazenamento dos dados em memória para simulação.

🌐 Exposição de APIs REST:

- Endpoint para registrar novas leituras de sensores.

- Endpoints separados para envio manual via MQTT e AMQP.

- Endpoint para consulta de todas as leituras registradas.

🔐 Segurança e autenticação:

- Implementação de Spring Security com autenticação via Bearer Token (JWT) para proteger rotas sensíveis.

---

## 🧪 Simulação

Este projeto **não se conecta a um broker real**, e sim simula todo o comportamento do RabbitMQ e MQTT **em memória**, permitindo que estudantes, professores ou curiosos possam entender o funcionamento de um sistema IoT sem a necessidade de infraestrutura adicional.

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
