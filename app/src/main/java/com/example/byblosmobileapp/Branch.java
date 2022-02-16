package com.example.byblosmobileapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Branch {


    private ArrayList<Service> listOfServices;

    private int[] startTime; // hour and minute
    private int[] endTime;

    public Branch() {
        listOfServices = new ArrayList<>();
    }

    public void addService(Service service) {
        listOfServices.add(service);
    }
    public void setServices(ArrayList service) {
        listOfServices = service;
    }

    public void removeService(Service service) {
        listOfServices.remove(service);
    }

    public boolean setWorkingHours(int startH, int startM, int endH, int endM) {
        // startH is the start time hour, startM is the start time minutes, same for endH and endM
        if (!verifyWorkingHours(startH, startM, endH, endM)) {
            return false;
        }

        startTime[0] = startH;
        startTime[1] = startM;
        endTime[0] = endH;
        endTime[1] = endM;
        return true;
    }

    private boolean verifyWorkingHours (int startH, int startM, int endH, int endM) {
        if (startH > 23 || startH < 0 || startM > 59 || startM < 0 ||
                endH > 23 || endH < 0 || endM > 59 || endM < 0)
            return false;
        // now we know the minutes are correct and the hours are correct too

        if (startH > endH)
            return false;
        else if (startH == endH) {
            return startM < endM;
        } else {
            return true;
        }
    }
}


