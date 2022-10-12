package com.alfonso.CarApp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@Document
@CompoundIndex( def = "{'brand' : 1, 'model' : 1}", unique = true)
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    private String id;

    @NotEmpty
//    @Pattern(regexp="\"(.*?)\"",message = "Incorrect car data provided")
    private String brand;

    @NotEmpty
//    @Pattern(regexp="^[A-Za-z0-9]*$",message = "Incorrect car data provided")
    private String model;


    @Min(1000)
    @Max(9999)
    @Digits(fraction = 0, integer = 4)
    @NumberFormat
    private int year;


    @NumberFormat
//    @Pattern(regexp="^[0-9]*$",message = "Incorrect car data provided")
    private int price;
    @NumberFormat
//    @Pattern(regexp="^[0-9]*$",message = "Incorrect car data provided")
    private int mileage;
    @NotEmpty
//    @Pattern(regexp="^[A-Za-z]*$",message = "Incorrect car data provided")
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