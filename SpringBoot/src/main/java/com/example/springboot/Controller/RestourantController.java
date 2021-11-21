package com.example.springboot.Controller;

import com.example.springboot.Models.Restourant;
import com.example.springboot.Models.User;
import com.example.springboot.Repositories.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class RestourantController {

    @Autowired
    private RestourantRepository restourantRepository;

    @PostMapping("/addRestourant")
    public String saveRestourant(@RequestBody Restourant restourant) {
        restourantRepository.save(restourant);
        return "Restourant saved";
    }
    @GetMapping("/allRestourant")
    public List<Restourant> getRestourant() {
        return restourantRepository.findAll();
    }
}
