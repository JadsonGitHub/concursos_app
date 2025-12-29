package com.concursos.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

/**
 * Filtro de autenticação HTTP Basic simples
 * Protege a aplicação sem precisar do Spring Security
 */
@Component
public class BasicAuthFilter implements Filter {

    @Value("${app.auth.enabled:true}")
    private boolean authEnabled;

    @Value("${app.auth.username:admin}")
    private String username;

    @Value("${app.auth.password:concursos2025}")
    private String password;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Se autenticação desabilitada, continua
        if (!authEnabled) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring(6);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);

            if (values.length == 2 && values[0].equals(username) && values[1].equals(password)) {
                // Autenticado com sucesso
                chain.doFilter(request, response);
                return;
            }
        }

        // Não autenticado - solicita login
        httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"Concursos App\"");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json");
        httpResponse.getWriter().write("{\"error\": \"Autenticação necessária\"}");
    }
}
