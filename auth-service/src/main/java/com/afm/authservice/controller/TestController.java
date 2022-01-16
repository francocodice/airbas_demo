package com.afm.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private2")
public class TestController {

    @GetMapping("/secure2")
    public String test(){
        return "Test";
    }
}
