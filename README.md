<h2 align="center">🌾Backend IoT Simulado Protocolos para Agricultura Inteligente com Spring Boot</h2>

### 📘 Disciplina: Tópicos Avançados em Web I

### 📌 Introdução

<div align="justify">
  
Este projeto é uma aplicação backend robusta desenvolvida em `Java` com `Spring Boot` para o monitoramento de dados de sensores em ambientes agrícolas. A aplicação coleta, armazena e publica informações de sensores como temperatura, umidade e luminosidade, simulando a comunicação via protocolos `AMQP (RabbitMQ)` e `MQTT`.

Além de uma simulação com os protocolos `AMQP` (`RabbitMQ`) e `MQTT`, o projeto também possui uma implementação funcional com integração real a brokers de mensagens. Essa nova arquitetura utiliza clientes e serviços reais para garantir que o fluxo de dados entre os dispositivos `IoT` simulados e o backend seja efetivo e confiável.

A interação com os dados da `API` é feita por um frontend construído em `JavaScript` que utiliza requisições `fetch` para consumir e exibir as informações em tempo real.

Trata-se de uma simulação educacional e funcional de um sistema `IoT` (Internet das Coisas), que representa o envio e recebimento de dados de sensores em uma fazenda inteligente. Os protocolos `AMQP` e `MQTT` são amplamente utilizados em aplicações reais para comunicação entre dispositivos IoT e servidores, e aqui são simulados com o objetivo de facilitar o aprendizado e a demonstração do funcionamento desses sistemas sem a necessidade de infraestrutura real.

