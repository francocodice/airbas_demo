package com.afm.flightsservice.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateNameService {

    protected String generateAirPlaneName(Long id) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        salt.append(id);
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected String generateFlightName(Long id) {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        salt.append(id);
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
