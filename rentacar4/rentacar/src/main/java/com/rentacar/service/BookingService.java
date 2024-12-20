package com.rentacar.service;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CarService carService;

    public BookingService(BookingRepository bookingRepository, CarService carService) {
        this.bookingRepository = bookingRepository;
        this.carService = carService;
    }

    public List<Booking> findBookingsByUserId(Long userId) {
        return bookingRepository.findByUserlist_Id(userId);
    }

    public Booking createBooking(Booking booking) {
        // Araba kiralanabilir mi kontrolü
        if (!carService.isCarAvailable(booking.getCar().getId())) {
            throw new IllegalArgumentException("Seçilen araba başka bir kullanıcı tarafından kiralanmıştır.");
        }

        // Fiyat hesaplama
        long days = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        booking.setTotalPrice(days * booking.getCar().getDailyPrice());

        // Arabayı kiralanmış hale getirme
        carService.decreaseCarAvailability(booking.getCar().getId());

        return bookingRepository.save(booking);
    }
}
