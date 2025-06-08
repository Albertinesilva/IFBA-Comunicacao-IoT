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

- 📂 Estrutura do Projeto

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

```
---

## 🧩 Diagrama de Arquitetura

```java

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
- 🗄️ **H2 Database (Banco de Dados em Memória)**
- 📫 **Postman**

---

## 🚀 Funcionalidades Principais

- 📥 Envio/recebimento de mensagens via RabbitMQ (em memória).
- 📤 Publicação de dados via MQTT.
- 🔍 APIs REST para sensores.
- 🔐 Segurança com autenticação básica.
- 🧩 Arquitetura modular e extensível.

---

## 🔌 Endpoints Principais:

`GET /api/sensores` Lista todas as leituras registradas.

📥 Requisição:
Nenhum corpo necessário.

📤 Resposta:

```json

[
    {
        "id": 1,
        "sensor": "temperatura",
        "valor": 23.285554905369192,
        "unidade": "C",
        "timestamp": "2025-06-07T22:30:55.961079"
    },
    {
        "id": 2,
        "sensor": "umidade",
        "valor": 47.44693101621682,
        "unidade": "%",
        "timestamp": "2025-06-07T22:30:56.057646"
    },
    {
        "id": 3,
        "sensor": "luminosidade",
        "valor": 308.20383252732455,
        "unidade": "lx",
        "timestamp": "2025-06-07T22:30:56.059667"
    }
]
```

`POST /api/sensores` Registra uma nova leitura de sensor. A lógica interna avalia o tipo de sensor e direciona a mensagem ao protocolo adequado (MQTT ou AMQP), podendo emitir alertas.

📥 Requisição (JSON):

```json
{
  "sensor": "temperatura",
  "valor": 38.6
}
```
📤 Resposta (com alerta):
```json
{
    "message": "🌡️ Alerta! Temperatura elevada detectada no campo. Verifique as condições da lavoura.",
    "data": {
        "id": 31,
        "sensor": "temperatura",
        "valor": 38.6,
        "unidade": "C",
        "timestamp": "2025-06-08T13:28:50.3789898"
    },
    "protocolo": "MQTT >> Enviando dados de temperatura para o sistema de monitoramento da fazenda: 38.6 C"
}
```
📥 Exemplo com valor normal:
```json
{
  "sensor": "temperatura",
  "valor": 25.6
}
```
📤 Resposta:
```json
{
    "message": "✅ Leitura registrada com sucesso na fazenda.",
    "data": {
        "id": 32,
        "sensor": "temperatura",
        "valor": 25.6,
        "unidade": "C",
        "timestamp": "2025-06-08T13:29:41.3520463"
    },
    "protocolo": "MQTT >> Enviando dados de temperatura para o sistema de monitoramento da fazenda: 25.6 C"
}
```
📥 Exemplo com sensor de umidade:
```json
{
  "sensor": "umidade",
  "valor": 25.6
}
```
📤 Resposta:
```json
{
    "message": "✅ Leitura registrada com sucesso na fazenda.",
    "data": {
        "id": 33,
        "sensor": "umidade",
        "valor": 25.6,
        "unidade": "%",
        "timestamp": "2025-06-08T13:30:20.4603612"
    },
    "protocolo": "AMQP >> Umidade do ar monitorada: 25.6 %"
}
```
📥 Exemplo com sensor de luminosidade:
```json
{
  "sensor": "luminosidade",
  "valor": 25.6
}
```
📤 Resposta:
```json
{
    "message": "✅ Leitura registrada com sucesso na fazenda.",
    "data": {
        "id": 34,
        "sensor": "luminosidade",
        "valor": 25.6,
        "unidade": "lx",
        "timestamp": "2025-06-08T13:30:44.3174017"
    },
    "protocolo": "AMQP >> Nível de luz solar captado: 25.6 lx"
}
```
---
`Post/api/sensores/enviar/amqp` Simula o envio de uma leitura de sensor utilizando o protocolo AMQP (RabbitMQ) diretamente.

📥 Requisição (JSON):

🌫️ Umidade (°C):

```json
{
  "sensor": "umidade",
  "valor": 27.8,
  "unidade": "°C"
}

```
📤 Resposta:
```json
📡 AMQP >> Umidade do ar monitorada: 27.8 °C
```

🔆 Luminosidade (lx):
```json
{
  "sensor": "luminosidade",
  "valor": 30.0,
  "unidade": "lx"
}
```
📤 Resposta:
```json
📡 AMQP >> Nível de luz solar captado: 30.0 lx
```

🌫️ Umidade (%):
```json
{
  "sensor": "umidade",
  "valor": 10.8,
  "unidade": "%"
}
```
📤 Resposta:
```json
📡 AMQP >> Umidade do ar monitorada: 10.8 %
````

Post localhost:8080/api/sensores/enviar/mqtt

Post localhost:8080/api/rabbit/send?msg=HelloRabbit

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
