package com.rentacar.controller;

import com.rentacar.model.Userlist;
import com.rentacar.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Userlist> registerUser(@RequestBody Userlist userlist) {
        Userlist savedUserlist = userService.save(userlist);
        return ResponseEntity.ok(savedUserlist);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Userlist userlist) {
        return userService.findByUsername(userlist.getUsername())
                .map(u -> ResponseEntity.ok("Login successful!"))
                .orElse(ResponseEntity.status(401).body("Invalid credentials!"));
    }
}
