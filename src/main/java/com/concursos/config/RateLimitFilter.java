package com.concursos.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Filtro simples de rate limiting para prevenir abuso
 * Limita requisições por IP
 */
@Component
public class RateLimitFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIP = getClientIP(httpRequest);

        // Limpar contadores antigos a cada minuto
        cleanupOldEntries();

        RequestCounter counter = requestCounts.computeIfAbsent(clientIP, k -> new RequestCounter());

        if (counter.increment() > MAX_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(429);  // Too Many Requests
            httpResponse.getWriter().write("{\"error\": \"Too many requests. Please try again later.\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private void cleanupOldEntries() {
        long currentTime = System.currentTimeMillis();
        requestCounts.entrySet().removeIf(entry ->
            currentTime - entry.getValue().getTimestamp() > 60000
        );
    }

    private static class RequestCounter {
        private final AtomicInteger count = new AtomicInteger(0);
        private final long timestamp = System.currentTimeMillis();

        public int increment() {
            return count.incrementAndGet();
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
