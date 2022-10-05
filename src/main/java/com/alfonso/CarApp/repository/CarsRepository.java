package com.alfonso.CarApp.repository;

import com.alfonso.CarApp.models.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends MongoRepository<Car, String> {


}
