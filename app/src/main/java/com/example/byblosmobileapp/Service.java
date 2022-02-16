package com.example.byblosmobileapp;

import com.example.byblosmobileapp.servicefolder.Address;
import com.example.byblosmobileapp.servicefolder.Date;
import com.example.byblosmobileapp.servicefolder.NumericalQuantity;
import com.example.byblosmobileapp.servicefolder.Option;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Service implements Serializable{
    private float hourlyRate;
    private boolean isAvailable = true;
    private String title;
    private String fName, lName;

    // All Services need date of birth, and the address of the customer
    // So, the first index of the following lists must match the info above
    // ex: dates.get(0) == dates of birth
    private ArrayList<Date> dates = new ArrayList<>();
    private ArrayList<Address> addresses = new ArrayList<>();
    private ArrayList<NumericalQuantity> numericalQuantities = new ArrayList<>();
    private ArrayList<Option> optionsList = new ArrayList<>();



    public Service() {
        isAvailable = true;
        // None of the arraylists can be empty otherwise, app will crash later on
        dates.add(new Date("Birth Date", null, null, null));
        addresses.add(new Address("Address", null, null, null, null));
        numericalQuantities.add(new NumericalQuantity("Dummy", -1));
        optionsList.add(new Option("Dummy", "dummy"));
    }

    public  Service(String title ) {
        isAvailable = true;
        this.title = title;
    }


    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setCustomerInfo(Customer customerInfo) {
        if (customerInfo == null) {
            fName = null;
            lName = null;
        } else {
            fName = customerInfo.getFirstName();
            lName = customerInfo.getLastName();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddressOfCustomer(Address addressOfCustomer) {
        if (!addresses.isEmpty()) {
            System.out.println("The address of customer was already added");
            return;
        }
        this.addresses.add(addressOfCustomer);
    }


    public void addNumericalQuantity(NumericalQuantity numericalQuantity) {
        numericalQuantities.add(numericalQuantity);
    }
    public void addAddress(Address add) {
        addresses.add(add);
    }

    public void removeAddress(Address add) {
        addresses.remove(add);
    }

    public void removeNumericalQuantity(NumericalQuantity numericalQuantity) {
       // for (int i = 0; i < numericalQuantities.size(); i++) {
         //   if (numericalQuantities.get(i).equals(numericalQuantity))
                numericalQuantities.remove(numericalQuantity);;
        //}

    }

    public void addOptions(Option options) {
        optionsList.add(options);
    }
    public void removeOption(Option option) {
        optionsList.remove(option);
    }

    public ArrayList<Option> getOptionsList() {
        return optionsList;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if (!dates.isEmpty()) {
            System.out.println("The birth date of customer was already added");
            return;
        }
        this.dates.add(dateOfBirth);
    }

    public void addDate(Date date) {
        this.dates.add(date);
    }

    public void removeDate(Date date) {
        this.dates.remove(date);
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public ArrayList<NumericalQuantity> getNumericalQuantities() {
        return numericalQuantities;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean equals(Object o) {
        if (o== null) {
            return false;
        } else if (o.getClass() == getClass()) {
            Service oServ = (Service) o;
            if (oServ != null || oServ.getTitle().equals(getTitle())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }


    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public void setNumericalQuantities(ArrayList<NumericalQuantity> numericalQuantities) {
        this.numericalQuantities = numericalQuantities;
    }

    public void setOptionsList(ArrayList<Option> optionsList) {
        this.optionsList = optionsList;
    }

}
