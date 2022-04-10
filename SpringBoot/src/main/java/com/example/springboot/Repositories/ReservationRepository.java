package com.example.springboot.Repositories;

import com.example.springboot.Models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Repository
public interface ReservationRepository extends MongoRepository<Reservation, Long> {
    List<Reservation> findBypeopleAmount(int peopleAmount);
    List<Reservation> findByreservationTime(LocalDateTime reservationTime);
    List<Reservation> findByReservationTimeBetween(LocalDateTime reservationTime);
}