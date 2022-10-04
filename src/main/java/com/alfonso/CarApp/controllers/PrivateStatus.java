package com.alfonso.CarApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateStatus {
    @GetMapping("/private/status")
    public String getPrivateStatus() {
        return "OK";
    }
}
