package com.alfonso.CarApp.services;

import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTest {

    @InjectMocks
    private CarsService carsService;

    @Mock
    private CarsRepository carsRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    void whenSaveCarsCalled_executeInsert() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsList.add(testCar1);
        carsService.saveCars(carsList);
        verify(carsRepository, times(1)).insert(testCar1);

    }

    @Test
    void whenGetCarsWithQuery_executeGet() {
        carsService.getCarsWithQuery("Hello", "", "", "", "", "");
        Query testQuery = new Query();
        Criteria testBrand = Criteria.where("brand").is("Hello");
        testQuery.addCriteria(testBrand);
        verify(mongoTemplate, times(1)).find(testQuery.with(Sort.by(Sort.Direction.ASC,"brand")), Car.class, "car");
    }

    @Test
    void whenGetCarsWithQueryWithIncorrectParameterAlphabetical_executeGet() {
        assertThatThrownBy(() -> carsService.getCarsWithQuery(" ", "", "", "", "", "")).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void whenGetCarsWithQueryWithIncorrectParameterNumeric_executeGet() {
        assertThatThrownBy(() -> carsService.getCarsWithQuery("", "", "", "", "", "400")).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void whenUpdateCarCalled_executePut() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1111,1,1,"1");
        carsList.add(testCar1);
        when(carsRepository.findByBrandAndModel("1", "1")).thenReturn(carsList);
        carsService.updateCar( carsList);
        Query select = new Query();
        select.addCriteria(new Criteria().andOperator(Criteria.where("brand").is("1"), Criteria.where("model").is("1")));

        Update update = new Update();
        update.set("colour", "1");
        update.set("mileage", "1");
        update.set("price", "1");
        update.set("year", "1111");

        verify(mongoTemplate, times(1)).findAndModify(select, update, Car.class);
    }

    @Test
    void whenUpdateCalledAndNoCarFound_throwException() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1111,1,1,"1");
        carsList.add(testCar1);
        assertThatThrownBy(() -> carsService.updateCar(carsList)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenDeleteCarCalled_executeDelete() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsList.add(testCar1);

        when(carsRepository.findById("1")).thenReturn(Optional.ofNullable(carsList.get(0)));
        carsService.deleteCar("1");
        verify(carsRepository, times(1)).deleteById("1");
    }

    @Test
    void whenDeleteCarCalledWithWrongId_throwIllegArgException() {
        assertThatThrownBy(() -> carsService.deleteCar("1111")).isInstanceOf(IllegalArgumentException.class);
    }
}
