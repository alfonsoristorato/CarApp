package com.alfonso.CarApp.services;

import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CarsService {
    @Autowired
    private  CarsRepository carsRepository;




    public void saveCars(Car[] carsArray) {
        Arrays.stream(carsArray).forEach(car -> carsRepository.insert(car));


    }

}
