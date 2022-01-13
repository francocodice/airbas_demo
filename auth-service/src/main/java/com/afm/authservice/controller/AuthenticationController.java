package com.afm.authservice.controller;


import com.afm.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import model.utils.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/users")
    public ResponseEntity<?> users(){
        return new ResponseEntity(authenticationService.findAll(), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest loginReq){
        String jwt;
        try {
            jwt = authenticationService.authenticateUser(loginReq);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(jwt, HttpStatus.OK);
    }

}
