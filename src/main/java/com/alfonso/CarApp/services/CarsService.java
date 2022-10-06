package com.alfonso.CarApp.services;

import com.alfonso.CarApp.exception.GlobalExceptionHandler;
import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsService {
    @Autowired
    private CarsRepository carsRepository;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;


    public void saveCars(List<Car> carsList) {

        carsList.forEach(car -> {
            try {
                carsRepository.insert(car);
            } catch (MongoWriteException e) {

                globalExceptionHandler.duplicateKeyException(e);
            }
        });


    }
}
