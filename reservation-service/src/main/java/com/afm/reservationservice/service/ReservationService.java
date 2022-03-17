package com.afm.reservationservice.service;

import com.afm.reservationservice.repository.PassengerRepository;
import com.afm.reservationservice.repository.RateRepository;
import com.afm.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import model.prenotation.Passenger;
import model.prenotation.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PassengerRepository passengerRepository;

    public Reservation createReservation(String flightName, String airPlaneName,
                                         String rateName, Passenger passenger, String userMail) {
        Reservation reservation = new Reservation();
        //Rate rate = rateRepository.findByType(rateName);

        reservation.setAirPlaneName(airPlaneName);
        reservation.setFlightName(flightName);
        reservation.setRate(rateName);
        reservation.setPassenger(passenger);
        reservation.setUserEmail(userMail);
        reservation.setName("ASD");

        passengerRepository.save(passenger);
        reservation.setPassenger(passenger);
        //BigDecimal result = flight.getPrice().add(rate.getPrice());
        //reservation.setPrice( result);
        reservationRepository.save(reservation);


        return reservation;
    }

}
