package com.dajevv.sri.mq.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Vehicle implements Serializable {

    private String marka;


    public Vehicle(String marka) {
        this.marka = marka;
    }

    public float getEngineTemp() {
        return (float) (Math.random()*60 + 90);
    }

    public float getTirePressure() {
        return (float) (Math.random()*300 + 50);
    }

    public float getOilPressure() {
        return (float) (Math.random()*500 + 100);
    }

    public LocalDateTime getDatetime() {
        return LocalDateTime.now();
    }

    @Override
    public String toString() {
        return getDatetime() + ", " + getEngineTemp() + ", " + getTirePressure() + ", " + getOilPressure();
    }
}
