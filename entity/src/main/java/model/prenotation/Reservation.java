package model.prenotation;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String name;
    private String cityArrival;
    private String cityDeparture;
    private String flightName;
    private String seatCord;
    private BigDecimal price;
    @OneToOne
    private Rate rate;
    @OneToOne
    private Passenger passenger;
}
