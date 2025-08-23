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

Demonstrar, de forma prática e simplificada, o funcionamento de um sistema backend de IoT para monitoramento de sensores em uma fazenda. O projeto abrange tanto o aspecto educacional e de simulação quanto uma implementação robusta para cenários de integração real.

📡 Fluxo de Dados: Simulação e Integração Real

A arquitetura do projeto foi desenvolvida para ser flexível e didática, permitindo a execução em dois modos principais: `simulação` e `integração real`. Essa abordagem facilita o aprendizado e a demonstração, ao mesmo tempo que garante a capacidade de se conectar a sistemas externos de mensageria.

🧪 Modo de Simulação

Neste modo, o sistema mantém uma simulação completa das camadas de processamento Edge, Fog e Cloud, com a divisão de responsabilidades. Isso inclui:

- **Geração de Dados:** Simulação da leitura de dados de `sensores` de `temperatura`, `umidade` e `luminosidade`.

- **Comunicação:** Simulação do envio de mensagens via `RabbitMQ` (`AMQP`) e `MQTT`, permitindo que o projeto funcione de forma independente.

- **Processamento de Dados:** Simulação da aplicação de lógica de negócio e da geração de alertas automatizados com base nas leituras dos `sensores`.

↔️ Modo de Integração Real e Orquestração

Além da simulação, o projeto foi evoluído para uma implementação funcional com `integração real` a `brokers` de mensagens. Essa nova arquitetura utiliza clientes e serviços reais para garantir que o fluxo de dados seja efetivo e confiável. Os principais pontos são:

- **Integração de Mensageria:** Implementação de clientes reais, como o Spring Integration para `MQTT` e o `Spring AMQP` para `RabbitMQ`, para a conexão direta com `brokers` externos.

- **Orquestração de Dados:** Criação de uma ponte entre os `brokers`, demonstrando como as mensagens de um dispositivo (`MQTT`) podem ser roteadas, processadas por um serviço central e reencaminhadas (`AMQP`).

- **Configuração de Ambiente:** Utilização de variáveis de ambiente para gerenciar credenciais e endpoints dos `brokers`, garantindo maior segurança e portabilidade ao projeto.

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

-  **Simulação e Coleta de Dados de Sensores (Camada Edge):** Uma rotina agendada (`Scheduler`) simula a coleta de dados de sensores de temperatura, umidade e luminosidade a cada 10 segundos, injetando leituras realistas no sistema. Este módulo pode operar tanto em modo de simulação, quanto recebendo dados de `brokers` de mensagens reais.

-  **Processamento e Publicação de Dados (Camada Fog):** Cada leitura de sensor é processada e publicada em tempo real. O sistema agora suporta tanto a simulação da mensageria quanto a integração real utilizando os protocolos `AMQP` (`RabbitMQ`) e `MQTT`. Isso garante uma comunicação eficiente com outros sistemas e reflete um cenário de produção.

-  **Detecção de Alertas em Tempo Real:** O sistema detecta automaticamente anomalias nos dados dos sensores, como temperaturas elevadas, umidade fora da faixa ideal ou baixa luminosidade. Quando uma anomalia é identificada, um alerta é gerado e, opcionalmente, salvo no banco de dados.

-  **Controle Global de Alertas:** Os alertas gerados são salvos no banco de dados. Um endpoint `RESTful` (`PUT /api/sensores/alertas/status/{status}`) permite que a funcionalidade de salvamento seja ativada ou desativada globalmente, dando ao usuário o controle total sobre a persistência dos alertas.
---

### 🌐 Protocolos

Justificativa da escolha dos protocolos de comunicação: foram selecionados três protocolos, cada um com um propósito específico:

- **HTTP REST:** Usado para a comunicação cliente-servidor, ideal para interações diretas e pontuais, como a consulta de dados e a alteração de configurações.

- **MQTT (Message Queuing Telemetry Transport):** Escolhido para a comunicação com os dispositivos IoT simulados. Sua leveza e baixa latência o tornam perfeito para o envio de dados em tempo real em redes com largura de banda limitada.

- **AMQP (Advanced Message Queuing Protocol):** Utilizado para a comunicação interna entre os serviços da aplicação, garantindo uma alta confiabilidade na entrega de mensagens críticas, como os alertas gerados pelo sistema.

---

### 🧪 Simulação e 🔗 Integração de Comunicação IoT

