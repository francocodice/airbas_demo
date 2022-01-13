package com.afm.authservice.controller;

import com.afm.authservice.service.AuthenticationService;
import com.afm.authservice.service.UserBasDetailsService;
import lombok.RequiredArgsConstructor;
import model.auth.AuthProvider;
import model.auth.UserBas;
import model.auth.UserBasDetail;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserBasDetailController {
    private final AuthenticationService authenticationService;
    private final UserBasDetailsService userBasDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserPayload payload) {
        LoginRequest credential = new LoginRequest(payload.getEmail(), payload.getPassword());
        UserBas u;
        try{
            u = authenticationService.createUser(credential, AuthProvider.local);
        } catch (Exception e){
            return new ResponseEntity("Fail creating user : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        try{
            userBasDetailsService.createDetailsUser(u,payload);
        } catch (Exception e){
            return new ResponseEntity("Fail creating user Detail : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(payload, HttpStatus.OK);
    }


    @PostMapping("/details")
    public ResponseEntity<?> details(@RequestBody LoginRequest credentials){
        UserBas user = authenticationService.findUser(credentials.getEmail());
        return new ResponseEntity<>(userBasDetailsService.findDetails(user), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserPayload payload){
        UserBas user;
        user = authenticationService.findUser(payload.getEmail());

        if(user != null){
            UserBasDetail userDetail = userBasDetailsService.findDetails(user);
            userBasDetailsService.updateDetailsUser(user,payload);

            return new ResponseEntity<>(userDetail, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("User not found ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
