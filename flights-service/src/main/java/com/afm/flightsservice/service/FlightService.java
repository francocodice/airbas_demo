package com.afm.flightsservice.service;

import com.afm.flightsservice.repository.AirPlaneRepository;
import com.afm.flightsservice.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import model.flights.AirPlane;
import model.flights.Flight;
import model.flights.RequestAddFlight;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final GenerateNameService generateNameService;

    public Flight addFlight(RequestAddFlight flight, String airPlaneName){

        Flight f = new Flight();
        f.setDepartureCity(flight.getDepartureCity());
        f.setArrivalCity(flight.getArrivalCity());
        f.setArrivalDate(flight.getArrivalDate());
        f.setDepartureDate(flight.getDepartureDate());
        f.setArrivalAirport(flight.getArrivalAirport());
        f.setDepartureAirport(flight.getDepartureAirport());
        f.setAirPlaneName(airPlaneName);
        flightRepository.save(f);
        f.setName(generateNameService.generateFlightName(f.getId()));

        return f;
    }


    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    public List<Flight> findByDepartureDate(Date departureDate){
        return flightRepository.findByDepartureDate(departureDate);
    }

    public List<Flight> findByDepartureCity(String departureCity){
        return flightRepository.findByDepartureCity(departureCity);
    }

    public List<Flight> findFlight(String departureCity, String arrivalCity, Date departureDate){
        return flightRepository.findFlight(departureCity, arrivalCity, departureDate);
    }


}
