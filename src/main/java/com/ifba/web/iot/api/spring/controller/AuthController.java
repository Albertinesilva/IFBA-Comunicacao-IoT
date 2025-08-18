package com.ifba.web.iot.api.spring.controller;

import com.ifba.web.iot.api.spring.controller.dto.form.LoginForm;
import com.ifba.web.iot.api.spring.controller.dto.form.RegisterForm;
import com.ifba.web.iot.api.spring.controller.dto.view.ClienteView;
import com.ifba.web.iot.api.spring.controller.dto.view.LoginView;
import com.ifba.web.iot.api.spring.jwt.JwtUtil;
import com.ifba.web.iot.api.spring.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * Controlador REST responsável pela gestão de autenticação e registro de
 * clientes.
 * <p>
 * Este controlador fornece endpoints para:
 * <ul>
 * <li>Cadastro de novos clientes com criptografia de senha.</li>
 * <li>Autenticação de clientes existentes e emissão de tokens JWT.</li>
 * </ul>
 * Dados sensíveis como senha são criptografados antes do armazenamento,
 * e informações pessoais podem ser mascaradas quando retornadas em DTOs.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UsuarioService clienteService;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  /**
   * Endpoint para registro de um novo cliente.
   * <p>
   * Recebe os dados do cliente, criptografa a senha e salva no banco.
   * Retorna os dados do cliente em um DTO {@link ClienteView} com informações
   * sensíveis mascaradas, como nome e email.
   * </p>
   *
   * @param requestBody Objeto {@link RegisterForm} contendo nome, email e senha
   *                    do cliente.
   * @return {@link ResponseEntity} contendo {@link ClienteView} em caso de
   *         sucesso
   *         ou mensagem de erro caso o registro falhe.
   */
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterForm requestBody) {
    log.info("Cadastrando novo cliente com email '{}'", requestBody.getEmail());

    try {
      ClienteView view = clienteService.saveCliente(requestBody);
      log.info("Cliente cadastrado com sucesso: {}", view.getEmailMascarado());
      return ResponseEntity.status(HttpStatus.CREATED).body(view);
    } catch (Exception e) {
      log.error("Erro ao cadastrar cliente", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Erro ao cadastrar cliente: " + e.getMessage());
    }
  }

  /**
   * Endpoint para autenticação de clientes.
   * <p>
   * Recebe email e senha do cliente, valida as credenciais e, em caso de sucesso,
   * gera e retorna um token JWT encapsulado no DTO {@link LoginView}.
   * </p>
   *
   * @param requestBody Objeto {@link LoginForm} contendo email e senha do
   *                    cliente.
   * @param request     Objeto {@link HttpServletRequest} para contexto HTTP
   *                    (opcional, usado para logs e headers).
   * @return {@link ResponseEntity} contendo {@link LoginView} com token JWT em
   *         caso de sucesso,
   *         ou mensagem de erro com status 401 se as credenciais forem inválidas.
   */
  @PostMapping
  public ResponseEntity<?> autenticar(@RequestBody LoginForm requestBody,
      HttpServletRequest request) {
    log.info("Iniciando autenticação para email '{}'", requestBody.getEmail());

    try {
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(requestBody.getEmail(),
          requestBody.getSenha());

      authenticationManager.authenticate(authToken);

      String token = jwtUtil.generateToken(requestBody.getEmail());

      log.info("Autenticação bem-sucedida para email '{}'", requestBody.getEmail());
      return ResponseEntity.ok(new LoginView(token));

    } catch (AuthenticationException ex) {
      log.warn("Tentativa de login inválida para email '{}'", requestBody.getEmail(), ex);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Credenciais inválidas");
    }
  }
}
