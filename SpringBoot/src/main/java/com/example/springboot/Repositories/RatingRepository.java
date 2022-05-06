package com.example.springboot.Repositories;

import com.example.springboot.Models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, Long> {


}
