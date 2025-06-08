package com.ifba.web.iot.api.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuração de segurança da aplicação.
 * Define as regras de autenticação/autorização e os usuários em memória.
 */
@Configuration
public class SecurityConfig {

  /**
   * Configura o filtro de segurança da aplicação.
   * - Desativa CSRF (útil para APIs REST).
   * - Protege todas as rotas por padrão.
   * - Permite acesso sem autenticação à rota "/api/sensores".
   * - Exige autenticação básica nas demais rotas, incluindo "/api/rabbit".
   *
   * @param http o objeto HttpSecurity para configuração.
   * @return o filtro de segurança configurado.
   * @throws Exception caso ocorra erro de configuração.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar chamadas de API
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/rabbit/**").authenticated() // Requer autenticação
            .requestMatchers("/api/sensores**").permitAll() // Permite sem login
            .anyRequest().authenticated()) // Demais rotas também requerem autenticação
        .httpBasic(Customizer.withDefaults()) // Habilita autenticação HTTP Basic
        .build();
  }

  /**
   * Cria um usuário em memória com nome "usuario" e senha "senha123".
   * Esse usuário pode ser usado para autenticação básica nas rotas protegidas.
   *
   * @return um gerenciador de usuários com o usuário em memória configurado.
   */
  @Bean
  public UserDetailsService users() {
    UserDetails user = User.withUsername("usuario")
        .password("{noop}senha123") // {noop} indica que a senha não está criptografada
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}
