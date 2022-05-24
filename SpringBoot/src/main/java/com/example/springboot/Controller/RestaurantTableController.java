package com.example.springboot.Controller;

import com.example.springboot.Models.Tables;
import com.example.springboot.Repositories.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class RestaurantTableController {
@Autowired
private RestaurantTableRepository restaurantTablesRepository;


    @PostMapping("/newRestaurantTable")
    public String saveTable (@RequestBody Tables tables) {
        restaurantTablesRepository.save(tables);
        return "Restaurant Rated";
    }

    @GetMapping("/allTables")
    public List<Tables> getTables() {
        return restaurantTablesRepository.findAll();
    }

}
