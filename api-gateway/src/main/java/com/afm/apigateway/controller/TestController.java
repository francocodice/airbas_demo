package com.afm.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class TestController {

    @GetMapping("/secure")
    public String test(){
        return "Test";
    }
}
