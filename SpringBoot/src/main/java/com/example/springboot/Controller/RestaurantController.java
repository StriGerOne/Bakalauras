package com.example.springboot.Controller;

import com.example.springboot.AutoID.SequenceGeneratorService;
import com.example.springboot.Models.Restaurant;
import com.example.springboot.Repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.springboot.Models.User.SEQUENCE_NAME;


@RestController
@RequestMapping
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SequenceGeneratorService service;

    @PostMapping("/addRestaurant")
    public String saveRestaurant(@RequestBody Restaurant restaurant) {
        restaurant.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
        restaurantRepository.save(restaurant);
        return "New Restaurant saved";
    }

    @GetMapping("/allRestaurant")
    public List<Restaurant> getRestaurant() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/getRestaurantby/{id}")
    public Optional<Restaurant> getRestaurant(@PathVariable Long id) {
        return restaurantRepository.findById(id);
    }

    @DeleteMapping("/deleteRestaurant/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantRepository.deleteById(id);
            return "Restaurant with id " + id + " deleted";
    }
    }