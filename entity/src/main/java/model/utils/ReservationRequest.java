package model.utils;

import lombok.Getter;
import lombok.Setter;
import model.flights.Flight;
import model.prenotation.EnumRateType;
import model.prenotation.Passenger;
import model.prenotation.Rate;

@Getter
@Setter
public class ReservationRequest {
    private Flight flight;
    private String rate;
    private String usermail;
    private Passenger passenger;
}
