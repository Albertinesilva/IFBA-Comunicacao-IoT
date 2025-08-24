<h1 align="center">📸 Exibição de Resultados e Logs</h1>

Esta seção apresenta imagens capturadas de logs que comprovam o funcionamento dos endpoints e a integração com `RabbitMQ`.

## ⚙️ Configuração da Mensageria (MQTT e AMQP)

Este log de inicialização detalha a configuração bem-sucedida dos componentes de mensageria da aplicação. Ele confirma que o sistema está pronto para interagir com o `broker RabbitMQ`, com a criação da fila `iot-queue` e do `iot-exchange`, e também com o `broker MQTT` (em `localhost`), onde se conecta e se inscreve para receber mensagens do tópico `dados/sensores/local1`. Esses eventos atestam que a aplicação está totalmente operacional para a comunicação e o processamento de dados de sensores.

## ![Sensor Log](src/main/resources/static/assets/img/mqtt-amqp/Configuracao-Messageria.png)

## 🔄 Análise do Processamento de Dados MQTT

Este log detalha o ciclo completo de uma mensagem, desde a sua origem até a tentativa de processamento final. Ele comprova a funcionalidade da sua arquitetura, ao mesmo tempo que aponta um ponto de melhoria no tratamento dos dados.

- **Mensagem MQTT Recebida:** A aplicação escutando o tópico `dados/sensores/local1` recebe a mensagem (`{temperatura: 35.5}`) enviada pelo comando `mosquitto_pub`. Isso demonstra que a sua aplicação está corretamente conectada ao `broker MQTT` e pronta para receber dados externos.

- **Redirecionamento com Sucesso:** A ponte (`MqttAmqpBridgeService`) encaminha a mensagem recebida do `MQTT` para o `RabbitMQ` sem nenhum problema. Esta etapa valida que a comunicação entre os dois protocolos de mensageria está funcionando perfeitamente.

- **Falha de Desserialização:** O erro ocorre no serviço de consumo de mensagens (`AmqpConsumerService`), que tenta converter o `payload` (`{temperatura: 35.5}`) de uma string para um objeto `JSON`. A conversão falha porque, no formato JSON, as chaves (ex: temperatura) precisam estar entre aspas duplas, como `"temperatura"`.

- **Validação da Comunicação:** O log comprova que a arquitetura de comunicação entre `MQTT` e `RabbitMQ` está funcional. A falha é na etapa de validação e processamento da mensagem, o que destaca a importância de garantir que o formato dos dados recebidos esteja sempre correto.

## ![Sensor Log](src/main/resources/static/assets/img/mqtt-amqp/Analise-Processamento-Dados.png)

📦 Fluxo de Mensagens Completo (Simulado)

## ![Sensor Log](src/main/resources/static/assets/img/mqtt-amqp/Fluxo-de-Mensagens.png)

## 🔓 Evidência de Ataque Spoofing (IDOR) - Broken Access Control

Este log demonstra a exploração de uma vulnerabilidade de Controle de Acesso Quebrado (Broken Access Control).

- A requisição `PUT /api/sensores/1` foi enviada por um usuário autenticado (`albertinesilva@gmail.com`). No entanto, o sensor com `ID 1` não pertencia a ele.

- Os logs detalham a tentativa de atualização e confirmam o sucesso da operação (`✅ Sensor com ID 1 do USUÁRIO ALVO atualizado com sucesso...`). Isso prova que um usuário mal-intencionado conseguiu alterar dados de outro, configurando um ataque de `Spoofing` via IDOR (Insecure Direct Object Reference).

- A ausência de uma verificação de propriedade no código do endpoint permitiu que o usuário `albertinesilva@gmail.com` assumisse temporariamente a identidade e o controle de um recurso que não lhe pertencia, demonstrando a gravidade da falha.

![Sensor Log](src/main/resources/static/assets/img/ataque/Simulacao-de-Ataque-Spoofing.png)

## 🚫 Log de Erro: Tentativa de Acesso com Token JWT Inválido

