package com.example.springboot.Controller;

import com.example.springboot.AutoID.SequenceGeneratorService;
import com.example.springboot.Models.Restourant;
import com.example.springboot.Repositories.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.springboot.Models.User.SEQUENCE_NAME;


@RestController
@RequestMapping
public class RestouranController {

    @Autowired
    private RestourantRepository restourantRepository;

    @Autowired
    private SequenceGeneratorService service;

    @PostMapping("/addRestouran")
    public String saveRestouran(@RequestBody Restourant restourant) {
        restourant.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
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
    }
    }
