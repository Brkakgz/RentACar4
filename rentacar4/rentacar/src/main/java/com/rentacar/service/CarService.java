package com.rentacar.service;

import com.rentacar.model.Car;
import com.rentacar.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public List<Car> findCarsByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }
    public boolean isCarAvailable(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        return car != null && car.getAvailableCount() > 0;
    }

    public void decreaseCarAvailability(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null && car.getAvailableCount() > 0) {
            car.setAvailableCount(car.getAvailableCount() - 1);
            carRepository.save(car);
        }
    }
}
