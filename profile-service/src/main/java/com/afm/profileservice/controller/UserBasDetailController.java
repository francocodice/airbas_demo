package com.afm.profileservice.controller;


import com.afm.profileservice.service.UserBasDetailsService;
import lombok.RequiredArgsConstructor;
import model.auth.UserBasDetail;
import model.utils.UserPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserBasDetailController {
    private final UserBasDetailsService userBasDetailsService;

    @PostMapping("/register")
    public void signUp(@RequestBody UserPayload payload) {
        if(userBasDetailsService.findDetails(payload.getEmail()) != null) {
            userBasDetailsService.createDetailsUser(payload);
        }
    }


    @GetMapping("/details/{email}")
    public UserBasDetail details(@PathVariable String email)  {
        return userBasDetailsService.findDetails(email);
    }

    @GetMapping("/details")
    public List<UserBasDetail> details() {
        return userBasDetailsService.findAll();
    }

    @PostMapping("/update")
    public UserBasDetail update(@RequestBody UserPayload payload)  {
        return userBasDetailsService.updateDetailsUser(payload);
    }



}
