package com.stylesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF disable
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // allrequest ok
            )
            .formLogin(form -> form
                .disable() // formLogin disable
            )
            .httpBasic(httpBasic -> httpBasic
                .disable() // HTTP Basic disable
            );

        return http.build();
    }
}
