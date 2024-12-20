package com.rentacar.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Userlist userlist; // Arabayı kiralayan kullanıcı

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car; // Kiralanan araba

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Userlist getUserlist() {
        return userlist;
    }

    public void setUserlist(Userlist userlist) {
        this.userlist = userlist;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private LocalDate startDate; // Kiralama başlangıç tarihi
    private LocalDate endDate; // Kiralama bitiş tarihi
    private String pickupLocation; // Alış adresi
    private String dropoffLocation; // Teslim adresi
    private double totalPrice; // Toplam fiyat
}
