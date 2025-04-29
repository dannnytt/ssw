package com.example.study.OrderService.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public JwtRequestFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String username;

        try {
            username = tokenProvider.getUsernameFromToken(jwt);
        } catch (ExpiredJwtException e) {
            log.warn("Время жизни токена вышло");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Токен истёк");
            return;
        } catch (SignatureException e) {
            log.warn("Неправильная подпись токена");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Неверная подпись токена");
            return;
        } catch (Exception e) {
            log.error("Ошибка парсинга Jwt токена", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Ошибка токена");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String role = tokenProvider.getUserRoleFromToken(jwt);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(role))
            );
            log.info("Пользователь: {}, Роль: {}", username, role);
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
