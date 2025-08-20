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

Telas `Home`, `Cadastro` e `Login`.

<div style="display: flex; align-items: center; justify-content: center; gap: 16px;">
  <img src="src/main/resources/static/assets/img/frontend/Home.png" alt="Tela inicial (Home)" width="30%" height="300">

  <img src="https://github.githubassets.com/images/icons/emoji/octocat.png" alt="Octocat" width="30px">

  <img src="src/main/resources/static/assets/img/frontend/Signup.png" alt="Tela de Cadastro" width="30%" height="280">

  <img src="https://github.githubassets.com/images/icons/emoji/octocat.png" alt="Octocat" width="30px">

  <img src="src/main/resources/static/assets/img/frontend/Login.png" alt="Tela de Login" width="30%" height="280">
</div>

---
Tela de `Escolha`

A tela de escolha é o ponto de partida. Ela permite para onde ir dentro do aplicativo.

<img src="src/main/resources/static/assets/img/frontend/Choice.png" alt="Tela de Login" width="50%" height="450">
