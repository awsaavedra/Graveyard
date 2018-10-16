package com.company;

public class Airport {
    private String city;
    private String country;
    private String code3;

    public Airport(String city, String country, String code) {
        this.city = city;
        this.country = country;
        this.code3 = code3;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code3;
    }
}
