package com.example.springboot.Repositories;


import com.example.springboot.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    public User getUserByUsernameAndPassword(String name, String psw);
}

