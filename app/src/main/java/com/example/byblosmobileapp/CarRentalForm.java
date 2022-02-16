package com.example.byblosmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CarRentalForm extends AppCompatActivity {

    private Spinner spinner;
    private Spinner typeSpinner;
    private static final String[] licenceOptions = {"License Type", "G1", "G2", "G2"};

    private static final String[] typeOptions = {"Car Type","Compact", "Intermediate", "SUV"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental_form);

        spinner = (Spinner) findViewById(R.id.licenseType);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(CarRentalForm.this, android.R.layout.simple_spinner_item, licenceOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        typeSpinner = (Spinner) findViewById(R.id.carType);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(CarRentalForm.this, android.R.layout.simple_spinner_item, typeOptions);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);
    }
}