package com.parking.administration.config;

import com.parking.administration.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.parking.administration.domain.enums.Permission.*;
import static com.parking.administration.domain.enums.UserRole.ADMIN;
import static com.parking.administration.domain.enums.UserRole.USER;
import static com.parking.administration.util.Constants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,"/v1/api/registration/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/registration/confirm/token").permitAll()

                        .requestMatchers(HttpMethod.GET, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())
                        .requestMatchers(HttpMethod.POST, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())
                        .requestMatchers(HttpMethod.PUT, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())
                        .requestMatchers(HttpMethod.DELETE, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())

                        .requestMatchers(HttpMethod.GET, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_READ.name())
                        .requestMatchers(HttpMethod.POST, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_DELETE.name())

                        .requestMatchers(HttpMethod.GET, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_READ.name())
                        .requestMatchers(HttpMethod.POST, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_DELETE.name())

                        .anyRequest().authenticated());


        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
