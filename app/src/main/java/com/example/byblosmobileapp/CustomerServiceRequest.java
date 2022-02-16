package com.example.byblosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.byblosmobileapp.servicefolder.Address;
import com.example.byblosmobileapp.servicefolder.Date;
import com.example.byblosmobileapp.servicefolder.NumericalQuantity;
import com.example.byblosmobileapp.servicefolder.Option;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerServiceRequest extends AppCompatActivity {

    private LinearLayout linearLayout;

    private String nameOfService;
    private String employeeInfo;

    private Service requestingService;
    private TextView textView;
    private Employee employee;

    private Address address;
    private Date date;
    private NumericalQuantity numericalQuantity;
    private Option option;
    private View modifyView;

    private TextView nameOfServiceView;

    int i =0;

    private EditText customerAddress, customerFirstName, customerLastName, customerDateOfBirth;

    ArrayList<Address> addresses;
    ArrayList<Date> dates;
    ArrayList<NumericalQuantity> numericalQuantities;
    ArrayList<Option> options;

    private boolean validated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_request);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        linearLayout = findViewById(R.id.service_request_rows);
        textView = findViewById(R.id.title_of_service_customer_request);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            nameOfService = extras.getString("serviceName").trim();
            nameOfService = extras.getString("serviceName");
            employeeInfo = extras.getString("employeeEmail");


            // setting the text at the top
            nameOfServiceView = findViewById(R.id.branch_name);
            nameOfServiceView.setText(nameOfService);
        }


        System.out.println(nameOfService);
        textView.setText("Form for " + nameOfService);

        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference db = fb.getReference().child("Service");

        db.child(nameOfService).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestingService = snapshot.getValue(Service.class);

                System.out.println(requestingService.getTitle());
                // = getLayoutInflater().inflate(R.layout., null);

                addresses = requestingService.getAddresses();
                dates = requestingService.getDates();
                numericalQuantities = requestingService.getNumericalQuantities();
                options = requestingService.getOptionsList();


                for ( i = 1; i < addresses.size(); i++) {
                    modifyView = getLayoutInflater().inflate(R.layout.add_address_data, null);

                    address = addresses.get(i);

                    TextView descriptionOfAdd = modifyView.findViewById(R.id.descript_of_address);
                    descriptionOfAdd.setText(address.getDescription());

                    EditText addressStreetNum = modifyView.findViewById(R.id.address_street_num);
                    EditText addressStreet = modifyView.findViewById(R.id.address_street);
                    EditText addressCity = modifyView.findViewById(R.id.address_city);

                    Button addToCustomerRequest = modifyView.findViewById(R.id.add_address);

                    addToCustomerRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (addressCity.getText().toString().trim().isEmpty() ||
                                    addressStreet.getText().toString().trim().isEmpty() ||
                                    addressStreetNum.getText().toString().trim().isEmpty()
                            ) {
                                Toast.makeText(CustomerServiceRequest.this, "Empty fields, must be populationed", Toast.LENGTH_SHORT).show();
                                validated = false;
                            } else {
                                address.setCity(addressCity.getText().toString());
                                address.setStreet(addressStreet.getText().toString());
                                address.setStreetNum(addressStreetNum.getText().toString());
                                addresses.set(i, address);
                                validated = true;
                            }

                        }
                    });
                    linearLayout.addView(modifyView);
                }
                for ( i = 1; i < dates.size(); i++) {
                    modifyView = getLayoutInflater().inflate(R.layout.add_date_data, null);

                    date = dates.get(i);

                    TextView descriptionOfAdd = modifyView.findViewById(R.id.descript_of_date);
                    descriptionOfAdd.setText(date.getDescription());

                    EditText dateText = modifyView.findViewById(R.id.date_information);

                    Button addToCustomerRequest = modifyView.findViewById(R.id.add_date);

                    addToCustomerRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dateText.getText().toString().trim().isEmpty()) {
                                Toast.makeText(CustomerServiceRequest.this, "Empty Field, must be populationed", Toast.LENGTH_SHORT).show();
                                validated = false;
                            } else {
                                String[] monthDayYear = dateText.getText().toString().split("/");
                                date.setMonth(monthDayYear[0]);
                                date.setDay(monthDayYear[1]);
                                date.setYear(monthDayYear[2]);
                                dates.set(i, date);
                                validated = true;
                            }
                        }
                    });

                    linearLayout.addView(modifyView);
                }

                for ( i = 1; i < numericalQuantities.size(); i++) {
                    modifyView = getLayoutInflater().inflate(R.layout.add_numerical_data, null);

                    numericalQuantity = numericalQuantities.get(i);

                    TextView descriptionOfAdd = modifyView.findViewById(R.id.descript_of_num);
                    descriptionOfAdd.setText(numericalQuantity.getDescription());

                    EditText numerical = modifyView.findViewById(R.id.enter_numerical_info);

                    Button addToCustomerRequest = modifyView.findViewById(R.id.add_num);

                    addToCustomerRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (numerical.getText().toString().isEmpty()) {
                                Toast.makeText(CustomerServiceRequest.this, "Empty Field, must be populationed", Toast.LENGTH_SHORT).show();
                                validated = false;
                            } else {
                                numericalQuantity.setQuantity(Integer.parseInt(numerical.getText().toString()));
                                numericalQuantities.set(i, numericalQuantity);
                                validated = true;
                            }
                        }
                    });
                    linearLayout.addView(modifyView);
                }

                for ( i = 1; i < options.size(); i++) {
                    modifyView = getLayoutInflater().inflate(R.layout.add_date_data, null);

                    option = options.get(i);

                    TextView descriptionOfAdd = modifyView.findViewById(R.id.descript_of_date);
                    descriptionOfAdd.setText(option.getDescription() + ": ");

                    EditText optionsField = modifyView.findViewById(R.id.date_information);
                    optionsField.setHint("Enter one of the options above ");

                    ArrayList<String> optionsMenu = option.getOptions();

                    for (int j = 0; j < option.getOptions().size(); j++) {
                        descriptionOfAdd.setText(descriptionOfAdd.getText() + optionsMenu.get(j) + " ");
                    }

                    Button addToCustomerRequest = modifyView.findViewById(R.id.add_date);

                    addToCustomerRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (optionsField.getText().toString().isEmpty() ||
                                    !optionsMenu.contains(optionsField.getText().toString())) {
                                Toast.makeText(CustomerServiceRequest.this, "One of the option field was not included in the options", Toast.LENGTH_SHORT).show();
                                validated = false;
                            } else {
                                option.selectedOption(optionsField.getText().toString());
                                options.set(i, option);
                                validated = true;
                            }
                        }
                    });
                    linearLayout.addView(modifyView);
                }

                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(employeeInfo);
                //DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("Users").child();
                db2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        employee = snapshot.getValue(Employee.class);

                        Button button = findViewById(R.id.submit_customer_request);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (validated) {
                                    requestingService.setAddresses(addresses);

                                    requestingService.setDates(dates);
                                    requestingService.setNumericalQuantities(numericalQuantities);
                                    requestingService.setOptionsList(options);
                                    employee.addServiceRequest(requestingService);
                                    db2.setValue(employee);
                                } else {
                                    Toast.makeText(CustomerServiceRequest.this, "Must validate all fields!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addAddress() {

    }
}