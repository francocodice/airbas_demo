package com.afm.flightsservice.service;

import com.afm.flightsservice.repository.AirPlaneRepository;
import lombok.RequiredArgsConstructor;
import model.flights.AirPlane;
import model.flights.AirPlaneType;
import model.utils.RequestAddFlight;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AirPlaneService {
    private final AirPlaneRepository airPlaneRepository;
    private final GenerateNameService generateNameService;

    private final Integer SEATS_SMALL_AIRPLANE = 96;
    private final Integer SEATS_BIG_AIRPLANE = 192;

    public AirPlane addAirPlane(RequestAddFlight flight){
        AirPlane airPlane = new AirPlane();

        airPlane.setType(flight.getType());
        airPlane.setAllSeats((flight.getType().toString() == AirPlaneType.BIG.toString()) ? SEATS_BIG_AIRPLANE : SEATS_SMALL_AIRPLANE);
        airPlane.setAvailableSeats((flight.getType().toString() == AirPlaneType.BIG.toString()) ? SEATS_BIG_AIRPLANE : SEATS_SMALL_AIRPLANE);
        airPlane.setSeats(buildMapSeats(flight.getType()));
        airPlaneRepository.save(airPlane);

        airPlane.setName(generateNameService.generateAirPlaneName(airPlane.getId()));

        airPlaneRepository.save(airPlane);
        return airPlane;
    }

    public List<AirPlane> getAllAirplane(){
        return airPlaneRepository.findAll();
    }

    public AirPlane getAirPlane(String name){
        return airPlaneRepository.findByName(name);
    }

    public AirPlane addBookSeat(String name, String seatCord){
        AirPlane airPlane = airPlaneRepository.findByName(name);
        Map<String, Integer> seats = airPlane.getSeats();
        seats.put(seatCord,1);
        airPlane.setSeats(seats);
        airPlane.setAvailableSeats(airPlane.getAvailableSeats()-1);
        return airPlaneRepository.save(airPlane);
    }

    public AirPlane removeBookSeat(String name, String seatCord){
        AirPlane airPlane = airPlaneRepository.findByName(name);
        Map<String, Integer> seats = airPlane.getSeats();
        seats.put(seatCord,0);
        airPlane.setSeats(seats);
        airPlane.setAvailableSeats(airPlane.getAvailableSeats()+1);
        return airPlaneRepository.save(airPlane);
    }

    public Map<String, Integer> buildMapSeats(AirPlaneType type){
        Map<String, Integer> seats = new HashMap<>();
        char[] colum = {'A','B','C','D','E','F'};
        int size = (type.toString() == AirPlaneType.BIG.toString()) ? SEATS_BIG_AIRPLANE : SEATS_SMALL_AIRPLANE;
        int row = size/colum.length;

        for(int index = 1; index <= row; index ++){
            for(int j = 0; j < colum.length; j++){
                seats.put(index + "-" + colum[j], 0);
            }
        }

        return seats;
    }
}
