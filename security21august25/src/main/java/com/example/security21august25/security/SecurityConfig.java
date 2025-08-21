package com.example.security21august25.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // stateful sessions
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // authorization
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/login").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // form login (default login page for now)
                .formLogin(Customizer.withDefaults())

                // logout: use logoutUrl instead of deprecated AntPathRequestMatcher
                .logout(logout -> logout
                        .logoutUrl("/logout")              // expects POST with valid CSRF
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                // keep CSRF enabled; optionally ignore your API register endpoint if it's pure REST
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/register"));

        return http.build();
    }
}
