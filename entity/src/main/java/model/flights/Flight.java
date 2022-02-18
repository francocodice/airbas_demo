package model.flights;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "flight")
@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDate;

    private String departureCity;

    private String arrivalCity;

    private String departureAirport;

    private String arrivalAirport;

    private String airPlaneName;

    private BigDecimal price;

}
