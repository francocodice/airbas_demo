package com.afm.reservationservice.controller;

import com.afm.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import model.prenotation.Passenger;
import model.prenotation.Reservation;
import model.utils.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/reservation")
@RestController
@RequiredArgsConstructor
@CrossOrigin

public class ReservationController {
    private static Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    @PostMapping("/creates")
    public List<Reservation> createReservations(@RequestBody List<ReservationRequest> requests){
        List<Reservation> reservations = new LinkedList<>();
        logger.info("Creation Reservations" );

        for (ReservationRequest request : requests){
            Passenger p = new Passenger();
            p.setBirthdate(request.getPassengerDate());
            p.setFirstname(request.getPassangerName());
            p.setSecondname(request.getPassangerSurname());
            p.setTelephone(request.getPassangerPhone());

            reservations.add(reservationService.createReservation(request.getFlightName(), request.getAirPlaneName(),
                    request.getSeatCord(), request.getRate(), p, request.getUsermail()));
        }
        return reservations;
    }

    @GetMapping ("/get/{email}")
    public List<Reservation> getReservations(@PathVariable String email){
        return reservationService.getReservation(email);
    }

    @GetMapping ("/getall")
    public List<Reservation> getAll(){
        return reservationService.getAll();
    }

    @GetMapping ("/delete/{email}/{cod}")
    public List<Reservation> deleteReservations(@PathVariable String email, @PathVariable String cod){
        return reservationService.deleteReservation(email, cod);
    }

}
