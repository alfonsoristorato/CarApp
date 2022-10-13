package com.alfonso.CarApp.services;

import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTest {

    @Mock
    private CarsService carsService;

    @Test
    void whenSaveCarsCalled_executeInsert() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsList.add(testCar1);
        carsService.saveCars(carsList);
        verify(carsService, times(1)).saveCars(carsList);

    }

    @Test
    void whenGetCarsWithQuery_executeGet() {
        carsService.getCarsWithQuery("undefined", "undefined", "undefined", "undefined", "undefined", "undefined");
        verify(carsService, times(1)).getCarsWithQuery("undefined", "undefined", "undefined", "undefined", "undefined", "undefined");
    }

    @Test
    void whenGetCarsWithQueryWithIncorrectParameterAlphabetical_executeGet() {
        carsService.verifyFieldsFormat("Mazda ", "undefined", "undefined", "undefined", "undefined", "undefined");
        verify(carsService, times(1)).verifyFieldsFormat("Mazda ", "undefined", "undefined", "undefined", "undefined", "undefined");
    }

    @Test
    void whenGetCarsWithQueryWithIncorrectParameterNumeric_executeGet() {
        carsService.verifyFieldsFormat("undefined", "undefined", "undefined", "undefined", "undefined", "aaa");
        verify(carsService, times(1)).verifyFieldsFormat("undefined", "undefined", "undefined", "undefined", "undefined", "aaa");
    }


}
