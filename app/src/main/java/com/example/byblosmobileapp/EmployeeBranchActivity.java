package com.example.byblosmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class EmployeeBranchActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;

    private ArrayList<Service> listOfServices;

    private String email = null;

    private EditText startHour, endHour;
    private Button addEmplInfoToDB;
    private LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_branch);

        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference().child("Service");

        Bundle extras = getIntent().getExtras();
        listOfServices = new ArrayList<>();

        linearLayout = findViewById(R.id.list_of_services_for_branch);
        //ArrayList<Service> myServices = new ArrayList<>();

        addEmplInfoToDB = findViewById(R.id.addService);
        startHour = findViewById(R.id.startTime);
        endHour = findViewById(R.id.endTime);

        if (extras != null) {
             email = extras.getString("email");
             System.out.println(email + " NOT NULL");
        }
        System.out.println(email);




        email = email.replace('.','|');
        // checking if requests are pending
        DatabaseReference requests = firebaseDatabase.getReference().child("Users").child(email);

        if (requests.child("serviceRequests").getKey() != null) {
            Button buttonRequests = findViewById(R.id.button_to_customer_request);
            buttonRequests.setVisibility(View.VISIBLE);
            buttonRequests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EmployeeBranchActivity.this, ServiceApproval.class);
                    intent.putExtra("employeeEmail", email);
                    startActivity(intent);
                }
            });
        }

        DatabaseReference employeeDB = firebaseDatabase.getReference().child("Users").child(email);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Service service = dataSnapshot.getValue(Service.class);
                    if (service.isAvailable())
                        listOfServices.add(service);
                }

                System.out.println(listOfServices.size());
                employeeDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Employee employee = snapshot.getValue(Employee.class);
                        ArrayList<String> currentServices = employee.getServices();

                        for (Service service: listOfServices) {
                            View newView = getLayoutInflater().inflate(R.layout.add_service_to_employee_selection, null);

                            CheckBox employWillService = newView.findViewById(R.id.checkbox_to_add_to_branch);
                            TextView textView = newView.findViewById(R.id.textview_for_service_name_employee);
                            textView.setText(service.getTitle());

                            textView.setText(service.getTitle());

                            for (String equalService : employee.getServices()) {
                               if (equalService.equals(service.getTitle())) {
                                   employWillService.setChecked(true);
                                   break;
                               }
                            }
                            employWillService.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (employWillService.isChecked()) {
                                        if (!currentServices.contains(service.getTitle())) {
                                            currentServices.add(service.getTitle());
                                        }
                                    } else {
                                        if (currentServices.contains(service.getTitle())) {
                                            currentServices.remove(service.getTitle());
                                        }
                                    }

                                    employee.setServices(currentServices);
                                }
                            });

                            linearLayout.addView(newView);
                        }

                        Button addEmplInfoToDB = findViewById(R.id.addService);
                        addEmplInfoToDB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String startTime = startHour.getText().toString().trim();
                                String endTime = endHour.getText().toString().trim();

                                Switch switchAvailable = (Switch) findViewById(R.id.switch1);
                                employee.setAvailable(switchAvailable.isChecked());

                                if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
                                    Toast.makeText(EmployeeBranchActivity.this, "Please fill out the hours", Toast.LENGTH_SHORT).show();
                                } else {
                                    String[] startT = startTime.split(":");
                                    String[] endT = endTime.split(":");

                                    //System.out.println(Integer.valueOf(endT[0])* 60 + Integer.valueOf(endT[1]));

                                    if (Integer.valueOf(startT[0]) * 60 + Integer.valueOf(startT[1]) <
                                            Integer.valueOf(endT[0])* 60 + Integer.valueOf(endT[1])) {
                                        employee.setStartHour(Integer.parseInt(startT[0]));
                                        employee.setStartMin(Integer.parseInt(startT[1]));


                                        employee.setEndHour(Integer.parseInt(endT[0]));
                                        employee.setEndMin(Integer.parseInt(endT[1]));


                                        employeeDB.setValue(employee);

                                    } else {
                                        Toast.makeText(EmployeeBranchActivity.this, "The closing hours must be later than opening hours", Toast.LENGTH_SHORT).show();
                                    }
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
}
