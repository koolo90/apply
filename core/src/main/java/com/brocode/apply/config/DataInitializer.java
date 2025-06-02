package com.brocode.apply.config;

import com.brocode.apply.model.Role;
import com.brocode.apply.model.Users;
import com.brocode.apply.repository.RoleRepository;
import com.brocode.apply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Initialize roles
        initRoles();
        
        // Initialize users
        initUsers();
    }

    private void initRoles() {
        // Create roles if they don't exist
        if (roleRepository.findByName(Role.ROLE_USER).isEmpty()) {
            Role userRole = new Role(Role.ROLE_USER);
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName(Role.ROLE_ADMIN).isEmpty()) {
            Role adminRole = new Role(Role.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }

    private void initUsers() {
        // Create admin user if it doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            Users adminUser = new Users();
            adminUser.setName("Admin User");
            adminUser.setEmail("admin@example.com");
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123"); // Plain text password as required

            // Add roles
            roleRepository.findByName(Role.ROLE_ADMIN).ifPresent(adminUser::addRole);
            roleRepository.findByName(Role.ROLE_USER).ifPresent(adminUser::addRole);

            userRepository.save(adminUser);
        }

        // Create regular user if it doesn't exist
        if (userRepository.findByUsername("user").isEmpty()) {
            Users regularUser = new Users();
            regularUser.setName("Regular User");
            regularUser.setEmail("user@example.com");
            regularUser.setUsername("user");
            regularUser.setPassword("user123"); // Plain text password as required

            // Add role
            roleRepository.findByName(Role.ROLE_USER).ifPresent(regularUser::addRole);

            userRepository.save(regularUser);
        }
    }
}