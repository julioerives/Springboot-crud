package com.register.registers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.register.registers.services.jwtServices.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**").permitAll() 
                        .anyRequest().authenticated()) 
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("*");
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");
                    return config;
                }))
                .build();
    }

    // @Bean
    // public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
    //    return config.getAuthenticationManager();
    // }
}