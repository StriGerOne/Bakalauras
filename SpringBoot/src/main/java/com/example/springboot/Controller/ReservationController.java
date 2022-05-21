package com.example.springboot.Controller;

import com.example.springboot.AutoID.SequenceGeneratorService;
import com.example.springboot.Models.Reservation;
import com.example.springboot.Models.Restaurant;
import com.example.springboot.Models.Tables;
import com.example.springboot.Repositories.ReservationRepository;
import com.example.springboot.Repositories.RestaurantRepository;
import com.example.springboot.Repositories.RestaurantTableRepository;
import com.example.springboot.Repositories.UserRepository;
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
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SequenceGeneratorService service;

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @PostMapping("/newReservation")
    public String saveReservation(@RequestBody Reservation reservation) {
        //  if (!checkReservationAvailability(reservation.getReservationTime().toString(), reservation.getDuration().toString(), reservation.getRestaurantId(), reservation.getPeopleAmount())) {
        reservation.setId((long) service.getSequenceNumber(SEQUENCE_NAME));
        reservationRepository.save(reservation);
        return "New reservation made";
        // } else {
        //     return "Too bad";
        // }
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

    @PutMapping("/cancelReservation/{id}")
    Reservation cancelReservation(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setStatus("Canceled");
                    return reservationRepository.save(reservation);
                })
                .orElseGet(() -> {
                    System.out.println("Kažkas blogai");
                    return null;
                });
    }

    @GetMapping("/getReservationsByUser")
    public List<Reservation> findByUserId(@RequestParam(name = "UserId") long id) {
        return reservationRepository.findByUserId(id);
    }


    @GetMapping("/checkReservationAvailability")
    public @ResponseBody
    boolean checkReservationAvailability(@RequestParam(name = "startDate") String start, @RequestParam(name = "duration") String end, @RequestParam(name = "restId") long id, @RequestParam(name = "peopleNum") int numberOfPpl) {
        LocalDateTime endDate = LocalDateTime.parse(start, formatter).plusHours(LocalTime.parse(end).getHour());
        List<Reservation> reservationList = reservationRepository.findByReservationTimeBetweenAndRestaurantId(LocalDateTime.parse(start, formatter), endDate, id);
        int occupiedSeats = reservationList.stream()
                //.filter(x -> x.getReservationTime().plusHours(x.getDuration().getHour()).isAfter(endDate))
                .map(Reservation::getPeopleAmount).mapToInt(Integer::intValue).sum();
        System.out.println(occupiedSeats);
        Optional<Restaurant> currentRest = restaurantRepository.findById(id);
        int allSeats = currentRest.stream()
                .map(Restaurant::getNumberOfSeats).mapToInt(Integer::intValue).sum();
        System.out.println(allSeats);

        //Cia siaip viska logiskai skaiciuoja, kiek dabar laisvu vietu in total
        return allSeats - occupiedSeats > numberOfPpl;
    }

    @GetMapping("/checkReservationAvailabilityTables")
    public @ResponseBody
    List<Tables> checkReservationAvailabilityTables(@RequestParam(name = "startDate") String start, @RequestParam(name = "duration") String end, @RequestParam(name = "restId") long id, @RequestParam(name = "peopleNum") int numberOfPpl) {
        LocalDateTime endDate = LocalDateTime.parse(start, formatter).plusHours(LocalTime.parse(end).getHour());
        List<Reservation> reservationList = reservationRepository.findByReservationTimeBetweenAndRestaurantId(LocalDateTime.parse(start, formatter), endDate, id);
        List<Tables> tablesList = restaurantTableRepository.findAllByRestaurantId(id);
        //tablesList.removeIf(tables -> reservationList )
        //va cia truksta jums staliuku id. Mintis - traukiat rezervacijas pagal restorano id ir intervala laiko
        //tada kiekviena rezervacija turi tureti staliuko id
        //tada gaunat visu staliuku restorane sarasa ir is to saraso isimat uzimtus
        //grazina tik laisvus staliukus
        return tablesList;
    }

}