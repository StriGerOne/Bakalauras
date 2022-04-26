package com.example.springboot.Controller;

import com.example.springboot.AutoID.SequenceGeneratorService;
import com.example.springboot.Models.Reservation;
import com.example.springboot.Models.Restourant;
import com.example.springboot.Repositories.ReservationRepository;
import com.example.springboot.Repositories.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.springboot.Models.User.SEQUENCE_NAME;

@RestController
@RequestMapping
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestourantRepository restourantRepository;

    @Autowired
    private SequenceGeneratorService service;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @PostMapping("/newReservation")
    public String saveReservation(@RequestBody Reservation reservation) {
        if (!checkReservationAvailability(reservation.getReservationTime().toString(), reservation.getDuration().toString(), reservation.getRestouranId(), reservation.getPeopleAmount())) {
            reservation.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
            reservationRepository.save(reservation);
            return "New reservation made";
        } else {
            return "Too bad";
        }
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
        return reservationRepository.findByPeopleAmount(peopleAmount);
    }

    @GetMapping("/get1/{reservationTime}")
    public List<Reservation> findByReservationTime(@PathVariable String reservationTime) {
        return reservationRepository.findByReservationTime(LocalDateTime.parse(reservationTime, formatter));
    }

    @GetMapping("/get2")
    public List<Reservation> findByReservationTimeBetween(@RequestParam(name = "startDate") String start, @RequestParam(name = "endDate") String end) {
        return reservationRepository.findByReservationTimeBetween(LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter));
    }

    @GetMapping("/checkReservationAvailability")
    public @ResponseBody
    boolean checkReservationAvailability(@RequestParam(name = "startDate") String start, @RequestParam(name = "duration") String end, @RequestParam(name = "restId") long id, @RequestParam(name = "peopleNum") int numberOfPpl) {
        LocalDateTime endDate = LocalDateTime.parse(start, formatter).plusHours(LocalTime.parse(end).getHour());
        List<Reservation> reservationList = reservationRepository.findByReservationTimeBetweenAndRestouranId(LocalDateTime.parse(start, formatter), endDate, id);
        int occupiedSeats = reservationList.stream()
                //.filter(x -> x.getReservationTime().plusHours(x.getDuration().getHour()).isAfter(endDate))
                .map(Reservation::getPeopleAmount).mapToInt(Integer::intValue).sum();
        System.out.println(occupiedSeats);
        Optional<Restourant> currentRest = restourantRepository.findById(id);
        int allSeats = currentRest.stream()
                .map(Restourant::getNumberOfSeats).mapToInt(Integer::intValue).sum();
        System.out.println(allSeats);

        return allSeats - occupiedSeats > numberOfPpl;
    }

}