Este log demonstra o comportamento de segurança da aplicação ao receber um `JSON Web Token` (JWT) malformado. Ele confirma que o sistema está corretamente configurado para rejeitar requisições que não apresentem um token válido, protegendo os endpoints da API.

Detalhes do Erro

- A exceção principal, `io.jsonwebtoken.MalformedJwtException`, indica que a biblioteca de `JWT` não conseguiu decodificar o token. A mensagem de erro específica, Malformed `JWT JSON`, aponta para um problema na estrutura do token, onde caracteres inesperados (como o $ no exemplo) foram encontrados.

## 🛡️🚫 Evidência de Bloqueio de Tentativa de Acesso Não Autorizado (IDOR)

Este log mostra que a sua defesa contra acessos indevidos funcionou perfeitamente. Um usuário (albertinesilva@gmail.com) tentou alterar os dados de um sensor (ID: 1) que não era dele. A sua lógica de segurança, que impede o ataque IDOR (Insecure Direct Object Reference), percebeu que o usuário logado não tinha permissão para essa ação. Por isso, a tentativa foi negada, e o sistema respondeu com um erro 403 Forbidden. Isso prova que a proteção dos dados está ativa e segura.

![Sensor Log](src/main/resources/static/assets/img/ataque/Tratamento-de-Ataque.png)

## ✅ Log de Segurança: Operação Autorizada e Bem-Sucedida

O log de auditoria a seguir demonstra uma operação de atualização de sensor bem-sucedida e autorizada, confirmando que os mecanismos de defesa contra `IDOR` permitem o acesso a recursos apenas para usuários com as permissões corretas.

Análise do Log
- Tentativa de Acesso (Linha 5): O usuário `albertinesilva@gmail.com` tenta atualizar o sensor com ID: 3. A aplicação registra este evento como parte de sua auditoria de segurança (`Simulação de Auditoria`).

- Autorização e Execução (Linha 6): Após a verificação de autorização, o mecanismo de defesa permite a operação. A mensagem `✅ Operação Bem-Sucedida` confirma que a solicitação foi validada e o sensor foi atualizado com sucesso.

- Confirmação do Servidor (Linha 10): A operação é finalizada com um status `200 OK`, indicando que a requisição foi processada sem erros e o recurso (o sensor) foi modificado conforme o esperado.

Este log serve como prova de que a aplicação não só bloqueia tentativas indevidas, mas também processa corretamente as solicitações legítimas, demonstrando a robustez dos seus controles de acesso e a prevenção eficaz contra vulnerabilidades IDOR.

## ![Sensor Log](src/main/resources/static/assets/img/ataque/Atualizacao-Sucesso.png)

## 📋 Listagem de Sensores

Visualização dos sensores simulados retornados pelo endpoint `GET /sensores`.

## ![Sensor Log](src/main/resources/static/assets/img/insert/1id.png)

## ![Sensor Log](src/main/resources/static/assets/img/insert/2id.png)

![Sensor Log](src/main/resources/static/assets/img/insert/3id.png)

---
## 🚨 Registro de Alertas

Exemplo de envio de dados de alerta via POST /alerta e resposta do servidor.

## ![Sensor Log](src/main/resources/static/assets/img/insert/post-alerta.png)

## ![Sensor Log](src/main/resources/static/assets/img/insert/alerta.png)

![Sensor Log](src/main/resources/static/assets/img/insert/post-alerta-200ok.png)

---
## 🐇 Comunicação com RabbitMQ

Confirmação de que os dados foram enviados para a fila RabbitMQ com sucesso.

![Sensor Log](src/main/resources/static/assets/img/rabbit/rabbit.png)

---

## Consumo pelo serviço, com evidências de cada etapa

### Temperatura acima de 30
![Sensor Log](src/main/resources/static/assets/img/rabbit/fila-rabbit.png)

---

### Temperatura abaixo de 30
![Sensor Log](src/main/resources/static/assets/img/rabbit/fila-rabbit-temperatura-abaixo-de-30.png)  
