package com.afm.reservationservice.controller;

import com.afm.reservationservice.repository.RateRepository;
import com.afm.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import model.prenotation.Passenger;
import model.prenotation.Rate;
import model.prenotation.Reservation;
import model.utils.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        List<Reservation> tmp = new LinkedList<>();
        logger.info("Creation Reservations" );
        //System.out.println(request.toString());
        for (ReservationRequest request : requests){
            Passenger p = new Passenger();
            // p.setBirthdate(request.getPassengerDate());
            p.setFirstname(request.getPassangerName());
            p.setFirstname(request.getPassangerSurname());
            p.setTelephone(request.getPassangerPhone());

            tmp.add(reservationService.createReservation(request.getFlightName(), request.getAirPlaneName(),
                    request.getRate(), p, request.getUsermail()));
        }
        return tmp;
    }


}
