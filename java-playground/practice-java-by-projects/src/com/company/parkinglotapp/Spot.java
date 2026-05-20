package com.company.parkinglotapp;

/*

    Every spot should keep track of whether it is filled,
    BUT vehicles should NOT keep track of whether they are in a spot.
 */

public class Spot {
    int size;
    Vehicle onSpot;

    public Spot(int size, Vehicle onSpot) {
        this.size = size;
        this.onSpot = onSpot;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Vehicle getOnSpot() {
        return onSpot;
    }

    public void setOnSpot(Vehicle onSpot) {
        this.onSpot = onSpot;
    }
}
