package com.afm.apigateway.service;

import com.afm.apigateway.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import model.utils.LoginRequest;
import model.utils.TokenDto;
import model.utils.UserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
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

    public JwtUser authenticateUser(LoginRequest credentials) {
        HttpEntity<LoginRequest> credentialsHttpEntity = new HttpEntity<>(credentials);

        JwtUser currentUser = restTemplate.postForObject(
                authAddress + "/auth/login",
                credentialsHttpEntity,
                JwtUser.class
        );

        return currentUser;
    }

    public UserPayload createUser(UserPayload payload) {
        HttpEntity<UserPayload> credentialsHttpEntity = new HttpEntity<>(payload);

        UserPayload userDatailsData = restTemplate.postForObject(
                authAddress + "/auth/signup",
                credentialsHttpEntity,
                UserPayload.class
        );
        return userDatailsData;
    }

    public void deleteUser(UserPayload payload){
        restTemplate.getForEntity(
                authAddress + String.format("/auth/users/delete/%s", payload.getEmail()),
                Void.class);
    }

    public TokenDto loginGoogle(TokenDto token){
        HttpEntity<TokenDto> credentialsHttpEntity = new HttpEntity<>(token);

        TokenDto jwtFromGoogle = restTemplate.postForObject(
                authAddress + "/oauth/google",
                credentialsHttpEntity,
                TokenDto.class
        );
        return jwtFromGoogle;
    }


    public TokenDto loginFacebook(TokenDto token){
        HttpEntity<TokenDto> credentialsHttpEntity = new HttpEntity<>(token);

        TokenDto jwtFromFacebook = restTemplate.postForObject(
                authAddress + "/oauth/facebook",
                credentialsHttpEntity,
                TokenDto.class
        );
        return jwtFromFacebook;
    }


    public TokenDto loginAmazon(TokenDto token){
        HttpEntity<TokenDto> credentialsHttpEntity = new HttpEntity<>(token);

        TokenDto jwtFromAmazon = restTemplate.postForObject(
                authAddress + "/oauth/amazon",
                credentialsHttpEntity,
                TokenDto.class
        );
        return jwtFromAmazon;
    }

}
