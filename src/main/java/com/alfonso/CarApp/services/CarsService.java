package com.alfonso.CarApp.services;

import com.alfonso.CarApp.exception.GlobalExceptionHandler;
import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarsService {
    @Autowired
    private MongoTemplate mongoTemplate;
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

    public List<Car> getAllCars() {
        return carsRepository.findAll();
    }

    public List<Car> getCarsWithQuery(String brand,
                                      String model,
                                      String colour,
                                      int mileage,
                                      int price,
                                      int year) {
        Query dynamicQuery = new Query();
        if (!brand.equals("-1")) {
            Criteria nameCriteria = Criteria.where("brand").is(brand);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!model.equals("-1")) {
            Criteria nameCriteria = Criteria.where("model").is(model);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!colour.equals("-1")) {
            Criteria nameCriteria = Criteria.where("colour").is(colour);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (mileage != -1) {
            Criteria nameCriteria = Criteria.where("mileage").is(mileage);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (price != -1) {
            Criteria nameCriteria = Criteria.where("price").is(price);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (year != -1) {
            Criteria nameCriteria = Criteria.where("year").is(year);
            dynamicQuery.addCriteria(nameCriteria);
        }
        System.out.println(dynamicQuery);
//        return carsRepository.getCarsByQuery(brand, model, colour, mileage, price, year);
        List<Car> result = mongoTemplate.find(dynamicQuery, Car.class, "car");
        return result;
    }



}
