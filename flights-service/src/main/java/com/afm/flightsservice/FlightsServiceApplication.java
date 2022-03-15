package com.afm.flightsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan(basePackages= {"com.afm.flightsservice","model.flights"})

public class FlightsServiceApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(FlightsServiceApplication.class, args);
    }

}
