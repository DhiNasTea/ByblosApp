package com.example.byblosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AdminPage extends AppCompatActivity {

    // Edittexts for hourly rates or car rental, truck rental and moving assistance
    EditText hourRateCREtext, hourRateTREtext, hourRateMAEtext;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;

    LinearLayout linearLayout;
    LinearLayout employeeLinearLayout;
    ArrayList<Service> serviceArrayList;

    private FloatingActionButton addNewSerivce, refreshPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        serviceArrayList = new ArrayList<Service>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference().child("Service");

        linearLayout = findViewById(R.id.list_of_services_admin_page);
        employeeLinearLayout = findViewById(R.id.display_employee_linearlayout);

        addNewSerivce = findViewById(R.id.add_new_service_button);
        refreshPage = findViewById(R.id.refresh_database);

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // If the 'Services' path in the db is empty, add services
                if (!snapshot.hasChildren()) {
                    Service carRental = new Service();
                    //carRental.setCustomerInfo(null);
                    //carRental.setfName(null);

                    Option licenseType = new Option();
                    licenseType.addAnOption("G1");
                    licenseType.addAnOption("G2");
                    licenseType.addAnOption("G");
                    licenseType.setDescription("License");
                    Option carType = new Option();
                    carType.addAnOption("compact");
                    carType.addAnOption("SUV");
                    carType.addAnOption("intermediate");
                    carType.setDescription("Type Of Car");

                    carRental.setCustomerInfo(null);
                    carRental.setTitle("Car Rental");
                    carRental.setDateOfBirth(new Date("Birth Date", null, null, null));
                    carRental.setAddressOfCustomer(new Address("Address", null, null, null, null));
                    carRental.setHourlyRate(20);
                    carRental.addOptions(licenseType);
                    carRental.addOptions(carType);

                    Service truckRental = new Service();
                    truckRental.setCustomerInfo(null);
                    truckRental.setTitle("Truck Rental");
                    truckRental.setDateOfBirth(new Date("Birth Date", null, null, null));
                    truckRental.addDate(new Date("Pick up date", null, null, null));
                    truckRental.addDate(new Date("Return date", null, null, null));
                    truckRental.addNumericalQuantity(new NumericalQuantity("Max Number of KM to be driven", -1));
                    truckRental.setAddressOfCustomer(new Address("Address", null, null, null, null));
                    truckRental.addAddress(new Address("Area where truck will be used", null,
                            null, null, null));
                    truckRental.setHourlyRate(20);

                    Service movingAssist = new Service();
                    movingAssist.setCustomerInfo(null);
                    movingAssist.setTitle("Moving Assistance");
                    movingAssist.setDateOfBirth(new Date("Birth Date", null, null, null));
                    movingAssist.setAddressOfCustomer(new Address("Address", null, null, null, null));
                    movingAssist.addNumericalQuantity(new NumericalQuantity("# of ,overs required", -1));
                    movingAssist.setHourlyRate(20);


                    db.child(carRental.getTitle()).setValue(carRental);
                    db.child(truckRental.getTitle()).setValue(truckRental);
                    db.child(movingAssist.getTitle()).setValue(movingAssist);
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Service service = dataSnapshot.getValue(Service.class);
                    System.out.println(service.getTitle());
                    if (service.isAvailable()) {
                        View serviceView = getLayoutInflater().inflate(R.layout.services_from_db, null);

                        Button modifyButton = serviceView.findViewById(R.id.modify_service);
                        modifyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                System.out.println("testing" + service.getTitle());

                                Intent intent = new Intent(AdminPage.this, ModifyServiceActivity.class);
                                intent.putExtra("service", service);

                                startActivity(intent);

                            }
                        });
                        TextView serviceTitle = serviceView.findViewById(R.id.name_of_service);
                        serviceTitle.setText(service.getTitle());

                        linearLayout.addView(serviceView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        addNewSerivce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPage.this, ModifyServiceActivity.class));
            }
        });

        refreshPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPage.this, AdminPage.class));
            }
        });

        DatabaseReference db2 = firebaseDatabase.getReference().child("Users");
        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Employee> employees = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (Objects.requireNonNull(dataSnapshot.child("role").getValue()).toString().equals("EMPLOYEE")) {
                        employees.add(dataSnapshot.getValue(Employee.class));
                    }
                }

                for (int i = 0; i < employees.size(); i++) {
                    Employee employee = employees.get(i);
                    View addEmployeeListView = getLayoutInflater().inflate(R.layout.display_employee, null);

                    TextView textView = addEmployeeListView.findViewById(R.id.employee_name);
                    textView.setText(employee.getFirstName() + " " + employee.getLastName());
                    ImageView imageView = addEmployeeListView.findViewById(R.id.remove_employee);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            employees.remove(employee);
                            db2.child(employee.getEmail().replace('.', '|')).removeValue();
                            employeeLinearLayout.removeView(addEmployeeListView);
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                        }
                    });
                    employeeLinearLayout.addView(addEmployeeListView);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}