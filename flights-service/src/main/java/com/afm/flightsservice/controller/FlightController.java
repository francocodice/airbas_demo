package com.afm.flightsservice.controller;

import com.afm.flightsservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import model.flights.Flight;
import model.flights.RequestFlight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/flights")
@RestController
@RequiredArgsConstructor
public class FlightController {
    private static Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightService flightService;

    @PostMapping("/add")
    public Flight addFlight(@RequestBody Flight newFlight){
        logger.info("adding flights");
        return flightService.addFlight(newFlight);
    }

    @GetMapping("/getAll")
    public List<Flight> getAll(){
        return flightService.getAll();
    }

    @PostMapping("/findDistinctByDepartureDate")
    public List<Flight> findDistinctByDepartureDate(@RequestBody RequestFlight request){
        return flightService.findByDepartureDate(request.getDepartureDate());
    }
//  1) citta partenza, città arrivo, data partenza findGoOnly
//  2)citta partenza, citta arrivo, data partenza, data arrivo
//  3)(opzionale) città partenza, città arrivo

    @PostMapping("/findByDepartureCity")
    public List<Flight> findByDepartureCity(@RequestBody RequestFlight request){
        return flightService.findByDepartureCity(request.getDepartureCity());
    }

    @PostMapping("/findGoOnly")
    public List<Flight> findGoOnly(@RequestBody RequestFlight request){
        return flightService.findGoOnly(request.getDepartureCity(), request.getArrivalCity(), request.getDepartureDate());
    }

    @PostMapping("/findGoAndReturn")
    public List<Flight> findGoAndReturn(@RequestBody RequestFlight request){
        return flightService.findGoAndReturn(request.getDepartureCity(), request.getArrivalCity(), request.getDepartureDate(), request.getArrivalDate());
    }


}
