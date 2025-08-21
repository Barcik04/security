// src/main/java/com/example/security21august25/user/JpaUserDetailsService.java
package com.example.security21august25.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository users;

    public JpaUserDetailsService(UserRepository users) { this.users = users; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = users.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '%s' not found".formatted(username)));

        var auths = u.getRoles().stream()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUserName())
                .password(u.getPassword())
                .authorities(auths)
                .disabled(!u.isEnabled())
                .build();
    }
}
