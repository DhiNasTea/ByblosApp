package com.example.byblosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServicesOfSelectedBranch extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;

    private ArrayList<String> listOfServices;

    private String email = null;

    private Button goToForm;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_of_selected_branch);


        firebaseDatabase = FirebaseDatabase.getInstance();
        //db = firebaseDatabase.getReference().child("Service");

        Bundle extras = getIntent().getExtras();
        listOfServices = new ArrayList<>();

        linearLayout = findViewById(R.id.list_of_services_for_current_branch);
        //ArrayList<Service> myServices = new ArrayList<>();

        goToForm = findViewById(R.id.goToForm);

        if (extras != null) {
            email = extras.getString("email");
            System.out.println(email + " NOT NULL");
        }
        System.out.println(email);

        email = email.replace('.', '|');

        DatabaseReference employeeDB = FirebaseDatabase.getInstance().getReference().child("Users").child(email);

        employeeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee employee = snapshot.getValue(Employee.class);

                listOfServices = employee.getServices();
                System.out.println(listOfServices.size());

                // start at 1, because all arraylists will have a dummy value at 0
                for (int i = 1; i < listOfServices.size(); i++) {

                    String nameOfServiceI = listOfServices.get(i);
                    View newView = getLayoutInflater().inflate(R.layout.services_from_db, null);

                    TextView title = newView.findViewById(R.id.name_of_service);
                    title.setText(listOfServices.get(i));

                    Button button = newView.findViewById(R.id.modify_service);
                    button.setText("Request");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ServicesOfSelectedBranch.this, CustomerServiceRequest.class);
                            intent.putExtra("serviceName", nameOfServiceI);
                            intent.putExtra("employeeEmail", email);

                            startActivity(intent);
                        }
                    });
                    linearLayout.addView(newView);
                }


                Button addEmplInfoToDB = findViewById(R.id.goToForm);
                addEmplInfoToDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ServicesOfSelectedBranch.this, CustomerServiceRequest.class);
                        String branchEmail = extras.getString("email");
                        intent.putExtra("email", branchEmail);


                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}