Este projeto oferece um Modo de Simulação que replica o comportamento dos protocolos `RabbitMQ` e `MQTT` em memória. Essa funcionalidade é ideal para fins de aprendizado, permitindo que estudantes e entusiastas compreendam a arquitetura `IoT` sem a necessidade de infraestrutura adicional.

Além disso, o projeto conta com um Modo de Integração Real, que se conecta a `brokers` de mensageria externos, demonstrando um cenário de produção completo.

---

### 🏗️ Arquitetura do Sistema e Fluxo de Dados

Arquitetura de Processamento Distribuído (Edge, Fog e Cloud)

A arquitetura do projeto simula a distribuição do processamento, uma característica fundamental em sistemas de IoT:

1. **Camada Edge (Borda):** A classe SensorScheduler simula um dispositivo na borda da rede (como um microcontrolador ou um sensor inteligente) que coleta dados brutos em tempo real e os envia para a camada de processamento local.

2. **Camada Fog (Névoa):** A classe SensorDataService atua como um hub intermediário. Nela, ocorre o processamento inicial dos dados recebidos da camada Edge, como a verificação de alertas e a decisão de quais dados devem ser publicados e salvos.

3. **Camada Cloud (Nuvem):** A persistência dos dados no banco de dados H2 (simulando um banco de dados em nuvem) representa a camada Cloud. É aqui que os dados são armazenados de forma centralizada para análise de longo prazo, visualização e tomada de decisões estratégicas.

- 📂 Estrutura do Projeto

```java
src/main/java/com/ifba/web/iot/api/spring
├── amqp/                   // Aspectos (AOP) para lidar com funcionalidades cruzadas, como log ou segurança.
│   └── AnqpPublisher.java // Publica mensagens ANQP, um aspecto para comunicação.
│
├── config/                // Configurações da aplicação e de terceiros.
│   ├── CorsConfig.java    // Configurações para o CORS (Cross-Origin Resource Sharing).
│   ├── MqttAnqpConfig.java// Configurações específicas para o cliente MQTT.
│   └── RabbitMqpConfig.java// Configurações específicas para o cliente RabbitMQ.
│
├── controller/            // Camada de apresentação que lida com as requisições HTTP e roteia para os serviços.
│   ├── dto/               // Objetos de Transferência de Dados (Data Transfer Objects).
│   │   ├── form/          // Formulários para entrada de dados.
│   │   │   ├── LoginForm.java  // Formulário para o login do usuário.
│   │   │   └── RegisterForm.java // Formulário para o registro de novos usuários.
│   │   └── view/          // Objetos para representar dados na camada de visualização.
│   │       ├── LoginView.java   // Visão para os dados de login.
│   │       ├── SensorView.java  // Visão para os dados do sensor.
│   │       ├── UsuarioView.java // Visão para os dados do usuário.
│   │       └── WeatherView.java // Visão para os dados meteorológicos.
│   ├── AuthController.java// Lida com requisições de autenticação e autorização.
│   ├── RabbitDataController.java// Gerencia o fluxo de dados via RabbitMQ.
│   ├── SensorDataController.java// Gerencia o fluxo de dados do sensor.
│   └── WeatherController.java // Lida com requisições relacionadas a dados meteorológicos.
│
├── jwt/                   // Classes relacionadas à segurança e tokens JWT.
│   ├── JwtAuthenticationFilter.java // Filtro para interceptar requisições e validar o token JWT.
│   └── JwtUtil.java       // Utilitário para gerar e validar tokens JWT.
│
├── model/                 // Camada de domínio que representa os dados da aplicação.
│   ├── Alert.java         // Representa um alerta gerado por um sensor.
│   ├── SensorData.java    // Representa os dados coletados por um sensor.
│   └── Usuario.java       // Representa a entidade de usuário.
│
├── mqtt/                  // Classes relacionadas ao protocolo de comunicação MQTT.
│   ├── MqttPublisher.java // Publica mensagens para um broker MQTT.
│   └── MqttToAnqpBridge.java// Ponte entre o MQTT e o ANQP.
│
├── rabbitmq/              // Classes relacionadas ao protocolo de comunicação RabbitMQ.
│   └── simulation/        // Simulação do ambiente RabbitMQ.
│       ├── InMemoryRabbitListener.java // Simula um listener de mensagens em memória.
│       └── InMemoryRabbitTemplate.java // Simula um template de envio de mensagens em memória.
│
├── repository/            // Camada de persistência que lida com o acesso aos dados.
│   ├── AlertRepository.java// Interface de repositório para a entidade Alert.
│   ├── SensorDataRepository.java// Interface de repositório para a entidade SensorData.
│   └── UsuarioRepository.java// Interface de repositório para a entidade Usuario.
│
├── security/              // Configurações e classes de segurança.
│   └── SecurityConfig.java// Configurações globais de segurança da aplicação.
│
└── service/               // Camada de negócios que contém a lógica da aplicação.
    ├── AlertService.java  // Lógica para lidar com alertas.
    ├── AnqpConsumerService.java// Consumidor de mensagens ANQP.
    ├── AnqpProducerService.java// Produtor de mensagens ANQP.
    ├── MqttBridgeService.java// Serviço de ponte para MQTT.
    ├── SensorDataService.java// Lógica para lidar com os dados do sensor.
    ├── SensorScheduler.java // Serviço para agendamento de tarefas de coleta de dados.
    ├── UsuarioService.java  // Lógica de negócio para a entidade de usuário.
    └── WeatherService.java  // Lógica para lidar com dados meteorológicos.
Application.java           // Classe principal da aplicação, ponto de entrada.
├── resources/        // Diretório para arquivos de configuração, templates e arquivos estáticos.
├── static/                // Arquivos estáticos (CSS, JavaScript, imagens, etc.).
├── templates/             // Templates de páginas web (ex: HTML, Thymeleaf).
├── application-dev.properties // Propriedades de configuração para o ambiente de desenvolvimento.
├── application-prod.properties// Propriedades de configuração para o ambiente de produção.
├── application-test.properties// Propriedades de configuração para o ambiente de teste.
├── application.properties // Propriedades gerais da aplicação.
└── keystore.p12  // Arquivo de chave para segurança e criptografia.
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

📡 **MQTT (simulado)**

- **Mosquitto Broker:** Para um ambiente real, um `broker` `MQTT` dedicado, como o `Mosquitto`, é usado para gerenciar a troca de mensagens.
- Eclipse Paho Java Client: Uma biblioteca Java robusta para conectar o `Spring Boot` ao `broker` `Mosquitto`.

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

### 💡 Funcionalidades Adicionais

- 📊 **Processamento de Dados em Tempo Real:** A aplicação se conecta a um `broker` `MQTT` real para receber e processar dados de `sensores` em tempo real.
- 📈 **Gestão de Conexão com o Broker:** Implementação de lógica para reconectar automaticamente ao `broker MQTT` em caso de falha de conexão.
- ⚙️ Topics Flexíveis: A aplicação é configurada para se inscrever em múltiplos tópicos (ex: `dados/sensores/#`) para capturar dados de diferentes locais ou tipos de sensores.

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

