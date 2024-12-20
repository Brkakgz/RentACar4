package com.rentacar.controller;

import com.rentacar.model.Userlist;
import com.rentacar.security.JwtUtil;
import com.rentacar.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Userlist> register(@RequestBody Userlist userlist) {
        userlist.setPassword(userService.encodePassword(userlist.getPassword()));
        return ResponseEntity.ok(userService.save(userlist));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Userlist userlist) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userlist.getUsername(), userlist.getPassword()));
            String token = jwtUtil.generateToken(userlist.getUsername());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
