package com.parking.administration.demo.config;

import com.parking.administration.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.parking.administration.demo.domain.enums.Permission.*;
import static com.parking.administration.demo.domain.enums.UserRole.ADMIN;
import static com.parking.administration.demo.domain.enums.UserRole.USER;
import static com.parking.administration.demo.utils.Constants.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

//    private final LogoutHandler logoutHandler;

    public WebSecurityConfig(UserService userService, JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/v1/api/registration/**").hasAnyRole(ADMIN.name(), USER.name())

                                .requestMatchers(GET, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(POST, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(PUT, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(DELETE, ADMIN_USER_ENDPOINT).hasAnyAuthority(ADMIN.name(), USER.name())

                                .requestMatchers(GET, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_READ.name())
                                .requestMatchers(POST, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_CREATE.name())
                                .requestMatchers(PUT, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, ADMIN_PARKING_SPACE_ENDPOINT).hasRole(ADMIN_DELETE.name())

                                .requestMatchers(GET, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_READ.name())
                                .requestMatchers(POST, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_CREATE.name())
                                .requestMatchers(PUT, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, ADMIN_VEHICLE_ENDPOINT).hasRole(ADMIN_DELETE.name())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                )
        return http.build();
    }

}
