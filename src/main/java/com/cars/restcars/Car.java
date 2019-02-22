package com.cars.restcars;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Car
{
    private @Id
    @GeneratedValue
    Long id;
    private Integer year;
    private String brand;
    private String model;

    public Car()
    {
    }

    public Car(Integer year, String brand, String model)
    {
        this.year = year;
        this.brand = brand;
        this.model = model;
    }
}
