package com.afm.profileservice.controller;


import com.afm.profileservice.service.UserBasDetailsService;
import lombok.RequiredArgsConstructor;
import model.profile.UserBasDetail;
import model.utils.UserPayload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserBasDetailController {
    private final UserBasDetailsService userBasDetailsService;

    @PostMapping("/register")
    public UserBasDetail signUp(@RequestBody UserPayload payload) {
        return userBasDetailsService.createDetailsUser(payload);
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
