package com.afm.flightsservice.repository;

import model.flights.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {


    @Query(value="SELECT * FROM Flight f WHERE f.departure_date <= :departureDate", nativeQuery = true)
    List<Flight> findByDepartureDate(@Param("departureDate")Date departureDate);

    @Query(value="SELECT * FROM Flight f WHERE f.departure_city = :departureCity ", nativeQuery = true)
    List<Flight> findByDepartureCity(@Param("departureCity")String departureCity);

    @Query(value="SELECT * FROM Flight f WHERE f.departure_city = :departureCity " +
            "AND f.arrival_city = :arrivalCity AND f.departure_date > :departureDate", nativeQuery = true)
    List<Flight> findFlight(@Param("departureCity")String departureCity,
                            @Param("arrivalCity")String arrivalCity, @Param("departureDate")Date departureDate);

    @Query(value="SELECT * FROM Flight f WHERE f.departure_city = :departureCity " +
            "AND f.arrival_city = :arrivalCity and f.departure_date = :departureDate and f.arrival_date = :arrivalDate", nativeQuery = true)
    List<Flight> findGoAndReturn(@Param("departureCity")String departureCity,
                            @Param("arrivalCity")String arrivalCity, @Param("departureDate")Date departureDate, @Param("departureDate")Date arrivalDate);
}
