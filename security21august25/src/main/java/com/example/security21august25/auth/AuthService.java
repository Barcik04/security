package com.example.security21august25.auth;

import com.example.security21august25.user.User;
import com.example.security21august25.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Transactional
    public void register(SignupRequest req) {
        if (users.existsByUserName(req.getUserName())) {
            throw new IllegalArgumentException("Username already taken");
        }
        var u = new User(req.getUserName(), encoder.encode(req.getPassword()));
        u.getRoles().add("USER");        // default role
        u.setEnabled(true);
        users.save(u);
    }
}
