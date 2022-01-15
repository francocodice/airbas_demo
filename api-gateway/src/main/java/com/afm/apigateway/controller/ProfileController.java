package com.afm.apigateway.controller;

import com.afm.apigateway.service.ProfileService;
import lombok.RequiredArgsConstructor;
import model.auth.UserBasDetail;
import model.exception.ResourceNotFoundException;
import model.utils.UserPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/register")
    public void signUp(@RequestBody UserPayload payload) {
        profileService.register(payload);
    }

    @GetMapping("/details/{email}")
    public ResponseEntity<?> details(@PathVariable String email)  {
        UserBasDetail detail = profileService.getDetailsByEmail(email);
        if(detail == null){
            throw new ResourceNotFoundException("User detail not found");
        } else {
            return new ResponseEntity<>(detail, HttpStatus.CREATED);
        }
    }

    @GetMapping("/details")
    public List<UserBasDetail> allDetails() {
        return profileService.allDetails();
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserPayload payload)  {
        UserBasDetail updated = profileService.update(payload);
        if(updated == null){
            throw new ResourceNotFoundException("User not found");
        }
        return new ResponseEntity(updated, HttpStatus.CREATED);

    }
}
