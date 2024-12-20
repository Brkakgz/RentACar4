package com.rentacar.controller;

import com.rentacar.model.Car;
import com.rentacar.model.Category;
import com.rentacar.model.Booking;
import com.rentacar.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Yeni marka ekle
    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(adminService.addCategory(category));
    }

    // Yeni araba ekle
    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return ResponseEntity.ok(adminService.addCar(car));
    }

    // Arabayı güncelle
    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        return ResponseEntity.ok(adminService.updateCar(id, car));
    }

    // Arabayı sil (veya pasif hale getir)
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        adminService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    // Teslim edilmemiş kiralamaları görüntüle
    @GetMapping("/bookings/pending")
    public ResponseEntity<List<Booking>> getPendingBookings() {
        return ResponseEntity.ok(adminService.getPendingBookings());
    }
}
