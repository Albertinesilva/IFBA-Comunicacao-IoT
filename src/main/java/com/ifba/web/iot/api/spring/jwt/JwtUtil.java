package com.ifba.web.iot.api.spring.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utilitário para geração, validação e extração de informações de tokens JWT
 * (JSON Web Token).
 * <p>
 * Esta classe encapsula operações com JWT, utilizando o algoritmo HS256 para
 * assinatura.
 * Inclui métodos para:
 * <ul>
 * <li>Gerar token JWT para um usuário</li>
 * <li>Validar token JWT e extrair o username</li>
 * <li>Obter o email/username diretamente do token</li>
 * </ul>
 * </p>
 * <p>
 * Os tokens gerados possuem validade configurada pelo atributo
 * {@code expiration} (1 hora por padrão).
 * </p>
 */
@Component
public class JwtUtil {

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private final long expiration = 1000 * 60 * 60; // 1 hora

  /**
   * Gera um token JWT assinado para o usuário informado.
   * <p>
   * O token contém o username como "subject", data de emissão e data de
   * expiração.
   * </p>
   *
   * @param username Nome do usuário para o qual o token será gerado
   * @return Token JWT assinado como {@link String}
   */
  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(key)
        .compact();
  }

  /**
   * Valida um token JWT e retorna o username contido nele.
   * <p>
   * Lança uma {@link JwtException} se o token for inválido ou estiver expirado.
   * </p>
   *
   * @param token Token JWT a ser validado
   * @return Username (subject) contido no token
   * @throws JwtException Se o token for inválido ou expirado
   */
  public String validateToken(String token) throws JwtException {
    Jws<Claims> claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
    return claims.getBody().getSubject();
  }

  /**
   * Extrai o email ou username diretamente do token JWT.
   * <p>
   * Este método não valida explicitamente o token, apenas obtém o "subject".
   * </p>
   *
   * @param token Token JWT
   * @return Email ou username do usuário contido no token
   */
  public String getEmailFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key) // define a chave
        .build() // constrói o parser
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

}
