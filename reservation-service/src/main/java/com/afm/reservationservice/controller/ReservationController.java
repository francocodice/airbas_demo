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
    private final RateRepository rateRepository;

    @PostMapping("/create")
    public Reservation createReservation(@RequestBody ReservationRequest request){
        logger.info("Creation Reservation" );
        System.out.println(request.toString());
        return null;
        //return reservationService.createReservation(request.getFlight(), request.getRate(), request.getPassenger(), request.getUsermail());
    }

    @PostMapping("/creates")
    public List<Reservation> createReservations(@RequestBody List<ReservationRequest> requests){
        List<Reservation> tmp = new LinkedList<>();
        logger.info("Creation Reservation" + requests.toArray().toString());
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

    @GetMapping("/addRates")
    public HttpStatus addRates(){
        Rate base = new Rate();
        base.setType("BASE");
        base.setPrice(new BigDecimal("10.00"));
        rateRepository.save(base);

        Rate plus = new Rate();
        plus.setType("PLUS");
        plus.setPrice(new BigDecimal("30.00"));
        rateRepository.save(plus);

        Rate premium = new Rate();
        premium.setType("PREMIUM");
        premium.setPrice(new BigDecimal("60.00"));
        rateRepository.save(premium);
        return HttpStatus.OK;
    }

    @GetMapping("/allRate")
    public List<Rate> allRate(){
        return rateRepository.findAll();

    }



}
