package com.example.byblosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity{

    // EditTexts to store email, username and password

    private FirebaseAuth mAuth;

    private FBHelper myFBHelper;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;

    private EditText mailText, userText, pwText, firstNameText, lastNameText, address;
    private CheckBox customerOrEmployee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mailText = findViewById(R.id.editTextTextEmailAddress);
        userText = findViewById(R.id.editTextTextPersonName);
        pwText = findViewById(R.id.editTextTextPassword);
        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        address = findViewById(R.id.addressText);

        customerOrEmployee = findViewById(R.id.checkBox);

        myFBHelper = new FBHelper("Users");

        /*signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(SignUp.this);
                myDB.addUser(mailText.getText().toString().trim(),
                             userText.getText().toString().trim(),
                             pwText.getText().toString().trim());
            }
        });*/
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference().child("Users");

        CheckBox checkBox = findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText addressField = findViewById(R.id.addressText);
                EditText username = findViewById(R.id.editTextTextPersonName);

                if (isChecked){
                    addressField.setVisibility(View.VISIBLE);
                    username.setHint("Enter a Branch name");
                } else {
                    addressField.setVisibility(View.INVISIBLE);
                    username.setHint("Enter a username");
                }
            }
        });

    }

    public void buttonFunctionSignUp(View view) {
        if (view.getId() == R.id.createAccountBtnn) {

            String mail = returnText(mailText);
            String password = returnText(pwText);
            Account.Role role = customerOrEmployee.isChecked() ?
                                Account.Role.EMPLOYEE :
                                Account.Role.CUSTOMER;
            /*String branchAddress = null;
            if (role == Account.Role.EMPLOYEE) {
                 branchAddress = returnText(address);
            }*/
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Customer newCustomer = null;
                                Employee newEmployee = null;
                                if (role == Account.Role.EMPLOYEE) {
                                     newEmployee = new Employee(returnText(userText)
                                            , returnText(pwText)
                                            , returnText(mailText)
                                            , returnText(firstNameText)
                                            , returnText(lastNameText)
                                            , role
                                            , returnText(address));
                                     //System.out.println("Inserting New Empl");
                                     db.child(mail.replace('.', '|')).setValue(newEmployee);
                                } else {
                                    newCustomer = new Customer(returnText(userText)
                                            , returnText(pwText)
                                            , returnText(mailText)
                                            , returnText(firstNameText)
                                            , returnText(lastNameText)
                                            , role);
                                    db.child(mail.replace('.', '|')).setValue(newCustomer);
                                }


                                startActivity(new Intent(SignUp.this, MainActivity.class));
                            } else {
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("SignUpAuthError", "FailureToAuth", task.getException());

                            }
                        }
                    });

            /*try {

                String role = customerOrEmployee.isChecked() ?
                              "EMPLOYEE" : "CUSTOMER";
                Account newAccount = new Account(returnText(mailText)
                        , returnText(userText)
                        , returnText(pwText)
                        , returnText(firstNameText)
                        , returnText(lastNameText));
                boolean add = myDB.addUser(returnText(mailText)
                        , returnText(userText)
                        , returnText(pwText)
                        , returnText(firstNameText)
                        , returnText(lastNameText)
                        , role);


                if (!myDB.verifyEmail(returnText(mailText)))
                    Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
                else if (!myDB.verifyName(returnText(firstNameText), returnText(lastNameText)))
                    Toast.makeText(this, "Invalid first name or last name", Toast.LENGTH_SHORT).show();
                else if (!add) {
                    Toast.makeText(this, "Failed to record account info", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Account added successfully", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            } catch (IllegalStateException e) {
                Toast.makeText(this, "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
            }*/


        }
    }

    private String returnText(EditText editText) {
        if (editText.getText().toString().trim().isEmpty())
            throw new IllegalStateException("Must enter information");

        return editText.getText().toString().trim();
    }


}