<h1 align="center">📸 Exibição de Resultados e Logs</h1>

Esta seção apresenta imagens capturadas durante os testes dos endpoints e da integração simulada com RabbitMQ.

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
![Sensor Log](src/main/resources/static/assets/img/rabbit/fila-rabbit.png)

---

![Sensor Log](src/main/resources/static/assets/img/rabbit/fila-rabbit-temperatura-abaixo-de-30.png)  
