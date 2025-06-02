package com.brocode.apply.service;

import com.brocode.apply.model.Role;
import com.brocode.apply.model.Users;
import com.brocode.apply.repository.RoleRepository;
import com.brocode.apply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, 
                      UserRepository userRepository,
                      RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public boolean login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Users register(Users user, boolean isAdmin) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        // Add user role
        Role userRole = roleRepository.findByName(Role.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User role not found"));
        user.addRole(userRole);

        // Add admin role if requested
        if (isAdmin) {
            Role adminRole = roleRepository.findByName(Role.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            user.addRole(adminRole);
        }

        // Save user
        return userRepository.save(user);
    }

    public Optional<Users> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return userRepository.findByUsername(authentication.getName());
    }

    // Initialize roles if they don't exist
    @Transactional
    public void initRoles() {
        if (roleRepository.findByName(Role.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(Role.ROLE_USER));
        }
        if (roleRepository.findByName(Role.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(Role.ROLE_ADMIN));
        }
    }
}