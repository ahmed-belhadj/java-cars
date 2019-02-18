package com.cars.restcars;

public class CarNotFoundException extends RuntimeException
{
    public CarNotFoundException(String car)
    {
        super("Could not find " + car);
    }
}
