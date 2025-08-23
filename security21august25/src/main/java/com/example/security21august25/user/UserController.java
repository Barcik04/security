package com.example.security21august25.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public User save(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/promote/{id}")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<?> promoteToAdmin(@PathVariable Long id) {
        User user = userService.findUserById(id);

        user.setRole(Role.ADMIN);
        userRepository.save(user);

        return ResponseEntity.ok("User " + user.getUserName() + " Promoted to admin");
    }
}
