package com.alfonso.CarApp.services;

import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTest {

    @InjectMocks
    private CarsService carsService;

    @Mock
    private CarsRepository carsRepository;

    @Test
    void whenSaveCarsCalled_executeInsert() {
        Car[] carsArray = new Car[1];
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsArray[0] = testCar1;
        carsService.saveCars(carsArray);
        verify(carsRepository, times(1)).insert(testCar1);

    }
}
