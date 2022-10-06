package com.alfonso.CarApp.controllers;
import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.services.CarsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarsControllerTest {
    @Mock
    private CarsService carsService;
    @InjectMocks
    private CarsController carsController;
    @Test
    void whenInsertCalled_return201_saveCarsCalled() {
        Car[] carsArray = new Car[1];
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsArray[0] = testCar1;
        ResponseEntity<?> response = carsController.insert(carsArray);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(carsService, times(1)).saveCars(carsArray);
    }

}