📶 Configurações MQTT (IoT)
```properties
# URL do broker MQTT, usando variável de ambiente.
mqtt.url=${MQTT_URL:tcp://localhost:1883}

# Credenciais de acesso
mqtt.username=${MQTT_USER:guest}
mqtt.password=${MQTT_PASS:guest}

# Tópico principal para comunicação
mqtt.topic=${MQTT_TOPIC:dados/sensores}

# ID único do cliente (opcional, mas recomendado)
mqtt.client.id=${MQTT_CLIENT_ID:iot-client-1234}
```
📨 Configurações AMQP (Filas)
```properties
# Exchange e Routing Key para roteamento de mensagens
amqp.exchange=${AMQP_EXCHANGE:amqp.direct}
amqp.routing.key=${AMQP_ROUTING_KEY:iot.routing}

# Nome da fila
amqp.queue=${AMQP_QUEUE:iot-queue}
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

A validação da arquitetura e do fluxo de dados foi realizada de forma prática, utilizando o comando mosquitto_pub -h localhost -t "dados/sensores/local1" -m '{"temperatura": 35.5}' para simular a transmissão de informações. Essa ação confirmou a capacidade do sistema de interagir com sucesso com fontes de dados externas.

Em última análise, esta experiência contribuiu significativamente para a compreensão dos conceitos que regem o desenvolvimento de aplicações web que interagem com dados externos, fortalecendo a base para a implementação de soluções de software mais complexas e aplicáveis a cenários do mundo real.

---

| 🌎 LinkedIn                                                              | 👨‍💻 **Autor**                                                                 |
| ------------------------------------------------------------------------ | ---------------------------------------------------------------------------- |
| [LinkedIn](https://www.linkedin.com/in/albert-backend-java-spring-boot/) | [Albert Silva](https://www.linkedin.com/in/albert-backend-java-spring-boot/) |
