package com.example.springboot.Controller;

import com.example.springboot.AutoID.SequenceGeneratorService;
import com.example.springboot.Models.Rating;
import com.example.springboot.Repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.springboot.Models.User.SEQUENCE_NAME;

@RestController
@RequestMapping
public class RestaurantRating {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private SequenceGeneratorService service;

    @PostMapping("/rateRestaurant")
    public String saveRate (@RequestBody Rating rating) {
        rating.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
        ratingRepository.save(rating);
        return "Restaurant Rated";
    }

    @GetMapping("/allRates")
    public List<Rating> getRating() {
        return ratingRepository.findAll();
    }

    @GetMapping("/getRateByRestaurant")
    public List<Rating> findByRestaurantId(@RequestParam(name = "RestaurantId") long id) {
        return ratingRepository.findByRestaurantId(id);
    }

    @GetMapping("/getRateByUser")
    public List<Rating> findByUserId(@RequestParam(name = "UserId") long id) {
        return ratingRepository.findByUserId(id);
    }




}


