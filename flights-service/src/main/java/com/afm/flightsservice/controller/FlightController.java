package com.afm.flightsservice.controller;

import com.afm.flightsservice.service.AirPlaneService;
import com.afm.flightsservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import model.flights.AirPlane;
import model.flights.Flight;
import model.utils.RequestAddFlight;
import model.utils.RequestFlight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RequestMapping("/flights")
@RestController
@RequiredArgsConstructor
public class FlightController {
    private static Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightService flightService;
    private final AirPlaneService airPlaneService;

    @PostMapping("/add")
    public Flight addFlight(@RequestBody RequestAddFlight newFlight){
        logger.info("Adding flight" );
        AirPlane currentAirPlane = airPlaneService.addAirPlane(newFlight);
        return flightService.addFlight(newFlight, currentAirPlane.getName());
    }

    @GetMapping("/allFlights")
    public List<Flight> getAll(){
        return flightService.getAll();
    }

    @PostMapping("/getByDate")
    public List<Flight> findByDate(@RequestBody RequestFlight request){
        return flightService.findByDepartureDate(request.getDepartureDate());
    }

    @PostMapping("/findByDepartureCity")
    public List<Flight> findByDepartureCity(@RequestBody RequestFlight request){
        return flightService.findByDepartureCity(request.getDepartureCity());
    }

    @PostMapping("/justGone")
    public List<Flight> findFlight(@RequestBody RequestFlight request){
        logger.info("Searching flights (justGone) : " + request.toString());

        return flightService.findFlight(request.getDepartureCity(), request.getDestinationCity(), request.getDepartureDate());
    }

    @PostMapping("/fullTrip")
    public List<List<Flight>> findFlightWithReturn(@RequestBody RequestFlight request){
        logger.info("Searching flights (FullTrip) : " + request.toString());

        List<List<Flight>> flights = new LinkedList<>();
        flights.add(flightService.findFlight(request.getDepartureCity(), request.getDestinationCity(), request.getDepartureDate()));
        flights.add(flightService.findFlight(request.getDestinationCity(), request.getDepartureCity(), request.getReturnDate()));
        return flights;
    }

    @GetMapping("/allAirplane")
    public List<AirPlane> getAllAirPlane(){
        return airPlaneService.getAllAirplane();
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




}
