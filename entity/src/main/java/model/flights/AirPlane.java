package model.flights;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "airplane")
public class AirPlane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private AirPlaneType type;

    private Integer availableSeats;

    private Integer allSeats;

    @ElementCollection
    private Map<String, Integer> seats;

}


