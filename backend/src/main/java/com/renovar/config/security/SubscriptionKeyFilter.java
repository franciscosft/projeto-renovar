package com.renovar.config.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Authenticates frontend requests via the X-Subscription-Key header.
 * Grants ROLE_FRONTEND, which allows all GET endpoints.
 */
public class SubscriptionKeyFilter extends OncePerRequestFilter {

    static final String HEADER = "X-Subscription-Key";

    private final String expectedKey;

    public SubscriptionKeyFilter(String expectedKey) {
        this.expectedKey = expectedKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String key = request.getHeader(HEADER);

        if (key == null) {
            chain.doFilter(request, response);
            return;
        }

        if (!expectedKey.equals(key)) {
            sendUnauthorized(response, "Invalid subscription key");
            return;
        }

        var auth = new UsernamePasswordAuthenticationToken(
                "frontend", null,
                List.of(new SimpleGrantedAuthority("ROLE_FRONTEND")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }

}