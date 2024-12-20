package com.rentacar;

import com.rentacar.model.Role;
import com.rentacar.model.Userlist;
import com.rentacar.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Admin kullanıcı ekle
        if (userService.findByUsername("admin").isEmpty()) {
            Userlist admin = new Userlist();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setUsername("admin");
            admin.setPassword(userService.encodePassword("admin123"));
            admin.setRole(Role.ADMIN);
            userService.save(admin);
        }
    }
}
