package com.afm.flightsservice.repository;

import model.flights.AirPlane;
import model.flights.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirPlaneRepository extends JpaRepository<AirPlane, Long> {
    AirPlane findByName(String name);
}
