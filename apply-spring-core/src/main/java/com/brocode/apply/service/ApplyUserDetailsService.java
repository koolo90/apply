package com.brocode.apply.service;

import com.brocode.apply.entity.User;
import com.brocode.apply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplyUserDetailsService implements UserDetailsService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    @Autowired
    public ApplyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if(byUsername.isPresent()){
            return org.springframework.security.core.userdetails.
                    User.withUsername(byUsername.get().getUsername())
                    .password(byUsername.get().getPassword())
                    .authorities(byUsername.get().getRoles().split(","))
                    .build();
        }
        throw new UsernameNotFoundException(username);
    }
}
