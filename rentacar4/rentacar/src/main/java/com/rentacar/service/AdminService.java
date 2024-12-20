package com.rentacar.service;

import com.rentacar.model.Car;
import com.rentacar.model.Category;
import com.rentacar.model.Booking;
import com.rentacar.repository.BookingRepository;
import com.rentacar.repository.CarRepository;
import com.rentacar.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;

    public AdminService(CarRepository carRepository, CategoryRepository categoryRepository, BookingRepository bookingRepository) {
        this.carRepository = carRepository;
        this.categoryRepository = categoryRepository;
        this.bookingRepository = bookingRepository;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }


    public Car updateCar(Long id, Car updatedCar) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        car.setBrand(updatedCar.getBrand());
        car.setModel(updatedCar.getModel());
        car.setColor(updatedCar.getColor());
        car.setAutomatic(updatedCar.isAutomatic());
        car.setMileage(updatedCar.getMileage());
        car.setDailyPrice(updatedCar.getDailyPrice());
        car.setImageUrl(updatedCar.getImageUrl());
        car.setAvailableCount(updatedCar.getAvailableCount());
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<Booking> getPendingBookings() {
        return bookingRepository.findAll(); // Daha spesifik filtreleme eklenebilir.
    }
}
