package com.alfonso.CarApp.services;

import com.alfonso.CarApp.exception.GlobalExceptionHandler;
import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
                                      String mileage,
                                      String price,
                                      String year) {
        verifyFieldsFormat(brand,
                model,
                colour,
                mileage,
                price,
                year);
        Query dynamicQuery = new Query();
        if (!brand.equals("undefined")) {
            Criteria nameCriteria = Criteria.where("brand").is(brand);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!model.equals("undefined")) {
            Criteria nameCriteria = Criteria.where("model").is(model);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!colour.equals("undefined")) {
            Criteria nameCriteria = Criteria.where("colour").is(colour);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!mileage.equals("undefined")) {
            Criteria nameCriteria = Criteria.where("mileage").is(mileage);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!price.equals("undefined")) {
            Criteria nameCriteria = Criteria.where("price").is(price);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!year.equals("undefined")) {
            Criteria nameCriteria = Criteria.where("year").is(year);
            dynamicQuery.addCriteria(nameCriteria);
        }
        List<Car> result = mongoTemplate.find(dynamicQuery.with(Sort.by(Sort.Direction.ASC,"brand")), Car.class, "car");
        return result;
    }

    public void verifyFieldsFormat( String brand,
                                    String model,
                                    String colour,
                                    String mileage,
                                    String price,
                                    String year){
        System.out.println(brand + " " + model + " " + colour );
        if ( !(brand+model+colour).matches("[a-zA-Z0-9.?]*") ||
                (!year.matches("[0-9]{4}") && !year.equals("undefined")) ||
                (!mileage.matches("[0-9]*") && !mileage.equals("undefined")) ||
                (!price.matches("[0-9]*") && !price.equals("undefined"))){
            throw new IllegalArgumentException();
        }

    }



}
