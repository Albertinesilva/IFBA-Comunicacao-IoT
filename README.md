<h2 align="center">🌾Backend IoT Simulado Protocolos para Agricultura Inteligente com Spring Boot</h2>

### 📘 Disciplina: Tópicos Avançados em Web I

### 📌 Introdução

<div align="justify">

Este projeto é uma aplicação backend desenvolvida em `Java` com `Spring Boot` para o monitoramento de dados de `sensores` em ambientes agrícolas. A aplicação coleta, armazena e publica informações de sensores como temperatura, umidade e luminosidade, simulando a comunicação via protocolos **AMQP (RabbitMQ)** e **MQTT**.
Trata-se de uma **simulação educacional** de um sistema IoT (Internet das Coisas), que representa o envio e recebimento de dados de sensores em uma fazenda inteligente. Os protocolos AMQP (RabbitMQ) e MQTT são amplamente utilizados em aplicações reais para comunicação entre dispositivos IoT e servidores, e aqui são simulados com o objetivo de facilitar o aprendizado e a demonstração do funcionamento desses sistemas sem a necessidade de infraestrutura real.

📄 Para ver os resultados e registros do sistema em execução, [clique aqui](https://github.com/Albertinesilva/IFBA-Comunicacao-IoT/blob/main/LOG.md) para ver os logs detalhados do projeto.
</div>

---

### 🎯 Objetivo

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

### 🧪 Simulação de Comunicação IoT

Este projeto **não se conecta a um broker real**, e sim simula todo o comportamento do RabbitMQ e MQTT **em memória**, permitindo que estudantes, professores ou curiosos possam entender o funcionamento de um sistema IoT sem a necessidade de infraestrutura adicional.

---

### 🏗️ Arquitetura do Sistema e Fluxo de Dados

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

### 🧩 Diagrama de Arquitetura

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

### 🛠️ Tecnologias Utilizadas

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

### 🚀 Funcionalidades Principais

- 📥 Envio/recebimento de mensagens via RabbitMQ (em memória).
- 📤 Publicação de dados via MQTT.
- 🔍 APIs REST para sensores.
- 🔐 Segurança com autenticação básica.
- 🧩 Arquitetura modular e extensível.

---

### ⚙️ Configuração do Projeto

O projeto utiliza o banco de dados em memória H2 para facilitar testes sem necessidade de um banco externo. A configuração do datasource é feita da seguinte forma:

```properties
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
```

Console do H2 está habilitado e disponível em /h2-console:

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

O sistema de logging está configurado para registrar logs importantes em arquivo myapp.log, com limite de tamanho e histórico para rotação dos arquivos:

```properties
logging.level.org.springframework=INFO
logging.level.root=WARN
logging.level.org.springframework.web=DEBUG
logging.level.com.ifba.web.iot.api.spring=INFO

logging.file.name=myapp.log
logging.logback.rollingpolicy.max-file-size=10MB
logging.logback.rollingpolicy.max-history=10
```

---

### ▶️ Como Executar o Projeto

1. Clone o repositório:

```bash
git clone https://github.com/Albertinesilva/IFBA-Comunicacao-IoT.git
```

2. Navegue até o diretório:

```bach
cd nome-do-projeto
```

3. Compile e execute com Maven:

```bash
 ./mvnw0 spring-boot:run
```

4. Acesse a API:

```bash
http://localhost:8080/api/sensores
```

---

### 🔌 Endpoints Principais:

`GET: /api/sensores`, Lista todas as leituras registradas.

📥 Requisição:
Nenhum corpo necessário.

📤 Resposta:

```json
[
  {
    "id": 1,
    "sensor": "temperatura",
    "valor": 23.285554905369192,
    "unidade": "°C",
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

`POST: /api/sensores`, Registra uma nova leitura de sensor. A lógica interna avalia o tipo de sensor e direciona a mensagem ao protocolo adequado (MQTT ou AMQP), podendo emitir alertas.

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
    "unidade": "°C",
    "timestamp": "2025-06-08T13:28:50.3789898"
  },
  "protocolo": "MQTT >> Enviando dados de temperatura para o sistema de monitoramento da fazenda: 38.6 °C"
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
    "unidade": "°C",
    "timestamp": "2025-06-08T13:29:41.3520463"
  },
  "protocolo": "MQTT >> Enviando dados de temperatura para o sistema de monitoramento da fazenda: 25.6 °C"
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

`POST:  /api/sensores/enviar/amqp`, Simula o envio de uma leitura de sensor utilizando o protocolo AMQP (RabbitMQ) diretamente.

📥 Requisição (JSON):

🌫️ Umidade (°C):

```json
{
  "sensor": "umidade",
  "valor": 27.8,
  "unidade": "%"
}
```

📤 Resposta:

```json
📡 AMQP >> Umidade do ar monitorada: 27.8 %
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
```

---

`POST: /api/sensores/enviar/mqtt`, Simula o envio de uma leitura de sensor utilizando o protocolo MQTT diretamente.

📥 Requisição (JSON):

🌫️ Umidade (%):

```json
{
  "sensor": "Umidade",
  "valor": 55.2,
  "unidade": "%"
}
```

📤 Resposta:

```json
📡 MQTT >> Enviando dados de Umidade para o sistema de monitoramento da fazenda: 55.2 %
```

🔆 Luminosidade (lx):

```json
{
  "sensor": "luminosidade",
  "valor": 20.2,
  "unidade": "lx"
}
```

📤 Resposta:

```json
📡 MQTT >> Enviando dados de luminosidade para o sistema de monitoramento da fazenda: 20.2 lx
```

🌡️ Temperatura (C):

```json
{
  "sensor": "temperatura",
  "valor": 20.2,
  "unidade": "°C"
}
```

📤 Resposta:

```json
📡 MQTT >> Enviando dados de temperatura para o sistema de monitoramento da fazenda: 20.2 °C
```

---

`POST: /api/rabbit/send?msg=`, Esta rota simula o envio de uma mensagem através do RabbitMQ (AMQP). A mensagem é armazenada em memória apenas para fins de simulação e demonstração do funcionamento do protocolo de mensagens assíncronas.

📥 Requisição:

```json
POST /api/rabbit/send?msg=HelloRabbit
```

📤 Resposta:

```json
Mensagem enviada: HelloRabbit
Mensagem recebida pelo listener: HelloRabbit
```

ℹ️ A mensagem é processada por um listener RabbitMQ simulado, que imprime o conteúdo recebido, demonstrando o ciclo de envio e recepção via AMQP.

---

### 🔐 Segurança (Simulação)

Este projeto utiliza uma configuração básica de segurança com Spring Security apenas para fins de simulação e testes locais. As seguintes regras estão aplicadas:

- A autenticação está habilitada nas rotas `/api/rabbit/**` e em todas as demais rotas, **exceto** `/api/sensores`.
- A autenticação utilizada é do tipo **HTTP Basic**, com um único usuário em memória:
  - **Usuário:** `usuario`
  - **Senha:** `senha123`
- A senha não está criptografada (`{noop}`), já que o foco aqui é apenas a simulação e não a segurança real em produção.

#### ⚠️ Aviso

> Esta configuração **não deve ser usada em ambientes de produção**.  
> Em produção, recomenda-se:
>
> - Uso de autenticação com JWT ou OAuth2.
> - Criptografia de senhas com `BCryptPasswordEncoder`.
> - Proteção CSRF habilitada, especialmente para aplicações web com sessões.

#### 🔓 Rotas públicas

- `GET /api/sensores`
- `GET /api/sensores/{id}` (ou qualquer subrota de `/api/sensores`)

#### 🔐 Rotas protegidas

Requerem autenticação com o usuário configurado:

- `GET/POST/etc /api/rabbit/**`
- Qualquer outra rota não listada como pública.

---

### ✅ Conclusão:

Durante o desenvolvimento desta atividade para a disciplina Tópicos Avançados em Web I, foi possível compreender a importância de estruturar um backend simulado (mock) para IoT, criando endpoints que fornecem dados fictícios para facilitar testes sem a necessidade de hardware real. O processo permitiu aprimorar habilidades na comunicação entre cliente e servidor, além da manipulação de dados para aplicações web. Assim, a atividade contribuiu para o entendimento da estruturação de endpoints e da organização dos dados para representar um sistema de agricultura inteligente. Essa experiência auxiliou no domínio de conceitos essenciais para o desenvolvimento de APIs RESTful, tornando mais claro o funcionamento de aplicações que dependem de dados externos.

---

| 🌎 LinkedIn                                                              | 👨‍💻 **Autor**                                                                 |
| ------------------------------------------------------------------------ | ---------------------------------------------------------------------------- |
| [LinkedIn](https://www.linkedin.com/in/albert-backend-java-spring-boot/) | [Albert Silva](https://www.linkedin.com/in/albert-backend-java-spring-boot/) |
