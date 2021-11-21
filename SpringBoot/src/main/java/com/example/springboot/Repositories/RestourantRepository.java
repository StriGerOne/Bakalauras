package com.example.springboot.Repositories;

import com.example.springboot.Models.Restourant;
import com.example.springboot.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestourantRepository extends MongoRepository<Restourant, Integer> {
}