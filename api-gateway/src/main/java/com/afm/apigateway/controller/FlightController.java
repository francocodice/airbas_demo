package com.afm.apigateway.controller;

import com.afm.apigateway.service.FlightsService;
import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import model.flights.AirPlane;
import model.flights.Flight;
import model.utils.LoginRequest;
import model.utils.RequestFlight;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/")
@RestController
public class FlightController {
    private final FlightsService flightService;

    @PostMapping("flights/oneway")
    public ResponseEntity<?> findFlights(@RequestBody RequestFlight request)  {
        List<Flight> flightList = flightService.oneWayRest(request);
        return new ResponseEntity(flightList, HttpStatus.OK);
    }

    @PostMapping("flights/fulltrip")
    public ResponseEntity<?> findFlightsWithReturn(@RequestBody RequestFlight request)  {
        List<List<Flight>> flightList = flightService.fullTripRest(request);
        return new ResponseEntity(flightList, HttpStatus.OK);
    }

    @GetMapping("flights/cities")
    public ResponseEntity<?> getCity()  {
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping("/flights/{airplaneName}")
    public ResponseEntity<?> findAirplane(@PathVariable(name = "airplaneName") String airPlaneName)  {
        AirPlane airPlane = flightService.getAirplaneRest(airPlaneName);
        return new ResponseEntity(airPlane, HttpStatus.OK);
    }

    @GetMapping("/flights/bookSeat/{airplaneName}/{seatCord}")
    public ResponseEntity<?> getBookSeat(@PathVariable(name = "airplaneName") String airPlaneName,
                                         @PathVariable(name = "seatCord") String seatCord)  {

        AirPlane airPlane = flightService.bookSeatRest(airPlaneName, seatCord);
        return new ResponseEntity(airPlane, HttpStatus.OK);
    }

    @GetMapping("/flights/removeBookSeat/{airplaneName}/{seatCord}")
    public ResponseEntity<?> removeBookSeat(@PathVariable(name = "airplaneName") String airPlaneName,
                                         @PathVariable(name = "seatCord") String seatCord)  {

        AirPlane airPlane = flightService.removeBookSeatRest(airPlaneName, seatCord);
        return new ResponseEntity(airPlane, HttpStatus.OK);
    }


}
