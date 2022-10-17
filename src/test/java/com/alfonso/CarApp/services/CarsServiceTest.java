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
        carsService.getCarsWithQuery("", "", "", "", "", "");
        verify(carsService, times(1)).getCarsWithQuery("", "", "", "", "", "");
    }

    @Test
    void whenGetCarsWithQueryWithIncorrectParameterAlphabetical_executeGet() {
        carsService.verifyFieldsFormat("Mazda ", "", "", "", "", "");
        verify(carsService, times(1)).verifyFieldsFormat("Mazda ", "", "", "", "", "");
    }

    @Test
    void whenGetCarsWithQueryWithIncorrectParameterNumeric_executeGet() {
        carsService.verifyFieldsFormat("", "", "", "", "", "aaa");
        verify(carsService, times(1)).verifyFieldsFormat("", "", "", "", "", "aaa");
    }

    @Test
    void whenUpdateCarCalled_executePut() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1111,1,1,"1");
        carsList.add(testCar1);
        carsService.saveCars(carsList);
        carsService.updateCar( "2", "1", "1", "1", "1", "1111");
        carsService.verifyFieldsFormat("2", "1", "1", "1", "1", "1111");
        verify(carsService, times(1)).verifyFieldsFormat("2", "1", "1", "1", "1", "1111");
    }
}
