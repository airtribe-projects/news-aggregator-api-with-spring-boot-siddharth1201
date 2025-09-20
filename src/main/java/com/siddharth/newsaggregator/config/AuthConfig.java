package com.siddharth.newsaggregator.config;

import org.springframework.beans.factory.annotation.Autowired; // <-- IMPORT
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // <-- IMPORT
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class AuthConfig {

    @Autowired // <-- INJECT YOUR FILTER
    private AuthJwtAuthenticationFilter authJwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) { // <-- Injects ServerHttpSecurity
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/api/users/register", "/api/auth/signin")
                .permitAll() // Permit public endpoints
                .anyExchange()
                .authenticated() // Secure all other endpoints
            )
            // Add your custom JWT filter at the right position in the chain
            .addFilterAt(authJwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }
}