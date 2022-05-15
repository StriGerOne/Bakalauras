package com.example.springboot.Repositories;

import com.example.springboot.Models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, Long> {
    List<Restaurant> findByRestaurantName(String restaurantName);
}




