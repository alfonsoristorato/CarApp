package com.alfonso.CarApp.controllers;

import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
@Validated
public class CarsController {
    @Autowired
    private CarsService carsService;


//    @PostMapping("/admin")
    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insert( @NotNull @NotEmpty @RequestBody List<@Valid Car> carsList) {

        carsService.saveCars(carsList);
        Map<String, String> responseObject = new HashMap<>();
        responseObject.put("description", "New record created in a database");
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);

    }

    @GetMapping(value = "/admin")
    public ResponseEntity<?> getCarsWithQuery(@RequestParam(required = false, defaultValue = "undefined") String brand,
                                              @RequestParam(required = false, defaultValue = "undefined") String model,
                                              @RequestParam(required = false, defaultValue = "undefined") String colour,
                                              @RequestParam(required = false, defaultValue = "undefined") String mileage,
                                              @RequestParam(required = false, defaultValue = "undefined") String price,
                                              @RequestParam(required = false, defaultValue = "undefined") String year){
        return new ResponseEntity<>(carsService.getCarsWithQuery(brand, model, colour, mileage, price, year), HttpStatus.OK);
    }
}
