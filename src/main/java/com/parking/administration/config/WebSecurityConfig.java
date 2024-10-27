package com.parking.administration.config;

import com.parking.administration.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.parking.administration.domain.enums.UserRole.ADMIN;
import static com.parking.administration.domain.enums.UserRole.USER;
import static com.parking.administration.util.Constants.BASE_URL;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final String USER_ROLE = "ROLE_" + USER.name();
    private final String ADMIN_ROLE = "ROLE_" + ADMIN.name();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(
                    "/api/v1/auth/",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html",
                    BASE_URL + "/public/**"
            ).permitAll();
            auth.requestMatchers(BASE_URL + "/users/**").hasAnyAuthority(USER_ROLE, ADMIN_ROLE);
            auth.requestMatchers(BASE_URL + "/moder/**").hasAnyAuthority(ADMIN_ROLE);
            auth.anyRequest().authenticated();
        });

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.logout(logout -> logout.logoutSuccessUrl("/api/v1/auth/logout")
                .clearAuthentication(true)
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> {
                    SecurityContextHolder.clearContext();
                })
                .permitAll());
        return http.build();
    }
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        if (Boolean.parseBoolean(System.getenv("DEV"))) {
            config.setAllowCredentials(true);
            config.setAllowedOrigins(List.of("http://localhost:3000"));
            config.setAllowedOrigins(List.of("http://localhost:8082"));
            config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        } else {
            config.setAllowedOrigins(List.of());
            config.setAllowedMethods(List.of());
        }

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
