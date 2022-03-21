package model.utils;

import lombok.Getter;
import lombok.Setter;
import model.flights.Flight;
import model.prenotation.EnumRateType;
import model.prenotation.Passenger;
import model.prenotation.Rate;

import java.util.Date;

@Getter
@Setter
public class ReservationRequest {
    private String flightName;
    private String airPlaneName;
    private String seatCord;
    private String rate;
    private String usermail;
    private String passangerName;
    private String passangerSurname;
    private String passangerPhone;
    private Date passengerDate;
}
