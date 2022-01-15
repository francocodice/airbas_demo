package com.afm.apigateway.controller;

import com.afm.apigateway.service.AuthService;
import lombok.RequiredArgsConstructor;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;


    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<?> users(){
        return new ResponseEntity(authService.allUser(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credential)  {
        return new ResponseEntity(authService.authenticateUser(credential), HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserPayload payload)  {
        UserPayload payloadId = authService.createUser(payload);
        return new ResponseEntity(payloadId, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/users/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email)  {
        authService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
