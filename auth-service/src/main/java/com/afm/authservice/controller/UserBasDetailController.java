package com.afm.authservice.controller;

import com.afm.authservice.exception.UsernameNotFoundException;
import com.afm.authservice.service.AuthenticationService;
import com.afm.authservice.service.UserBasDetailsService;
import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
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

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody UserPayload payload) {

        try{
            userBasDetailsService.createDetailsUser(payload);
        } catch (Exception e){
            return new ResponseEntity("Fail creating user Detail : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }


    @PostMapping("/details")
    public ResponseEntity<?> details(@RequestBody LoginRequest credentials){
        UserBas user = authenticationService.findUser(credentials.getEmail());
        if(user != null){
            return new ResponseEntity<>(userBasDetailsService.findDetails(user), HttpStatus.OK);
        } else{
            throw new UsernameNotFoundException("User not found : " + credentials.getEmail());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserPayload payload){
        try {
            userBasDetailsService.updateDetailsUser(payload);
        } catch (Exception e){
            throw new UsernameNotFoundException("User not Found email : " + payload.getEmail());
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
