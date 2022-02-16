package com.example.byblosmobileapp.servicefolder;

import java.io.Serializable;

public class Date implements Serializable {

    private String description;

    private String month, day, year;


    public Date() {}

    public Date(String description, String month, String day,
                   String year) {
        this.description = description;
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
