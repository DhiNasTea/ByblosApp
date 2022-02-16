package com.example.byblosmobileapp.servicefolder;

import java.io.Serializable;

public class Address implements Serializable {

    private String description;

    private String city, street, streetNum;


    public Address() {}

    public Address(String description, String country, String city,
                   String street, String streetNum) {
        this.description = description;
        this.city = city;
        this.street = street;
        this.streetNum = streetNum;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
