package com.alfonso.CarApp.services;

import com.alfonso.CarApp.exception.GlobalExceptionHandler;
import com.alfonso.CarApp.models.Car;
import com.alfonso.CarApp.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
                carsRepository.insert(car);
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
                year,
                false);
        Query dynamicQuery = new Query();
        if (!brand.equals("")) {
            Criteria nameCriteria = Criteria.where("brand").is(brand);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!model.equals("")) {
            Criteria nameCriteria = Criteria.where("model").is(model);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!colour.equals("")) {
            Criteria nameCriteria = Criteria.where("colour").is(colour);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!mileage.equals("")) {
            Criteria nameCriteria = Criteria.where("mileage").is(mileage);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!price.equals("")) {
            Criteria nameCriteria = Criteria.where("price").is(price);
            dynamicQuery.addCriteria(nameCriteria);
        }
        if (!year.equals("")) {
            Criteria nameCriteria = Criteria.where("year").is(year);
            dynamicQuery.addCriteria(nameCriteria);
        }
        List<Car> result = mongoTemplate.find(dynamicQuery.with(Sort.by(Sort.Direction.ASC,"brand")), Car.class, "car");
        return result;
    }

    public CarsRepository getCarsRepository() {
        return carsRepository;
    }

    public void verifyFieldsFormat(String brand,
                                   String model,
                                   String colour,
                                   String mileage,
                                   String price,
                                   String year,
                                   Boolean update){
        System.out.println(brand + " " + model + " " + colour );
        if ( (!(brand+model+colour).matches("^[a-zA-Z0-9_\\-.]*$") || (brand+model+colour).contains(" ")) ||
                (!year.matches("[0-9]{4}") && !year.equals("")) ||
                (!mileage.matches("[0-9]*") && !mileage.equals("")) ||
                (!price.matches("[0-9]*") && !price.equals(""))){
            if (!update) throw new IllegalArgumentException("Incorrect Format Values");
            else throw new IllegalArgumentException("Illegal car parameters");
        }
    }

    public void updateCar(List<Car> cars) {
        for (Car car: cars){
            String brand = car.getBrand();
            String model = car.getModel();

            Query select = new Query();
            select.addCriteria(new Criteria().andOperator(Criteria.where("brand").is(brand), Criteria.where("model").is(model)));
            if (carsRepository.findByBrandAndModel(brand,model).isEmpty()){
                throw new IllegalArgumentException("No car matches");
            }

            String colour =  car.getColour();
            String mileage = String.valueOf(car.getMileage());
            String price = String.valueOf(car.getPrice());
            String year = String.valueOf(car.getYear());
            verifyFieldsFormat(brand, model, colour, mileage, price, year, true);

            Update update = new Update();
            if (!colour.equals("")) update.set("colour", colour);
            if (!mileage.equals("")) update.set("mileage", mileage);
            if (!price.equals("")) update.set("price", price);
            if (!year.equals("")) update.set("year", year);
            mongoTemplate.findAndModify(select, update, Car.class);
        }

    }


    public void deleteCar(String id) {

        if (carsRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("No car matches");
        } else{
            carsRepository.deleteById(id);
        }

    }

    public void deleteByTestModel() {
        List<Car> testCars = carsRepository.findByModel("TestModel");
        carsRepository.deleteAll(testCars);
    }
}
