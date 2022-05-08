package com.example.springboot.Repositories;

import com.example.springboot.Models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, Long> {
    List<Rating> findByRestaurantId(Long id);

}
