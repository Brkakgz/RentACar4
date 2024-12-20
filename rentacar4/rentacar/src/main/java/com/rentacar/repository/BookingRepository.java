package com.rentacar.repository;

import com.rentacar.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Spring Data JPA otomatik yöntem

    List<Booking> findByUserlist_Id(Long userId);
}
