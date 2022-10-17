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
import org.springframework.data.mongodb.core.aggregation.SortByCountOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
        carsService.saveCars(carsList);
        carsService.updateCar( "1", "1", "1", "1", "1", "1112");
        carsService.verifyFieldsFormat("1", "1", "1", "1", "1", "1112");
        Query select = new Query();
        select.addCriteria(new Criteria().andOperator(Criteria.where("brand").is("1"), Criteria.where("model").is("1")));

        Update update = new Update();
        update.set("colour", "1");
        update.set("mileage", "1");
        update.set("price", "1");
        update.set("year", "1112");

        verify(mongoTemplate, times(1)).findAndModify(select, update, Car.class);
    }

    @Test
    void whenDeleteCarCalled_executeDelete() {
        carsService.deleteCar("1");
        verify(carsRepository, times(1)).deleteById("1");
    }
}
