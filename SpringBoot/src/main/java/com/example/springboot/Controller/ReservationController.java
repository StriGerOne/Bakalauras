package com.example.springboot.Controller;

import com.example.springboot.AutoID.SequenceGeneratorService;
import com.example.springboot.Models.Reservation;
import com.example.springboot.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.springboot.Models.User.SEQUENCE_NAME;

@RestController
    @RequestMapping
    public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SequenceGeneratorService service;

    @PostMapping("/newReservation")
    public String saveReservation(@RequestBody Reservation reservation, Reservation today) {
        reservation.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
        reservationRepository.save(reservation);
        System.out.println(today);
        return "New reservation made";
    }

    @GetMapping("/allReservations")
    public List<Reservation> getReservation() {
        return reservationRepository.findAll();
    }

    @GetMapping("/getReservationBy/{id}")
    public Optional<Reservation> getReservation(@PathVariable Long id) {
        return reservationRepository.findById(id);
    }

    @DeleteMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
        return "Reservation with id " + id + " deleted";
    }

    @GetMapping("/get/{peopleAmount}")
    public List<Reservation> findBypeopleAmount(@PathVariable int peopleAmount) {
        return reservationRepository.findBypeopleAmount(peopleAmount);
    }

    @GetMapping("/get1/{reservationTime}")
    public List<Reservation> findByReservationTime(@PathVariable String reservationTime) {
        return reservationRepository.findByreservationTime(LocalDateTime.parse(reservationTime));
    }

    @GetMapping("/get2/{reservationTime}")
    public List<Reservation> findByReservationTimeBetween(@PathVariable LocalDateTime reservationTime) {
        return reservationRepository.findByReservationTimeBetween(reservationTime);
    }

}
