package com.rentacar.repository;

import com.rentacar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrand(String brand); // Markaya göre araba bul
}
