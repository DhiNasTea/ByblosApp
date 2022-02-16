package com.example.byblosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText emailText; // The editText for the username
    private EditText passwordText; // The password

    private Account[] accounts;

    private CheckBox emplOrCust;

    FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;

    String defUsername = "testemployeeadress@gmail.com";
    String defPassword = "password";



    public static final Pattern Valid_Email_Pattern =
            Pattern.compile("^[a-z0-9_.]+@[a-z0-9_]+\\.[a-z0-9_]+(\\.[a-z0-9_]+)*$"
                    , Pattern.CASE_INSENSITIVE);


    public boolean UsernameEmailValidation(String userName) {
        Matcher matcher = Valid_Email_Pattern.matcher(userName.trim());
        return matcher.find();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mAuth.signInWithEmailAndPassword(username, password);
        emailText = findViewById(R.id.email_sign_in);
        passwordText = findViewById(R.id.password);

        // Checkbox to see if user is employee or customer
        emplOrCust = findViewById(R.id.empl_or_cust);

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

    }


    public void buttonFunction(View view) {
        String tagLogIn = "successLoggingIn";
        switch(view.getId()) {
            //TODO: change db to be able to iterate through emails
            // emails should be at the level under Users (currently it's Customer and Employee that
            // are at that level
            // db = db.child(email) would then give us the right person

            case R.id.logIn:
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (email.equals("admin") && password.equals("admin")) {
                    Intent intent = new Intent(this, AdminPage.class);
                    startActivity(intent);
                    return;
                } else if (email.equals("employee") && password.equals("employee")) {
                    defUsername = "testemployeeadress@gmail.com";
                    password = "password";
                    mAuth.signInWithEmailAndPassword(defUsername, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(tagLogIn, "onComplete: ");
                                db = firebaseDatabase.getReference().child("Users").child(defUsername.replace('.', '|'));
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.d(tagLogIn, "called");

                                        Employee newEmployee;


                                        Intent intent;

                                        newEmployee = snapshot.getValue(Employee.class);

                                        intent = new Intent(MainActivity.this, EmployeeBranchActivity.class);
                                        intent.putExtra("email",newEmployee.getEmail());
                                        //System.out.println(newEmployee.getEmail());
                                        //TODO: Put newEmployee object into the intent as well
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });
                } else if (email.equals("customer") && password.equals("customer")) {
                    defUsername = "customer@gmail.com";
                    password = "password";
                    mAuth.signInWithEmailAndPassword(defUsername, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(tagLogIn, "onComplete: ");
                                db = firebaseDatabase.getReference().child("Users").child(defUsername.replace('.', '|'));
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.d(tagLogIn, "called");

                                        Customer newCustomer;


                                        Intent intent;

                                        newCustomer = snapshot.getValue(Customer.class);

                                        intent = new Intent(MainActivity.this, WelcomePage.class);
                                        intent.putExtra("email",newCustomer.getEmail());
                                        //System.out.println(newEmployee.getEmail());
                                        //TODO: Put newEmployee object into the intent as well
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });

                }else {
                    // FirebaseDatabase db = FirebaseDatabase.getInstance();
                   // DatabaseReference databaseReference = db.getReference().child("Users").child(username).child("email");
                    mAuth.signInWithEmailAndPassword(defUsername, defPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(tagLogIn, "onComplete: ");
                                db = firebaseDatabase.getReference().child("Users").child(email.replace('.', '|'));
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.d(tagLogIn, "called");

                                        Customer newCustomer = null;
                                        Employee newEmployee = null;


                                        Intent intent;
                                        if (Objects.equals(snapshot.child("role").getValue(String.class), "EMPLOYEE")) {
                                            newEmployee = snapshot.getValue(Employee.class);

                                            intent = new Intent(MainActivity.this, EmployeeBranchActivity.class);
                                            intent.putExtra("email",newEmployee.getEmail());
                                            //System.out.println(newEmployee.getEmail());
                                            //TODO: Put newEmployee object into the intent as well
                                            startActivity(intent);
                                        } else {
                                            newCustomer = snapshot.getValue(Customer.class);

                                            intent = new Intent(MainActivity.this, WelcomePage.class);
                                            intent.putExtra("role", "Customer");
                                            intent.putExtra("name", newCustomer.getFirstName());
                                            //TODO: Put newCustomer object into the intent as well
                                            startActivity(intent);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }


                                });
                                // db.addValueEventListener(new OnCompleteListener<>() )

                            }
                        }
                    });
                    }
                break;
            case R.id.createAccountBtn:
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                // Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }

                // Check if username and password belong to the admin
//                if (username == "admin" && password == "admin") {
//                    Intent adminIntent = new Intent(MainActivity.this, WelcomePage.class);
//                    startActivity(adminIntent);
//                    break;
//                }

                // Method to check if this username exists in the database
                /*Boolean exists = dbHelper.ifEmailOrUserExists(null, username);
                Boolean validPassword = dbHelper.passwordValidity(username,
                                                                  password);


                Intent intent2 = new Intent(MainActivity.this, WelcomePage.class);


                if (exists && validPassword) {
                    intent2.putExtra("name", dbHelper.retrieveInfo(username, 3));
                    intent2.putExtra("role", dbHelper.retrieveInfo(username, 5));

                    startActivity(intent2);
                } else {
                    Toast.makeText(this, "Account does not exist with that password!", Toast.LENGTH_SHORT).show();
                }*/
                // Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();


        }


}
