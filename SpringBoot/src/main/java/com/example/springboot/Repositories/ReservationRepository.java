package com.example.springboot.Repositories;

import com.example.springboot.Models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Repository
public interface ReservationRepository extends MongoRepository<Reservation, Long> {
    List<Reservation> findByPeopleAmount(int peopleAmount);
    List<Reservation> findByReservationTime(LocalDateTime reservationTime);
    List<Reservation> findReservationByReservationTimeAfterAndRestouranId(LocalDateTime reservationTime,long id);
    //Turi gi 2 datos buti, between reiskia intervala
    List<Reservation> findByReservationTimeBetween(LocalDateTime reservationTime, LocalDateTime reservationTime2);
    List<Reservation> findByReservationTimeBetweenAndRestouranId(LocalDateTime reservationTime, LocalDateTime reservationTime2, long id);

}