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
    public ResponseEntity<?> getCarsWithQuery(@RequestParam(required = false, defaultValue = "") String brand,
                                              @RequestParam(required = false, defaultValue = "") String model,
                                              @RequestParam(required = false, defaultValue = "") String colour,
                                              @RequestParam(required = false, defaultValue = "") String mileage,
                                              @RequestParam(required = false, defaultValue = "") String price,
                                              @RequestParam(required = false, defaultValue = "") String year){
        return new ResponseEntity<>(carsService.getCarsWithQuery(brand, model, colour, mileage, price, year), HttpStatus.OK);
    }

    @PutMapping(value = "/admin")
    public ResponseEntity<?> update(@RequestBody List<Car> cars ) {
        carsService.updateCar(cars);
        Map<String, String> responseObject = new HashMap<>();
        responseObject.put("description", "Cars updated");
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/admin/{carId}", "admin"})
    public ResponseEntity<?> delete(@PathVariable(required = false) String carId) {
        if (carId == null){
            throw new IllegalArgumentException("No id provided");
        }
        carsService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "admin/delete-test-data")
    public ResponseEntity<?> deleteTestCars() {
        carsService.deleteByTestModel();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
