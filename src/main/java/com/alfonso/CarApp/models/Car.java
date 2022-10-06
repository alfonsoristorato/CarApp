package com.alfonso.CarApp.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    private String id;
    private String brand;
    private String model;
    private int year;
    private int price;
    private int mileage;
    private String colour;

    public Car(String brand, String model, int year, int price, int mileage, String colour) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.colour = colour;
    }
}