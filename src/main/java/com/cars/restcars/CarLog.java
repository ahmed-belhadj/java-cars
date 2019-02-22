package com.cars.restcars;

import lombok.Data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CarLog implements Serializable
{
    private final String mgs;
    private final String date;

    public CarLog(String mgs)
    {
        this.mgs = mgs;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        date = dateFormat.format(new Date());
    }
}