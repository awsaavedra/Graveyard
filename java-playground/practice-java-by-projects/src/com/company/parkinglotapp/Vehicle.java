package com.company.parkinglotapp;

public class Vehicle {

    private int wheels;
    private int passengers;
    private int spots;

    public Vehicle(int wheels, int passengers, int spots) {
        this.wheels = wheels;
        this.passengers = passengers;
        this.spots = spots;
    }

    public int getWheels() {
        return wheels;
    }

    public void setWheels(int wheels) {
        this.wheels = wheels;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }
}
