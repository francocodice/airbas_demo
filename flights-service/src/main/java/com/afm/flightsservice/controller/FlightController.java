package com.afm.flightsservice.controller;

import com.afm.flightsservice.service.AirPlaneService;
import com.afm.flightsservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import model.flights.AirPlane;
import model.flights.Flight;
import model.utils.RequestAddFlight;
import model.utils.RequestFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RequestMapping("/flights")
@RestController
@RequiredArgsConstructor
@CrossOrigin

public class FlightController {
    private static Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightService flightService;
    private final AirPlaneService airPlaneService;

    @Autowired
    public RestTemplate restTemplate;

    // @DUBUG
    @GetMapping("/allFlights")
    public List<Flight> getAll(){
        return flightService.getAll();
    }

    // @DUBUG
    @PostMapping("/offers")
    public List<Flight> findByDepartureCity(@RequestBody RequestFlight request){
        return flightService.findByDepartureCity(request.getDepartureCity());
    }

    // @DUBUG
    @GetMapping("/allAirplane")
    public List<AirPlane> getAllAirPlane(){
        return airPlaneService.getAllAirplane();
    }


    @PostMapping("/justGone")
    public List<Flight> findFlight(@RequestBody RequestFlight request){
        return flightService.findFlight(request.getDepartureCity(),
                request.getDestinationCity(),
                request.getDepartureDate());
    }

    @PostMapping("/fullTrip")
    public List<List<Flight>> findFlightWithReturn(@RequestBody RequestFlight request){
        List<List<Flight>> flights = new LinkedList<>();

        flights.add(flightService.findFlight(request.getDepartureCity(),
                request.getDestinationCity(),
                request.getDepartureDate()));

        flights.add(flightService.findFlight(request.getDestinationCity(),
                request.getDepartureCity(),
                request.getReturnDate()));

        return flights;
    }

    @GetMapping("/getAirplane")
    public AirPlane getAirPlane(@RequestParam String name){
        return airPlaneService.getAirPlane(name);
    }

    @GetMapping("/bookSeat")
    public AirPlane bookSeat(@RequestParam String name, @RequestParam String seatCord){
        return airPlaneService.addBookSeat(name, seatCord);
    }

    @GetMapping("/removeBookSeat")
    public AirPlane removeBookSeat(@RequestParam String name, @RequestParam String seatCord){
        return airPlaneService.removeBookSeat(name, seatCord);
    }

    @PostMapping("/add")
    public List<Flight> addFlights(@RequestBody List<RequestAddFlight> flights){
        for(RequestAddFlight newFlight : flights) {
            AirPlane currentAirPlane = airPlaneService.addAirPlane(newFlight);
            flightService.addFlight(newFlight, currentAirPlane.getName());
        }
        return null;
    }


}