📄 Para ver os resultados e registros do sistema em execução, [clique aqui](https://github.com/Albertinesilva/IFBA-Comunicacao-IoT/blob/main/LOG.md) para ver os logs detalhados do projeto.

🖼️ Para visualizar as telas e a interface do sistema, [clique aqui](https://github.com/Albertinesilva/IFBA-Comunicacao-IoT/blob/main/FRONT-END.md) para ver as capturas de tela do frontend.

📚 Para acessar a documentação técnica completa do backend gerada com JavaDocs, basta rodar o comando:
```java
mvn javadoc:javadoc
```
Os arquivos serão gerados em:
```java
/target/site/apidocs/index.html
```

</div>

---

### 🎯 Objetivo

Demonstrar, de forma prática e simplificada, como funcionaria um backend de um sistema IoT para monitoramento de sensores em uma fazenda, com uma simulação realista e funcional que abrange:

↔️ Integração e Orquestração de Protocolos Reais:

- **Integração de Mensageria Real:** Implementar clientes reais como o `Spring` Integration para `MQTT` e `Spring AMQP` para `RabbitMQ`.

- **Orquestração do Fluxo de Dados:** Criar a ponte entre os `brokers`, demonstrando como mensagens de um dispositivo (`MQTT`) podem ser roteadas e processadas por um serviço central (`AMQP`).

- **Configuração de Ambiente:** Utilizar variáveis de ambiente para gerenciar credenciais e endpoints dos `brokers`, elevando o nível de segurança e boas práticas do projeto.

- **Arquitetura Distribuída:** Manter a simulação das camadas `Edge`, `Fog` e `Cloud` agora com um fluxo de dados real.

📡 Simulação da comunicação IoT:

- **Arquitetura Distribuída:** Simulação das camadas de processamento `Edge`, `Fog` e `Cloud` para demonstrar o fluxo de dados e a divisão de responsabilidades.

- **Comunicação IoT:** Simulação do envio de mensagens via `RabbitMQ (AMQP)` e `MQTT`.

- **Processamento e Registro de Dados:** Simulação da leitura de `sensores`, registro das leituras em tempo real, aplicação de lógica de negócio e geração de alertas automatizados.

🌡️ Leitura e registro de sensores:

- **Simulação da leitura:** Uma rotina agendada `(Scheduler)` simula a coleta de dados de `sensores` de `temperatura`, `umidade` e `luminosidade` a cada 10 segundos, injetando leituras realistas no sistema.

- **Registro das leituras:** Registro das leituras com a possibilidade de geração de alertas automatizados.

- **Coleta e armazenamento:** Coleta e armazenamento dos dados em memória para simulação, utilizando o banco de dados `H2`.

🌐 Exposição de APIs REST:

- **Endpoints de autenticação:** Endpoints para `autenticação` (login e registro de usuário) usando `Bearer Token` (JWT).

- **Endpoints de dados de sensores:** Endpoints para registrar novas leituras e para a consulta de todas as leituras registradas via `MQTT` e `AMQP`.

- **Endpoints de controle de sensores:** Endpoint para ativar e desativar a simulação de sensores em tempo real.

- **Endpoints de mensageria:** Endpoints separados para envio manual de mensagens via `RabbitMQ` e `MQTT`.

- **Endpoints de integração:** Endpoint para integração com uma `API externa` para buscar dados de cidades por `nome` e `ID`.

🔐 Segurança e autenticação:

- Implementação: Implementação de `Spring Security` com autenticação via Bearer `Token (JWT)` para proteger rotas sensíveis e garantir a integridade do sistema.

---

### ⚙️ Funcionalidades

Visão Geral das Funcionalidades:

Este projeto implementa uma solução de monitoramento de sensores IoT para a agricultura, oferecendo as seguintes funcionalidades principais:

-  **Simulação de Sensores (Camada Edge):** Uma rotina agendada (`Scheduler`) simula a coleta de dados de sensores de temperatura, umidade e luminosidade a cada 10 segundos, injetando leituras realistas no sistema.

-  **Processamento e Publicação de Dados (Camada Fog):** Cada leitura de sensor é processada e publicada em tempo real, utilizando os protocolos de mensageria `AMQP` e `MQTT` para garantir a comunicação eficiente com outros sistemas.

-  **Detecção de Alertas em Tempo Real:** O sistema detecta automaticamente anomalias nos dados dos sensores, como temperaturas elevadas, umidade fora da faixa ideal ou baixa luminosidade. Quando uma anomalia é identificada, um alerta é gerado e, opcionalmente, salvo no banco de dados.

-  **Controle Global de Alertas:** Os alertas gerados são salvos no banco de dados. Um endpoint `RESTful` (`PUT /api/sensores/alertas/status/{status}`) permite que a funcionalidade de salvamento seja ativada ou desativada globalmente, dando ao usuário o controle total sobre a persistência dos alertas.
---

### 🌐 Protocolos

Justificativa da escolha dos protocolos de comunicação: foram selecionados três protocolos, cada um com um propósito específico:

- **HTTP REST:** Usado para a comunicação cliente-servidor, ideal para interações diretas e pontuais, como a consulta de dados e a alteração de configurações.

- **MQTT (Message Queuing Telemetry Transport):** Escolhido para a comunicação com os dispositivos IoT simulados. Sua leveza e baixa latência o tornam perfeito para o envio de dados em tempo real em redes com largura de banda limitada.

- **AMQP (Advanced Message Queuing Protocol):** Utilizado para a comunicação interna entre os serviços da aplicação, garantindo uma alta confiabilidade na entrega de mensagens críticas, como os alertas gerados pelo sistema.

---

### 🧪 Simulação de Comunicação IoT

Este projeto **não se conecta a um broker real**, e sim simula todo o comportamento do `RabbitMQ` e `MQTT` **em memória**, permitindo que estudantes, professores ou curiosos possam entender o funcionamento de um sistema IoT sem a necessidade de infraestrutura adicional.

---

### 🏗️ Arquitetura do Sistema e Fluxo de Dados

Arquitetura de Processamento Distribuído (Edge, Fog e Cloud)

A arquitetura do projeto simula a distribuição do processamento, uma característica fundamental em sistemas de IoT:

1. **Camada Edge (Borda):** A classe SensorScheduler simula um dispositivo na borda da rede (como um microcontrolador ou um sensor inteligente) que coleta dados brutos em tempo real e os envia para a camada de processamento local.

2. **Camada Fog (Névoa):** A classe SensorDataService atua como um hub intermediário. Nela, ocorre o processamento inicial dos dados recebidos da camada Edge, como a verificação de alertas e a decisão de quais dados devem ser publicados e salvos.

3. **Camada Cloud (Nuvem):** A persistência dos dados no banco de dados H2 (simulando um banco de dados em nuvem) representa a camada Cloud. É aqui que os dados são armazenados de forma centralizada para análise de longo prazo, visualização e tomada de decisões estratégicas.

- 📂 Estrutura do Projeto

```java
projeto/
├── amqp/
│ └── AmqpPublisher.java         // Gerencia o envio de mensagens via AMQP
├── config/
│ └── CorsConfig.java            // Configuração de CORS para a API
├── controller/
│ ├── dto/                       // Data Transfer Objects (DTOs) para a API
│ ├── form/                      // Formulários de entrada para a API
│ ├── view/                      // Visualizações de resposta
│ ├── AuthController.java        // Controlador para autenticação e registro de usuários
│ ├── RabbitSimulationController.java // Controlador para simular mensagens RabbitMQ
│ ├── SensorDataController.java    // Controlador para gerenciar dados de sensores
│ └── WeatherController.java     // Controlador para dados de clima de API externa
├── jwt/
│ ├── JwtAuthenticationFilter.java // Filtro de autenticação JWT
│ └── JwtUtil.java               // Utilitário para manipulação de tokens JWT
├── model/
│ ├── Alert.java                 // Modelo de dados para alertas
│ ├── SensorData.java            // Modelo de dados para leituras de sensores
│ └── Usuario.java               // Modelo de dados para usuários
├── mqtt/
│ ├── MqttPublisher.java         // Gerencia o envio de mensagens via MQTT
│ └── MqttToAmqpBridge.java      // Ponte que retransmite mensagens de MQTT para AMQP
├── rabbitmq/
│ └── simulation/
│ ├── InMemoryRabbitListener.java // Listener simulado para o RabbitMQ
│ └── InMemoryRabbitTemplate.java // Template simulado para o RabbitMQ
├── repository/
│ ├── AlertRepository.java       // Repositório para acesso a dados de alertas
│ ├── SensorDataRepository.java  // Repositório para acesso a dados de sensores
│ └── UsuarioRepository.java     // Repositório para acesso a dados de usuários
├── security/
│ └── SecurityConfig.java        // Configuração principal de segurança
├── service/
│ ├── AlertService.java          // Lógica de negócio para alertas
│ ├── SensorDataService.java     // Lógica de negócio para dados de sensores
│ ├── SensorScheduler.java       // Simulação da coleta de dados em tempo real (Scheduler)
│ ├── UsuarioService.java        // Lógica de negócio para usuários
│ └── WeatherService.java        // Lógica de negócio para a API de clima
└── IoTApplication.java          // Classe principal da aplicação
├── resources/
│ ├── application.properties # Configurações do H2

```
---

### 🧩 Diagrama de Arquitetura

```java
[🧑‍💻 Frontend ou Cliente REST (Postman, JavaScript API fetch, Angular, etc.)]
             | (Requisições HTTP)
             ▼
[🔐 Spring Security / Filtro JWT (Autenticação)]
             | (JWT Bearer Token)
             ▼
[🌐 Controller (AuthController, SensorDataController, etc.)]
             | (Validação e Roteamento)
             ▼
[🧠 Service (UsuarioService, SensorDataService, etc.)]
             | (Lógica de Negócio)
             |
             |───────┐ (Chamadas Assíncronas)
             |       |
             ▼       ▼
[💾 Repository (UsuarioRepository, SensorDataRepository, etc.)]
|            |
|            ▼
|───────>[🗃️ Banco de Dados (Simulado H2)]
|
| (Outras chamadas de serviço)
|
|───────┐ (Simulação de Mensagens)
|       |
|       ▼
|  [📡 MqttPublisher]
|       |
|       ▼
|  [📨 AmqpPublisher]
|
|───────┐ (Simulação de Broker)
|       |
|       ▼
|  [📥 RabbitSimulationController]
|       |
|       ▼
|  [🧪 InMemoryRabbitTemplate]
|       |
|       ▼
|  [👂 InMemoryRabbitListener]
|
|───────┐ (Scheduler)
|       |
|       ▼
[⏰ SensorScheduler (Simulação de Coleta de Dados)]

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

- 📥 Envio/recebimento de mensagens via `RabbitMQ` (em memória).
- 📤 Publicação de dados via `MQTT`.
- 🔍 APIs `REST` para sensores.
- 🔐 Segurança com autenticação JWT.
- 🧩 Arquitetura modular e extensível.

---

### ⚙️ Configuração do Projeto

💾 Banco de Dados (H2)

O projeto utiliza o banco de dados em memória `H2` para facilitar testes sem necessidade de um banco externo. A configuração do datasource é feita da seguinte forma:

```properties
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
🌦️ Integração com OpenWeatherMap

Para buscar dados de clima, o projeto se integra com a `API` do `OpenWeatherMap`. A `URL` e a chave de acesso são configuradas, com a chave sendo injetada por uma variável de ambiente:

```properties
openweathermap.api.url=https://api.openweathermap.org/data/2.5/weather
openweathermap.api.key=${CHAVE_API_WEATHER}
```
🔒 Segurança (HTTPS/SSL)

O servidor está configurado para usar `HTTPS` na porta `8443` para garantir a segurança da comunicação. O certificado `SSL` (`keystore.p12`) é referenciado a partir do classpath do projeto e suas senhas são carregadas de variáveis de ambiente:

```properties
# Ativar HTTPS
server.port=8443
server.ssl.enabled=true

# Caminho para o certificado (JKS ou PKCS12)
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SERVER_SSL}
server.ssl.key-store-type=PKCS12

# Nome comum do certificado
server.ssl.key-alias=${SERVER_SSL_CERTIFICADO}
```

📡 Configurações de Mensageria `(MQTT e AMQP)`

Este bloco define as variáveis de ambiente utilizadas para a comunicação do sistema com `brokers` de mensagens.

- `MQTT`: protocolo leve usado principalmente para dispositivos `IoT`, configurado com `URL`, credenciais e tópico de `publicação/assinatura`.

- `AMQP`: protocolo robusto usado para filas de mensagens, configurado com `exchange` e `routing key` para roteamento das mensagens.

```properties
mqtt.url=${MQTT_URL}
mqtt.username=${MQTT_USER}
mqtt.password=${MQTT_PASS}
mqtt.topic=${MQTT_TOPIC}

amqp.exchange=${AMQP_EXCHANGE}
amqp.routing.key=${AMQP_ROUTING_KEY}
```
📝 Logging

O sistema de `logging` está configurado para registrar logs importantes em arquivo `myapp.log`, com limite de tamanho e histórico para rotação dos arquivos:

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
https://localhost:8443/api/**
```

---

### 🔌 Endpoints Principais:

`Post: /api/auth/register`, Cadastra um novo usuário no sistema.

📥 Requisição (JSON):

```json
{
    "nome": "nome",
    "email": "iot@ifba.edu.br",
    "senha": "123456"
}
```

📤 Resposta, (DTO com dados mascarados):

```json
{
    "nomeMascarado": "n**e",
    "emailMascarado": "i***ifba.edu.br"
}
```

---

`Post: /api/auth`, Login no sistema

📥 Requisição (JSON):

```json
{
    "email": "iot@ifba.edu.br",
    "senha": "123456"
}
```

📤 Resposta, (Bearer Token):

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGJlcnRpbmVzaWx2YUBnbWFpbC5jb20iLCJpYXQiOjE3NTU3MDQwNjIsImV4cCI6MTc1NTcwNzY2Mn0.xghotnVpGEK1Z6_GWQveaEXoVmxg4jGmRWku5RZRrhg"
}
```

---

GET /api/clima
Obtém dados de clima de uma cidade pelo nome, sigla ou ID.

📥 Requisição (Parâmetros de Query):

- Por nome de Estado ou Sigla:
- ?city=Bahia
- ?cyti=BA

- Por nome da cidade:
- ?city=Recife

- Por ID da cidade (Rio de Janeiro): ?id=3451190

📤 Resposta de Sucesso (200 OK):

**Nome do Estado**: `Bahia` ou `BA`
```json
{
    "main": {
        "temp": 27.42,
        "humidity": 26
    },
    "weather": [
        {
            "description": "nuvens dispersas"
        }
    ],
    "wind": {
        "speed": 3.66
    },
    "name": "Estado de Bahia"
}
```
**Nome da cidade**: `Recife`
```json
{
    "main": {
        "temp": 28.02,
        "humidity": 78
    },
    "weather": [
        {
            "description": "nuvens dispersas"
        }
    ],
    "wind": {
        "speed": 6.69
    },
    "name": "Recife"
}
```
**Pelo `ID`**: `3451190`
```json
{
    "main": {
        "temp": 30.39,
        "humidity": 41
    },
    "weather": [
        {
            "description": "céu limpo"
        }
    ],
    "wind": {
        "speed": 4.63
    },
    "name": "Rio de Janeiro"
}
```

📤 Resposta de Erro (404 Not Found):

❌ Erro ao buscar dados de clima da API do OpenWeather: 404 Not Found on GET request for "https://api.openweathermap.org/data/2.5/weather": "{"cod":"404","message":"city not found"}"

❌ Não foi possível obter os dados de clima para a cidade: Porto Alegre

---

`GET: /api/sensores`, Lista todas as leituras registradas.

📥 Requisição:
Nenhum corpo necessário.

📤 Resposta (Lista de leituras):

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

---

`Put /api/sensores/alertas/status/false`, Desabilita o salvamento no banco.

📥 Requisição:
Nenhum corpo necessário.

📤 Resposta (com alerta):
```json
🛑 Salvamento de alertas desativado.
```

---

`Put /api/sensores/alertas/status/true`, Habilita o salvamento no banco.

📥 Requisição:
Nenhum corpo necessário.

📤 Resposta (com alerta):

```json
✅ Salvamento de alertas ativado.
```

---

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

---

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

---

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

---

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

---

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

---

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

---

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

---

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

### 🔐 Segurança (Spring Security e JWT)

Este projeto implementa um esquema de segurança mais robusto utilizando `Spring Security` com `JSON Web Tokens` (JWT) para autenticação e autorização, garantindo que apenas usuários autenticados possam acessar as funcionalidades principais da `API`.

📊 Visão Geral

A arquitetura de segurança segue o fluxo padrão de JWT:

1. Um usuário envia credenciais (usuário e senha) para uma rota de login.

2. Em caso de sucesso, o servidor gera um `JWT` (Bearer Token) e o retorna ao cliente.

3. Para acessar rotas protegidas, o cliente deve incluir este `token` no cabeçalho `Authorization` de todas as requisições subsequentes.

4. O `JwtAuthenticationFilter` intercepta as requisições, valida o `token` e autentica o usuário para que o acesso à rota seja permitido.

A senha do usuário, por ser um dado sensível, é `criptografada` usando `BCrypt` antes de ser armazenada no banco de dados, o que é uma prática essencial para ambientes de produção.

### 🔓 Rotas de Autenticação (Públicas)

A única rota pública do projeto, que não exige `autenticação`, é a de autenticação. Isso permite que novos usuários se registrem e que usuários existentes façam `login` para obter um `token`.

- `POST /api/auth/register` (Registro de novo usuário)

- `POST /api/auth/login` (Obtenção do JWT)

#### 🔐 Rotas Protegidas

Todas as demais rotas da aplicação estão protegidas e exigem um `JWT` válido no cabeçalho `Authorization` para serem acessadas.

- /api/sensores/**

- /api/rabbit/**

- /api/clima/**

Qualquer outra rota que não seja listada em "Rotas de Autenticação (Públicas)".

Exemplo de requisição protegida:

`GET /api/sensores`

`Host: localhost:8443`

`Authorization: Bearer <seu-jwt-token-aqui>`

---

### ✅ Conclusão:

Durante o desenvolvimento desta atividade para a disciplina de Tópicos Avançados em Web I, foi possível consolidar o entendimento da importância estratégica de um backend simulado (mock) para o ecossistema de Internet das Coisas (IoT). A criação de endpoints que fornecem dados fictícios mostrou-se crucial para a fase de testes, eliminando a dependência de hardware físico e agilizando o ciclo de desenvolvimento.

O processo permitiu o aprimoramento de habilidades essenciais em comunicação entre cliente e servidor, modelagem de dados e arquitetura de APIs RESTful. A estruturação dos endpoints e a organização dos dados foram fundamentais para representar de forma eficaz um sistema de agricultura inteligente, tornando tangível o fluxo de informações que viria de sensores reais.

Em última análise, esta experiência contribuiu significativamente para a compreensão dos conceitos que regem o desenvolvimento de aplicações web que interagem com dados externos, fortalecendo a base para a implementação de soluções de software mais complexas e aplicáveis a cenários do mundo real.

---

| 🌎 LinkedIn                                                              | 👨‍💻 **Autor**                                                                 |
| ------------------------------------------------------------------------ | ---------------------------------------------------------------------------- |
| [LinkedIn](https://www.linkedin.com/in/albert-backend-java-spring-boot/) | [Albert Silva](https://www.linkedin.com/in/albert-backend-java-spring-boot/) |
