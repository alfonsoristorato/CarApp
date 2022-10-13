package com.alfonso.CarApp.controllers;

import com.alfonso.CarApp.exception.GlobalExceptionHandler;
import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.services.CarsService;
import com.mongodb.MongoWriteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarsControllerTest {
    @Mock
    private CarsService carsService;
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    public CarsService getCarsService() {
        return carsService;
    }

    @InjectMocks
    private CarsController carsController;

    ResponseEntity<?> response;
    @Test
    void whenInsertCalled_return201_saveCarsCalled() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsList.add(testCar1);
        response = carsController.insert(carsList);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(carsService, times(1)).saveCars(carsList);
    }
    @Test
    void whenInsertCalledWithWrongData_return400_genericException() {
        try{
            List<Car> carsList = new ArrayList<>();
            Car testCar1 = new Car(null,null,1,1,1,"1");
            carsList.add(testCar1);
            response = carsController.insert(carsList);
            verify(carsService, times(1)).saveCars(carsList);
        }
        catch(ConstraintViolationException ex){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            verify(globalExceptionHandler, times(1)).genericException(ex);
        }

    }

    @Test
    void whenGetCarsByQueryCalled_return200() {
        response = carsController.getCarsWithQuery("", "", "", "", "", "");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenInsertCalledWithDuplicateCars_return409_duplicateKeyExceptionCalled() {
        try{
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1,1,1,"1");

        carsList.add(testCar1);

        response = carsController.insert(carsList);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        response = carsController.insert(carsList);
        verify(carsService, times(2)).saveCars(carsList);
        }
        catch(MongoWriteException ex) {
            Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            verify(globalExceptionHandler, times(1)).duplicateKeyException(ex);
        }
    }

    @Test
    void whenUpdateCarCalled_return200() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1111,1,1,"1");
        testCar1.setId("testid");
        carsList.add(testCar1);
        testCar1.setBrand("2");
        response = carsController.update("testid",testCar1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(carsService, times(1)).updateCar("testid",testCar1.getBrand(),
                testCar1.getModel(),
                testCar1.getColour(),
                String.valueOf(testCar1.getMileage()),
                String.valueOf(testCar1.getPrice()),
                String.valueOf(testCar1.getYear()));
    }

}