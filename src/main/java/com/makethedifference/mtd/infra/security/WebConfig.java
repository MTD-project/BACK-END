package com.makethedifference.mtd.infra.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Método de inicialización del filtro (sin implementación en este caso)
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Configura las cabeceras CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, HEAD, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, credential, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // Responde con OK si el método es OPTIONS
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Continúa con la cadena de filtros
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        // Método de destrucción del filtro (sin implementación en este caso)
    }
}