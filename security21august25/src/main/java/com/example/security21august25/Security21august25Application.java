package com.example.security21august25;

import com.example.security21august25.user.Role;
import com.example.security21august25.user.User;
import com.example.security21august25.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Security21august25Application {

    public static void main(String[] args) {
        SpringApplication.run(Security21august25Application.class, args);
    }

    // Seeds an ADMIN user once at startup if missing
    @Bean
    CommandLineRunner seed(UserRepository repo, PasswordEncoder enc) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                User u = new User("admin", enc.encode("admin123"), Role.ADMIN);
                repo.save(u);
            }
        };
    }
}
