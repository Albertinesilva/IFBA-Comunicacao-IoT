package com.ifba.web.iot.api.spring.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticação JWT que intercepta todas as requisições HTTP
 * para validar o token JWT fornecido no cabeçalho Authorization.
 * <p>
 * Funcionalidades:
 * <ul>
 * <li>Extrai o token do cabeçalho "Authorization" caso exista</li>
 * <li>Valida o token utilizando {@link JwtUtil}</li>
 * <li>Se válido, autentica o usuário no contexto de segurança do Spring</li>
 * <li>Se inválido, apenas registra um warning e segue sem autenticação</li>
 * </ul>
 * </p>
 * <p>
 * Este filtro é executado uma vez por requisição, garantindo que cada
 * requisição
 * HTTP seja autenticada antes de acessar recursos protegidos.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * Intercepta a requisição HTTP e valida o token JWT.
     * <p>
     * Caso o token seja válido, adiciona o usuário autenticado no
     * {@link SecurityContextHolder} para que o Spring Security permita
     * o acesso aos recursos protegidos.
     * </p>
     *
     * @param request     Requisição HTTP recebida
     * @param response    Resposta HTTP que será enviada
     * @param filterChain Cadeia de filtros do Spring Security
     * @throws ServletException caso ocorra erro no processamento do filtro
     * @throws IOException      caso ocorra erro de entrada/saída
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String email = jwtUtil.validateToken(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
                        null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.warn("Token JWT inválido: {}", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
