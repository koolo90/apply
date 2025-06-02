package com.brocode.apply;

import com.brocode.apply.model.Users;
import com.brocode.apply.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.stream.Stream;

@SpringBootApplication
public class ApplyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApplyApplication.class, args);
    }

    /*@Bean
    @Profile("!test") // Don't run this in test profile
    CommandLineRunner init(UserService userService) {
        return args -> {
            // Initialize sample users
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                Users user = new Users(name, name.toLowerCase() + "@domain.com");
                userService.saveUser(user);
            });

            // Log all users
            userService.getAllUsers().forEach(System.out::println);

            System.out.println("Database initialized with sample users");
        };
    }*/
}
