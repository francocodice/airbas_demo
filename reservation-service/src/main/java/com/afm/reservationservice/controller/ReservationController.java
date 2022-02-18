package com.afm.reservationservice.controller;

import com.afm.reservationservice.repository.RateRepository;
import com.afm.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
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
public class ReservationController {
    private static Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;
    private final RateRepository rateRepository;

    @PostMapping("/create")
    public Reservation createReservation(@RequestBody ReservationRequest request){
        logger.info("Creation Reservation" );
        System.out.println(request.toString());
        return reservationService.createReservation(request.getFlight(), request.getRate(), request.getPassenger(), request.getUsermail());
    }

    @PostMapping("/creates")
    public List<Reservation> createReservations(@RequestBody List<ReservationRequest> requests){
        List<Reservation> tmp = new LinkedList<>();
        logger.info("Creation Reservation" );
        //System.out.println(request.toString());
        for (ReservationRequest request : requests){
            tmp.add(reservationService.createReservation(request.getFlight(), request.getRate(), request.getPassenger(), request.getUsermail()));
        }
        return tmp;
    }

    @GetMapping("/addRates")
    public HttpStatus addRates(){
        Rate base = new Rate();
        base.setType("BASE");
        base.setHoldBaggage(false);
        base.setPrice(new BigDecimal("10.00"));
        rateRepository.save(base);

        Rate plus = new Rate();
        plus.setType("PLUS");
        plus.setHoldBaggage(true);
        plus.setPrice(new BigDecimal("30.00"));
        plus.setPriceForLuggage(new BigDecimal("10.00"));
        rateRepository.save(plus);

        Rate premium = new Rate();
        premium.setType("PREMIUM");
        premium.setHoldBaggage(true);
        premium.setPrice(new BigDecimal("60.00"));
        premium.setPriceForLuggage(new BigDecimal("20.00"));
        rateRepository.save(premium);

        return HttpStatus.OK;

    }

    @GetMapping("/allRate")
    public List<Rate> allRate(){
        return rateRepository.findAll();

    }



}
