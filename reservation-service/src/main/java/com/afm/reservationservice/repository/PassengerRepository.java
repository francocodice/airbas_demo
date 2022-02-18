package com.afm.reservationservice.repository;

import model.prenotation.Passenger;
import model.prenotation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
