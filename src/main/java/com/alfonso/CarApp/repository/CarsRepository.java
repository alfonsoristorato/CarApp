package com.alfonso.CarApp.repository;

import com.alfonso.CarApp.models.Car;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends MongoRepository<Car, String> {
    List<Car> findByModel(String model);

    List<Car> findByBrandAndModel(String brand, String model);


}
