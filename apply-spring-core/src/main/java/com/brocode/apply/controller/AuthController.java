package com.brocode.apply.controller;

import com.brocode.apply.entity.User;
import com.brocode.apply.service.ApplyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = { "http://localhost:4200/", "http://127.0.0.1:4200/" })
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ApplyUserDetailsService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public  AuthController(ApplyUserDetailsService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if(userService.findByUsername(user.getUsername()).isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @RequestMapping("/login")
    public boolean login(@RequestBody User user) {
        Optional<User> user1 = userService.findByUsername(user.getUsername());
        if (user1.isEmpty()) return false;
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        return user1.get().getPassword().equals(encodedPassword);
    }
}
