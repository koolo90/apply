package com.brocode.apply.controller;

import com.brocode.apply.entity.User;
import com.brocode.apply.service.ApplyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ApplyUserDetailsService userService;

    @Autowired
    public  AuthController(ApplyUserDetailsService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if(userService.findByUsername(user.getUsername()).isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/login")
    public ResponseEntity<User> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
