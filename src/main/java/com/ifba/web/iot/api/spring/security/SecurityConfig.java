package com.ifba.web.iot.api.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ifba.web.iot.api.spring.jwt.JwtAuthenticationFilter;

/**
 * Configuração de segurança da aplicação utilizando Spring Security e JWT.
 * <p>
 * Esta classe define:
 * <ul>
 * <li>Filtro de autenticação JWT</li>
 * <li>Permissões públicas e privadas das rotas</li>
 * <li>Política de sessão stateless</li>
 * <li>Codificador de senha BCrypt</li>
 * <li>AuthenticationManager para validação de credenciais</li>
 * </ul>
 * </p>
 */
@Configuration
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtFilter;

  public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  /**
   * Configura a cadeia de filtros de segurança da aplicação.
   * <p>
   * Regras aplicadas:
   * <ul>
   * <li>CSRF desativado</li>
   * <li>Permissão pública para rotas "/api/auth/**" e "/api/sensores**"</li>
   * <li>Autenticação obrigatória para "/api/rabbit/**" e demais rotas</li>
   * <li>Política de sessão stateless (sem armazenamento de sessão no
   * servidor)</li>
   * <li>Adiciona o filtro JWT antes do filtro padrão de autenticação por
   * username/senha</li>
   * </ul>
   * </p>
   *
   * @param http HttpSecurity utilizado para configurar as regras de segurança
   * @return {@link SecurityFilterChain} configurado
   * @throws Exception caso ocorra algum erro na configuração
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> {
        }) // <-- Ativa CORS
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/clima/**").authenticated()
            .requestMatchers("/api/sensores/**").authenticated()
            .requestMatchers("/api/rabbit/**").authenticated()
            .anyRequest().authenticated())
        .headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin()) // permite exibir em frames
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * Fornece o codificador de senha BCrypt utilizado para criptografia de senhas.
   *
   * @return instância de {@link BCryptPasswordEncoder}
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Fornece o {@link AuthenticationManager} utilizado para autenticação de
   * usuários.
   * <p>
   * Este bean é necessário para que o Spring Security possa validar as
   * credenciais
   * durante o login com JWT.
   * </p>
   *
   * @param authConfig Configuração de autenticação do Spring
   * @return {@link AuthenticationManager} configurado
   * @throws Exception caso ocorra algum erro na criação do AuthenticationManager
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
