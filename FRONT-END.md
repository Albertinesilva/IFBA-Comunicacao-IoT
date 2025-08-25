<h2 align="center">🌾Frontend IoT Simulado Protocolos para Agricultura Inteligente com Spring Boot</h2>

Este documento detalha o funcionamento e a interface de usuário do ***frontend***, que se comunica com o backend ***Spring Boot*** para monitorar dados de sensores agrícolas. O frontend é construído com ***JavaScript*** puro e utiliza a `API fetch` para consumir os dados em tempo real.

---
### Como Rodar a Aplicação

Para executar a aplicação frontend, você precisará da extensão `Live Server` instalada no seu editor de código Visual Studio Code:

<img src="src/main/resources/static/assets/img/frontend/Live-Server.png" width="300" alt="Imagem da extensão Live Server">

- Abra a pasta do projeto no `VS Code`.

- Clique com o botão direito do mouse no arquivo `home.html`.

- Selecione a opção "`Open with Live Server`".

O **Live Server** irá iniciar um servidor local e abrir a página no seu navegador padrão, permitindo que o `frontend` faça as requisições para o `backend`.

---

### Telas do Sistema

#### Telas `Home`, `Cadastro` e `Login`.

<div style="display: flex; align-items: center; justify-content: center; gap: 16px;">
  <img src="src/main/resources/static/assets/img/frontend/Home.png" alt="Tela inicial (Home)" width="30%" height="300">

  <img src="https://github.githubassets.com/images/icons/emoji/octocat.png" alt="Octocat" width="30px">

  <img src="src/main/resources/static/assets/img/frontend/Signup.png" alt="Tela de Cadastro" width="30%" height="280">

  <img src="https://github.githubassets.com/images/icons/emoji/octocat.png" alt="Octocat" width="30px">

  <img src="src/main/resources/static/assets/img/frontend/Login.png" alt="Tela de Login" width="30%" height="280">
</div>

---
#### Tela de `Escolha`.

A tela de escolha é o ponto de partida. Ela permite para onde ir dentro do aplicativo.

<img src="src/main/resources/static/assets/img/frontend/Choice.png" alt="Tela de Escolha" width="40%" height="450">

---
#### Telas de alertas de `Cadastro/Sensor`, `Temperatura`, `Umidade` e `Luminosidade`.

🍃 `Cadastro de Sensor`

<img src="src/main/resources/static/assets/img/frontend/Painel-Cadastro.png" alt="Tela de Cadastro" width="100%" height="100%">

🌡️ `Temperatura Acima de 30 ºC`

<img src="src/main/resources/static/assets/img/frontend/Alert-Temperatura.png" alt="Tela de Alert de Temperatura" width="100%" height="100%">

---
💧 `Umidade Abaixo de 100,00 %`

<img src="src/main/resources/static/assets/img/frontend/Alert-Umidade.png" alt="Tela de Alert de Umidade" width="100%" height="100%">

---
💡 `Luminosidade Abaixo de 200,00 Lux`

<img src="src/main/resources/static/assets/img/frontend/Alert-Luminosidade.png" alt="Tela de Alert de Luminosidade" width="100%" height="100%">

---
### 📈 Tela de `Gráfico`

Visualização de Dados com `Gráficos`

<img src="src/main/resources/static/assets/img/frontend/Chart-View.png" alt="Tela de Graficos" width="100%" height="100%">

---
### ☀️ 🌧️ Tela de Clima das `Cidades`

A tela de `clima` permite que o usuário pesquise as condições `meteorológicas` de qualquer `cidade`. É possível usar os botões pré-definidos para cidades populares ou digitar o nome da cidade desejada e buscar.

<img src="src/main/resources/static/assets/img/frontend/Weather.png" alt="Tela de Clima" width="100%" height="100%">

---

### 📅 Tela de Historico de Sensores

<img src="src/main/resources/static/assets/img/frontend/History-Alerts.png" alt="Tela de Historico" width="100%" height="100%">

---

### ✅ Conclusão e Aprendizados

Este projeto demonstra não apenas a capacidade de construir uma interface de usuário responsiva e intuitiva, mas também destaca a importância da comunicação entre o `frontend` e o `backend`. Através do uso de `JavaScript` puro e da `API fetch`, foi possível criar um painel de monitoramento em tempo real que simula um ambiente de agricultura inteligente, consumindo dados de `sensores` e apresentando-os de forma clara para o usuário.

A experiência de desenvolver este sistema reforça o conhecimento sobre a `arquitetura` de aplicações web, o tratamento de dados `assíncronos` e a visualização de informações, elementos cruciais para o desenvolvimento de soluções `IoT`.

---
| 🌎 LinkedIn                                                              | 👨‍💻 **Autor**                                                                 |
| ------------------------------------------------------------------------ | ---------------------------------------------------------------------------- |
| [LinkedIn](https://www.linkedin.com/in/albert-backend-java-spring-boot/) | [Albert Silva](https://www.linkedin.com/in/albert-backend-java-spring-boot/) |


