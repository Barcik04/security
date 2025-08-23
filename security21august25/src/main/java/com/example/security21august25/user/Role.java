package com.example.security21august25.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USER_READ)),
    ADMIN(Set.of(Permission.USER_READ, Permission.USER_CREATE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {         // ✅ constructor
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {   // ✅ getter
        return permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> auths = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toSet());

        auths.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return auths;
    }
}
