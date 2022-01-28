package com.afm.flightsservice.service;

import com.afm.flightsservice.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import model.flights.Flight;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flight addFlight(Flight flight){
        //Flight flight = new Flight();
        flightRepository.save(flight);
        return flight;
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

    public List<Flight> findGoOnly(String departureCity, String arrivalCity, Date departureDate){
        return flightRepository.findGoOnly(departureCity, arrivalCity, departureDate);
    }

    public List<Flight> findGoAndReturn(String departureCity, String arrivalCity, Date departureDate, Date arrivalDate){
        return flightRepository.findGoAndReturn(departureCity, arrivalCity, departureDate, arrivalDate);
    }


}
