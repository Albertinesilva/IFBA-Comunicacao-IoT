package com.ifba.web.iot.api.spring.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifba.web.iot.api.spring.controller.dto.form.RegisterForm;
import com.ifba.web.iot.api.spring.controller.dto.view.ClienteView;
import com.ifba.web.iot.api.spring.model.Usuario;
import com.ifba.web.iot.api.spring.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Serviço responsável pelo gerenciamento de clientes no sistema.
 * <p>
 * Este serviço fornece funcionalidades para:
 * <ul>
 * <li>Cadastro de novos clientes com criptografia de senha.</li>
 * <li>Conversão de entidades Cliente para DTO {@link ClienteView} com dados
 * mascarados.</li>
 * <li>Autenticação de clientes via Spring Security
 * {@link UserDetailsService}.</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

  private final UsuarioRepository clienteRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  /**
   * Carrega um cliente pelo email para fins de autenticação Spring Security.
   *
   * @param email Email do cliente
   * @return {@link UserDetails} com informações de autenticação
   * @throws UsernameNotFoundException se o cliente não for encontrado
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario cliente = clienteRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));
    return User.builder()
        .username(cliente.getEmail())
        .password(cliente.getSenha())
        .roles("USER")
        .build();
  }

  /**
   * Salva um novo cliente no banco de dados a partir de um DTO
   * {@link RegisterForm}.
   * <p>
   * A senha é criptografada antes de salvar. O retorno é um {@link ClienteView}
   * com
   * nome e email mascarados para exibição segura.
   * </p>
   *
   * @param registerForm DTO contendo nome, email e senha do cliente
   * @return {@link ClienteView} com informações mascaradas do cliente
   */
  @Transactional
  public ClienteView saveCliente(RegisterForm registerForm) {
    Usuario cliente = new Usuario();
    cliente.setNome(registerForm.getNome());
    cliente.setEmail(registerForm.getEmail());
    cliente.setSenha(passwordEncoder.encode(registerForm.getSenha()));

    Usuario savedCliente = clienteRepository.save(cliente);

    return new ClienteView(
        maskName(savedCliente.getNome()),
        maskEmail(savedCliente.getEmail()));
  }

  /**
   * Busca um cliente pelo email.
   *
   * @param email Email do cliente
   * @return {@link Optional} contendo o cliente caso encontrado, ou vazio caso
   *         contrário
   */
  public Optional<Usuario> findByEmail(String email) {
    return clienteRepository.findByEmail(email);
  }

  /**
   * Converte um {@link Usuario} para {@link ClienteView}, aplicando mascaramento
   * de nome e email.
   *
   * @param cliente Entidade Cliente
   * @return {@link ClienteView} com dados mascarados
   */
  public ClienteView toClienteView(Usuario cliente) {
    return new ClienteView(maskName(cliente.getNome()), maskEmail(cliente.getEmail()));
  }

  /**
   * Verifica se uma senha em texto plano corresponde à senha criptografada.
   *
   * @param rawSenha     Senha em texto plano
   * @param encodedSenha Senha criptografada armazenada no banco
   * @return {@code true} se as senhas coincidirem, {@code false} caso contrário
   */
  public boolean verificarSenha(String rawSenha, String encodedSenha) {
    return passwordEncoder.matches(rawSenha, encodedSenha);
  }

  /**
   * Mascara o nome, mantendo visíveis apenas a primeira e a última letra.
   *
   * @param name Nome original
   * @return Nome mascarado
   */
  public String maskName(String name) {
    if (name == null || name.length() <= 2)
      return name;
    return name.charAt(0) + "*".repeat(name.length() - 2) + name.charAt(name.length() - 1);
  }

  /**
   * Mascara o email, mantendo visível apenas a primeira letra e o domínio.
   *
   * @param email Email original
   * @return Email mascarado
   */
  public String maskEmail(String email) {
    if (email == null)
      return null;
    int index = email.indexOf("@");
    if (index <= 1)
      return "***" + email.substring(index);
    return email.substring(0, 1) + "***" + email.substring(index - 1);
  }
}
