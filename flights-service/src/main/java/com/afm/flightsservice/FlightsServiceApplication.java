package com.afm.flightsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages= {"com.afm.flightsservice","model.flights"})

public class FlightsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightsServiceApplication.class, args);
    }

}
