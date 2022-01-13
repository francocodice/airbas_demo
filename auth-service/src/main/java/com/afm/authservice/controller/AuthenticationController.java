package com.afm.authservice.controller;


import com.afm.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import model.auth.AuthProvider;
import model.auth.UserBas;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserPayload payload) throws Exception {
        LoginRequest credential = new LoginRequest(payload.getEmail(), payload.getPassword());

        UserBas user = authenticationService.createUser(credential, AuthProvider.local);
        payload.setId(user.getId());
        payload.setPassword("");

        return new ResponseEntity(payload, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest loginReq) {
        String jwt = authenticationService.authenticateUser(loginReq);
        return new ResponseEntity(jwt, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> users(){
        return new ResponseEntity(authenticationService.findAll(), HttpStatus.OK);
    }


}
