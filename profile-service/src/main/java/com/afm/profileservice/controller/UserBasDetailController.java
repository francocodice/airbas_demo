package com.afm.profileservice.controller;


import com.afm.profileservice.service.UserBasDetailsService;
import lombok.RequiredArgsConstructor;
import model.utils.UserPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserBasDetailController {
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


    @GetMapping("/details/{email}")
    public ResponseEntity<?> details(@PathVariable String email) throws Exception {
        try {
            return new ResponseEntity(userBasDetailsService.findDetails(email) , HttpStatus.OK);
        }catch (Exception e)  {
            throw new Exception("User not found : " + email);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> details() {
        return new ResponseEntity(userBasDetailsService.findAll() , HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserPayload payload) throws Exception {
        try {
            userBasDetailsService.updateDetailsUser(payload);
        } catch (Exception e){
            throw new Exception("User not Found email : " + payload.getEmail());
        }
        return new ResponseEntity(payload, HttpStatus.OK);
    }

}
