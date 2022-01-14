package com.afm.apigateway.service;

import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    public RestTemplate restTemplate;

    @Value("${services.authentication.authAddress}")
    private String authAddress;

    public List<UserBas> allUser() {
        return restTemplate.exchange(
                authAddress+ "/auth/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserBas>>() {
                }
        ).getBody();
    }

    public String authenticateUser(LoginRequest credentials) {
        HttpEntity<LoginRequest> credentialsHttpEntity = new HttpEntity<>(credentials);

        String jwt = restTemplate.postForObject(
                authAddress + "/auth/login",
                credentialsHttpEntity,
                String.class
        );

        assert jwt != null;
        return jwt;
    }

    public UserPayload createUser(UserPayload payload) {
        HttpEntity<UserPayload> credentialsHttpEntity = new HttpEntity<>(payload);

        UserPayload payload_returned = restTemplate.postForObject(
                authAddress + "/auth/signup",
                credentialsHttpEntity,
                UserPayload.class
        );

        return payload_returned;
    }

    public void deleteUser(String email){
        restTemplate.getForEntity(
                authAddress + String.format("/auth/users/delete/%s", email),
                Void.class);

    }

}
