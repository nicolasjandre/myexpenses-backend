package com.example.myexpenses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.myexpenses.domain.services.RefreshJwtService;
import com.example.myexpenses.handler.CustomAccessDeniedHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private RefreshJwtService refreshJwtService;

        @Autowired
        private AuthenticationConfiguration authenticationConfiguration;

        @Autowired
        private UserDetailsSecurityServer userDetailsSecurityServer;

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .headers(headers -> headers.frameOptions().disable())
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/refresh").permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                http
                                .addFilter(new JwtAuthenticationFilter(
                                                authenticationManager(authenticationConfiguration), jwtUtil, refreshJwtService));

                http
                                .addFilter(new JwtAuthorizationFilter(
                                                authenticationManager(authenticationConfiguration), jwtUtil,
                                                userDetailsSecurityServer));
                http.cors(withDefaults()).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(401))
                        .accessDeniedHandler(new CustomAccessDeniedHandler());
                return http.build();
        }
}
