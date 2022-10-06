package com.alfonso.CarApp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Document
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    private String id;

    @NotEmpty

    private String brand;

    @NotEmpty

    private String model;


    @Min(1000)
    @Max(9999)
    @Digits(fraction = 0, integer = 4)
    private int year;


    @NumberFormat
    private int price;
    @NumberFormat
    private int mileage;
    @NotEmpty
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