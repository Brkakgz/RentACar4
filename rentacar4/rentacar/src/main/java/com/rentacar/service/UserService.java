package com.rentacar.service;

import com.rentacar.model.Userlist;
import com.rentacar.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Optional<Userlist> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Userlist save(Userlist userlist) {
        return userRepository.save(userlist);
    }
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
