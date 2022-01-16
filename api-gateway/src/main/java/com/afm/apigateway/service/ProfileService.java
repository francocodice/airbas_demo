package com.afm.apigateway.service;

import model.profile.UserBasDetail;
import model.utils.UserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
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

    public UserPayload saveDetails(UserPayload payload){
        HttpEntity<UserPayload> credentialsHttpEntity = new HttpEntity<>(payload);

        UserPayload new_payload = restTemplate.postForObject(
                profileAddress + "/profile/register",
                credentialsHttpEntity,
               UserPayload.class
        );

        System.out.println(new_payload);

        return new_payload;
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
