package com.example.springboot.Controller;

import com.example.springboot.Models.User;
import com.example.springboot.Repositories.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Properties;


@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/addUser")
    public String saveUser(@RequestBody User user) {
        userRepo.save(user);
        return "User saved";
    }

    @PostMapping("/authenticate")
    public User authenticate(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        User user = userRepo.getUserByUsernameAndPassword(properties.getProperty("login"), properties.getProperty("psw"));
        return user;
    }

    @GetMapping("/allUser")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/Usersby/{id}")
    public Optional<User> getUser(@PathVariable int id) {
        return userRepo.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userRepo.deleteById(id);
        return "User" + id + " deleted";
    }
}
