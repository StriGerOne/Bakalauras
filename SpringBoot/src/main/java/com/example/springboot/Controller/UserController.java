package com.example.springboot.Controller;

import com.example.springboot.Models.User;
import com.example.springboot.Repositories.UserRepository;
import com.example.springboot.AutoID.SequenceGeneratorService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static com.example.springboot.Models.User.SEQUENCE_NAME;


@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SequenceGeneratorService service;

    @PostMapping("/addUser")
    public String saveUser(@RequestBody User user) {
        user.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
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
    public Optional<User> getUser(@PathVariable Long id) {
        return userRepo.findById(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "User" + id + " deleted";
    }

    @PutMapping("/updateuser/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {

        return userRepo.findById(id)
                .map(user -> {
                    user.setFname(newUser.getFname());
                    user.setLname(newUser.getLname());
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setPhone(newUser.getPhone());
                    user.setEmail(newUser.getEmail());
                    return userRepo.save(user);
                })
                .orElseGet(() -> {
                    System.out.println("Kažkas blogai");
                    return null;
                });
    }
}

