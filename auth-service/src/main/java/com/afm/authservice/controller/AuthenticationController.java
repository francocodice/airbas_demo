package com.afm.authservice.controller;

import com.afm.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import model.auth.AuthProvider;
import model.auth.UserBas;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public UserPayload signUp(@RequestBody UserPayload payload)  {
        LoginRequest credential = new LoginRequest(payload.getEmail(), payload.getPassword());
        authenticationService.createUser(credential, AuthProvider.local);
        payload.setPassword("");
        return payload;
    }

    @PostMapping("/login")
    public String logIn(@RequestBody LoginRequest loginReq) {
        return authenticationService.authenticateUser(loginReq);
    }

    @GetMapping("/users")
    public List<UserBas> users() {
        return authenticationService.findAll();
    }

    @GetMapping("/users/delete/{email}")
    public UserBas deleteUser(@PathVariable String email){
        UserBas user = authenticationService.findUser(email);
        authenticationService.deleteUser(user);
        return user;
    }


}
