package com.alfonso.CarApp.controllers;

import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarsController {
    @Autowired
    private CarsService carsService;



    @PostMapping("/admin")
        public ResponseEntity<?> insert(@RequestBody Car[] carsArray) {

        carsService.saveCars(carsArray);

        return new ResponseEntity<>("\"description\": \"New record created in a database\"", HttpStatus.CREATED);}

}
