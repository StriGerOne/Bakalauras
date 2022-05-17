package com.example.springboot.Repositories;

import com.example.springboot.Models.Tables;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantTableRepository extends MongoRepository<Tables, String> {
    List<Tables> findByRestaurantId(Long id);

   // List<Tables> findByReservationTimeBetweenAndRestaurantId(LocalDateTime reservationTime, LocalDateTime reservationTime2, long id);

/** Reikia surasti laisvus stalus pagal ID + REZERVACIJOS LAIKA **/
}
