package com.company.bankaccountapp;

// A new API available to us
public interface BaseRate {

    // Write a method that returns the base rate
    default double getBaseRate(){ return 2.5; }

}
