package com.afm.flightsservice.service;

import com.afm.flightsservice.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import model.flights.Flight;
import model.utils.RequestAddFlight;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
        f.setPrice(new BigDecimal(flight.getPrice()));
        flightRepository.save(f);
        f.setName(generateNameService.generateFlightName(f.getId()));
        flightRepository.save(f);

        return f;
    }


    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    public List<Flight> findByDepartureCity(String departureCity){
        return flightRepository.findByDepartureCity(departureCity);
    }

    public List<Flight> findFlight(String departureCity, String arrivalCity, Date departureDate){
        return flightRepository.findFlight(departureCity, arrivalCity, departureDate);
    }


}
