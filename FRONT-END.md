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

Visão Geral das Principais Telas
Aqui estão as telas Home, a página de Login e a de Cadastro.

<div style="display: flex; flex-direction: row; justify-content: space-around; gap: 1rem;">

<div style="flex-basis: 30%;">
<img src="src/main/resources/static/assets/img/frontend/Home.png" alt="Tela inicial (Home)" width="200">
<p style="text-align: center; font-style: italic; color: #555;">Tela Home</p>
</div>

<div style="flex-basis: 30%;">
<img src="src/main/resources/static/assets/img/frontend/Login.png" alt="Tela de Login" width="200">
<p style="text-align: center; font-style: italic; color: #555;">Tela de Login</p>
</div>

<div style="flex-basis: 30%;">
<img src="src/main/resources/static/assets/img/frontend/Signup.png" alt="Tela de Cadastro (Signup)" width="200">
<p style="text-align: center; font-style: italic; color: #555;">Tela de Cadastro</p>
</div>

</div>

