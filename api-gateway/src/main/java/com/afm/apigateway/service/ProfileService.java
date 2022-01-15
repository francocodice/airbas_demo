package com.afm.apigateway.service;

import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import model.auth.UserBasDetail;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class ProfileService {

    @Autowired
    public RestTemplate restTemplate;

    @Value("${services.authentication.profileAddress}")
    private String profileAddress;

    public List<UserBasDetail> allDetails() {
        return restTemplate.exchange(
                profileAddress+ "/profile/details",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserBasDetail>>() {
                }
        ).getBody();
    }

    public void register(UserPayload payload){
        HttpEntity<UserPayload> credentialsHttpEntity = new HttpEntity<>(payload);

       restTemplate.postForObject(
                profileAddress + "/profile/register",
                credentialsHttpEntity,
                Void.class
        );
    }


    public UserBasDetail getDetailsByEmail(String email) {
        return restTemplate.exchange(
                profileAddress + String.format("/profile/details/%s", email),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<UserBasDetail>() {
                }
        ).getBody();
    }

    public UserBasDetail update(UserPayload payload) {
        return restTemplate.postForObject(
                    profileAddress + "/profile/update",
                    payload,
                    UserBasDetail.class);

    }
}
