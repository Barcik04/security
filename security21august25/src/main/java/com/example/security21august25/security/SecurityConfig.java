package com.example.security21august25.security;

import com.example.security21august25.jwt.AuthEntryPointJwt;
import com.example.security21august25.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

    @Bean AuthenticationManager authManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain chain(HttpSecurity http,
                                     AuthTokenFilter jwt,
                                     AuthEntryPointJwt entry) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entry))
                .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

//Who’s who in your code
//
//SecurityConfig → the festival’s rulebook (what zones exist, who can enter, filter order).
//
//AuthController → the ticket booth handing out wristbands (tokens).
//
//JwtUtils → the printer + UV stamper (creates/signs/validates tokens).
//
//AuthTokenFilter → the guards at every gate (validate token, set user for this request).
//
//AuthEntryPointJwt → the person who says “no wristband? sorry, can’t enter” and gives a clean reason (401).
//
//UserDetailsService → the database the booth checks when issuing a wristband (who you are, what role).

