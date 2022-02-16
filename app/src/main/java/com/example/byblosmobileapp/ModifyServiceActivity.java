package com.example.byblosmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.byblosmobileapp.servicefolder.Address;
import com.example.byblosmobileapp.servicefolder.Date;
import com.example.byblosmobileapp.servicefolder.NumericalQuantity;
import com.example.byblosmobileapp.servicefolder.Option;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ModifyServiceActivity extends AppCompatActivity {


    private LinearLayout linearLayout;

    private FloatingActionButton floatingActionButton;

    private ArrayList<String> typesOfData = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private Service service=null;

    private Button submit, removeSerivce;

    private EditText titleEditText;
    private TextView titleText;

    private NumericalQuantity numericalQuantity;
    private Address address;
    private Date date;
    private Option option;

    private EditText hourlyRate;
    private CheckBox visible;

    private boolean creatingNewService = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_service);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        linearLayout = findViewById(R.id.service_info);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        Bundle extras = getIntent().getExtras();



        titleEditText = findViewById(R.id.modify_service_title);
        titleText = findViewById(R.id.title_of_services);

        submit = findViewById(R.id.submit_new_info_button);
        removeSerivce = findViewById(R.id.remove_service);

        hourlyRate = findViewById(R.id.editHourlyRate);
        visible = findViewById(R.id.setVisible);

        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference().child("Service");

        if (extras != null) {
            service = (Service) getIntent().getSerializableExtra("service");
            System.out.println(service.getTitle() + " NOT NULL");
            titleText.setText(service.getTitle());
        } else {
            titleText.setText("New Service");
        }


        typesOfData.add("Date");
        typesOfData.add("Address");
        typesOfData.add("Numerical Quantity");
        typesOfData.add("Options");

        if (service != null) {
            db = db.child(service.getTitle());
            ArrayList<Address> addresses = service.getAddresses();
            ArrayList<Date> dates = service.getDates();
            ArrayList<NumericalQuantity>  numericalQuantities = service.getNumericalQuantities();
            ArrayList<Option> options = service.getOptionsList();

            for (int i = 1; i < dates.size(); i++) {
                date = dates.get(i);
                View modifyView = getLayoutInflater().inflate(R.layout.add_service_row, null);
                EditText data = modifyView.findViewById(R.id.add_service_data);
                ImageView removeView = modifyView.findViewById(R.id.imageView3);
                ImageView submitToDB = modifyView.findViewById(R.id.submit_new_info);

                data.setText(date.getDescription());

                AppCompatSpinner spinnerDataType = modifyView.findViewById(R.id.data_type);
                ArrayAdapter arrayAdapter = new ArrayAdapter(ModifyServiceActivity.this, android.R.layout.simple_spinner_item, new String[]{"Date"});
                spinnerDataType.setAdapter(arrayAdapter);

                removeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.removeDate(date);
                        linearLayout.removeView(modifyView);
                    }
                });

                submitToDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dates.remove(date);
                        date = new Date(data.getText().toString(), null, null, null);
                        dates.add(date);
                        db.setValue(service);
                    }
                });
                linearLayout.addView(modifyView);
            }

            for (int i = 1; i < addresses.size(); i++) {
                address = addresses.get(i);
                View modifyView = getLayoutInflater().inflate(R.layout.add_service_row, null);

                EditText data = modifyView.findViewById(R.id.add_service_data);
                ImageView removeView = modifyView.findViewById(R.id.imageView3);
                ImageView submitToDB = modifyView.findViewById(R.id.submit_new_info);

                data.setText(address.getDescription());

                AppCompatSpinner spinnerDataType = modifyView.findViewById(R.id.data_type);
                ArrayAdapter arrayAdapter = new ArrayAdapter(ModifyServiceActivity.this, android.R.layout.simple_spinner_item, new String[]{"Address"});
                spinnerDataType.setAdapter(arrayAdapter);

                removeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.removeAddress(address);
                        linearLayout.removeView(modifyView);
                    }
                });
                submitToDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addresses.remove(address);
                        address = new Address(data.getText().toString(), null, null, null, null);
                        addresses.add(address);
                        db.setValue(service);
                    }
                });
                linearLayout.addView(modifyView);
            }
            for (int i = 1; i < numericalQuantities.size(); i++) {
                numericalQuantity = numericalQuantities.get(i);
                View modifyView = getLayoutInflater().inflate(R.layout.add_service_row, null);

                EditText data = modifyView.findViewById(R.id.add_service_data);
                ImageView removeView = modifyView.findViewById(R.id.imageView3);
                ImageView submitToDB = modifyView.findViewById(R.id.submit_new_info);

                data.setText(numericalQuantity.getDescription());

                AppCompatSpinner spinnerDataType = modifyView.findViewById(R.id.data_type);
                ArrayAdapter arrayAdapter = new ArrayAdapter(ModifyServiceActivity.this, android.R.layout.simple_spinner_item, new String[]{"Numerical Quantity"});
                spinnerDataType.setAdapter(arrayAdapter);

                removeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.removeNumericalQuantity(numericalQuantity);
                        linearLayout.removeView(modifyView);
                        System.out.println(service.getNumericalQuantities().size());
                        db.setValue(service);
                    }
                });
                titleText = findViewById(R.id.modify_service_title);

                submitToDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //String titleName = titleText.getText().toString();
                        //if (titleName == null || titleName.isEmpty()) {
                         //   Toast.makeText(ModifyServiceActivity.this, "Enter a title", Toast.LENGTH_SHORT).show();;
                        //} else {
                        numericalQuantities.remove(numericalQuantity);
                        numericalQuantity = new NumericalQuantity(data.getText().toString(), -1);
                        numericalQuantities.add(numericalQuantity);
                        db.setValue(service);
                        //}
                    }
                });
                linearLayout.addView(modifyView);
            }

            for (int i = 1; i < options.size(); i++) {
                option = options.get(i);
                View modifyView = getLayoutInflater().inflate(R.layout.add_service_row, null);

                EditText data = modifyView.findViewById(R.id.add_service_data);
                ImageView removeView = modifyView.findViewById(R.id.imageView3);
                ImageView submitToDB = modifyView.findViewById(R.id.submit_new_info);

                data.setText(option.getDescription());

                AppCompatSpinner spinnerDataType = modifyView.findViewById(R.id.data_type);
                ArrayAdapter arrayAdapter = new ArrayAdapter(ModifyServiceActivity.this, android.R.layout.simple_spinner_item, new String[]{"Options"});
                spinnerDataType.setAdapter(arrayAdapter);

                removeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.removeOption(option);
                        linearLayout.removeView(modifyView);
                    }
                });
                submitToDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        options.remove(address);
                        option = new Option(data.getText().toString(), null);
                        addresses.add(address);
                        db.setValue(service);
                    }
                });
                linearLayout.addView(modifyView);
            }
        } else {
            creatingNewService = true;
            service = new Service();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView(view);
                /*View modifyView = getLayoutInflater().inflate(R.layout.add_service_row, null);

                EditText data = modifyView.findViewById(R.id.add_service_data);
                ImageView removeView = modifyView.findViewById(R.id.imageView3);

                AppCompatSpinner spinnerDataType = modifyView.findViewById(R.id.data_type);
                ArrayAdapter arrayAdapter = new ArrayAdapter(ModifyServiceActivity.this, android.R.layout.simple_spinner_item, typesOfData);
                spinnerDataType.setAdapter(arrayAdapter);

                removeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        linearLayout.removeView(modifyView);
                    }
                });

                linearLayout.addView(modifyView);*/
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseDatabase.getInstance().getReference().child("Service");
                String titleName = titleEditText.getText().toString();
                if ((titleName == null || titleName.isEmpty()) && creatingNewService) {
                    Toast.makeText(ModifyServiceActivity.this, "Enter a title", Toast.LENGTH_SHORT).show();;
                } else if ( !(titleName == null || titleName.isEmpty())){
                    service.setTitle(titleName);
                //    db.child(titleName).setValue(service);
                }

                String hourlyRateString = hourlyRate.getText().toString();
                if ((hourlyRateString == null || hourlyRateString.isEmpty()) && creatingNewService) {
                    Toast.makeText(ModifyServiceActivity.this, "Enter an hourly rate", Toast.LENGTH_SHORT).show();;
                } else if ( !(hourlyRateString == null || hourlyRateString.isEmpty())){
                    service.setHourlyRate(Float.valueOf(hourlyRateString));
                //    db.child(titleName).setValue(service);
                }

                if (visible.isChecked()) {
                    service.setAvailable(false);
                }
                db.child(service.getTitle()).setValue(service);
                startActivity(new Intent(ModifyServiceActivity.this, AdminPage.class));
            }
        });
        removeSerivce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 db = FirebaseDatabase.getInstance().getReference().child("Service");
                 db.child(service.getTitle()).removeValue();
                 startActivity(new Intent(ModifyServiceActivity.this, AdminPage.class));
            }
        });


    }

    public void addView(View v) {
        View modifyView = getLayoutInflater().inflate(R.layout.add_service_row, null);

        EditText data = modifyView.findViewById(R.id.add_service_data);
        ImageView removeView = modifyView.findViewById(R.id.imageView3);
        ImageView submitNewInfo = modifyView.findViewById(R.id.submit_new_info);

        AppCompatSpinner spinnerDataType = modifyView.findViewById(R.id.data_type);
        ArrayAdapter arrayAdapter = new ArrayAdapter(ModifyServiceActivity.this, android.R.layout.simple_spinner_item, typesOfData);
        spinnerDataType.setAdapter(arrayAdapter);

        service = new Service();

        removeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.removeView(modifyView);
            }
        });
        submitNewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  db = firebaseDatabase.getReference().child(service.getTitle()).child()
                String descript = data.getText().toString().trim();
                switch (spinnerDataType.getSelectedItem().toString()) {
                    case "Address":
                        service.addAddress(new Address(descript, null, null, null, null));
                        break;

                    case "Date":
                        service.addDate(new Date(descript, null, null, null));
                        break;

                    case "Numerical Quantity":
                        service.addNumericalQuantity(new NumericalQuantity(descript, -1));
                        break;
                }
            }
        });

        linearLayout.addView(modifyView);


    }
}