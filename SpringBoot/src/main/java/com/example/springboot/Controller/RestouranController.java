package com.example.springboot.Controller;

import com.example.springboot.Models.Restourant;
import com.example.springboot.Repositories.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
public class RestouranController {

    @Autowired
    private RestourantRepository restourantRepository;

    @PostMapping("/addRestouran")
    public String saveRestouran(@RequestBody Restourant restourant) {
        restourantRepository.save(restourant);
        return "New Restouran saved";
    }

    @GetMapping("/allRestouran")
    public List<Restourant> getRestouran() {
        return restourantRepository.findAll();
    }

    @GetMapping("/getResbouranby/{id}")
    public Optional<Restourant> getRestouran(@PathVariable Long id) {
        return restourantRepository.findById(id);
    }

    @DeleteMapping("/deleteRestauran/{id}")
    public String deleteRestouran(@PathVariable Long id) {
        restourantRepository.deleteById(id);
        return "Restouran with id " + id + " deleted";
        // Labas vakaras



        //
    }

    }
