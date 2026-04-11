package com.socodo.mechcalc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.socodo.mechcalc.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper = JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/auth/login",
                "/auth/introspect",
                "/users/register",
                "/mechcalc/auth/login",
                "/mechcalc/auth/introspect",
                "/mechcalc/users/register",
                "/v3/api-docs/**",
                "/mechcalc/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/mechcalc/swagger-ui/**",
                "/mechcalc/swagger-ui.html"
            ).permitAll()
            .anyRequest().authenticated())
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((request, response, authException) ->
                writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED", "Unauthorized"))
            .accessDeniedHandler((request, response, accessDeniedException) ->
                writeErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "FORBIDDEN", "Forbidden")))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }

    private void writeErrorResponse(HttpServletResponse response, int status, String code, String message)
        throws java.io.IOException {
    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    ApiResponse<Void> body = ApiResponse.error(code, message);
    response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
