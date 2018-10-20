package com.company;

public class Main {

    public static void main(String[] args) {

    }

    public static String findAirportCode(String toFind, Airport[] airports){
        for( Airport airport: airports){
            if( toFind.equals(airport.getCity()){
                return airport.getCode();
            }
        }
        return "Airport not found";
    }
}
