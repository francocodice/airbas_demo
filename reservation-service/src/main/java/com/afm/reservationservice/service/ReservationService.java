package com.afm.reservationservice.service;

import com.afm.reservationservice.repository.PassengerRepository;
import com.afm.reservationservice.repository.RateRepository;
import com.afm.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import model.flights.Flight;
import model.prenotation.EnumRateType;
import model.prenotation.Passenger;
import model.prenotation.Rate;
import model.prenotation.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RateRepository rateRepository;
    private final PassengerRepository passengerRepository;

    public Reservation createReservation(Flight flight, String rateName, Passenger passenger, String userMail) {
        Reservation reservation = new Reservation();
        Rate rate = rateRepository.findByType(rateName);

        reservation.setCityArrival(flight.getArrivalCity());
        reservation.setCityDeparture(flight.getDepartureCity());
        reservation.setFlightName(flight.getName());
        reservation.setRate(rate);
        reservation.setPassenger(passenger);
        reservation.setUserEmail(userMail);
        reservation.setName("ASD");

        passengerRepository.save(passenger);
        reservation.setPassenger(passenger);

        BigDecimal result = flight.getPrice().add(rate.getPrice());
        reservation.setPrice( result);
        reservationRepository.save(reservation);


        return reservation;
    }

}
