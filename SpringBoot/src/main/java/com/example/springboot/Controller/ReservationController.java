package com.example.springboot.Controller;

import com.example.springboot.AutoID.SequenceGeneratorService;
import com.example.springboot.Models.Reservation;
import com.example.springboot.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String saveReservation(@RequestBody Reservation reservation) {
        reservation.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
        reservationRepository.save(reservation);
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
}
