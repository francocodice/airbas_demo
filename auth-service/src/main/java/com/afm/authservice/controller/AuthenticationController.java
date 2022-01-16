package com.afm.authservice.controller;

import com.afm.authservice.security.JwtUser;
import com.afm.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import model.auth.AuthProvider;
import model.auth.ERole;
import model.auth.UserBas;
import model.utils.LoginRequest;
import model.utils.UserPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;



    @PostMapping("/signup")
    public UserPayload signUp(@RequestBody UserPayload payload)  {
        LoginRequest credential = new LoginRequest(payload.getEmail(), payload.getPassword());
        //TODO Gestire registrazione + jwt
        authenticationService.createUser(credential, AuthProvider.local, ERole.ROLE_USER);
        payload.setPassword("");
        return payload;
    }

    @GetMapping("/signup/admin")
    public UserBas signUpAdmin(){
        return null;
    }


    @PostMapping("/login")
    public UserDetails logIn(@RequestBody LoginRequest loginReq, HttpServletResponse response) {
        UserDetails details = authenticationService.authenticateUser(loginReq);

        //TODO GENERATE TOKEN ON API-GATEWAY
        //final String token = jwtTokenUtil.generateToken(details);
        //response.setHeader(tokenHeader,token);
        //System.out.println(token);
        return details;
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
