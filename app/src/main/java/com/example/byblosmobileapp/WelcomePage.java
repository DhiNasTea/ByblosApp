package com.example.byblosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchableInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WelcomePage extends AppCompatActivity {


    //ArrayList<String> branches;

    //ArrayAdapter<String> arrayAdapter;

    ArrayList<Employee> employeeArrayList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;

    boolean serviceFound = false;

    RecyclerView recycler;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        TextView welcomeUser = (TextView) findViewById(R.id.welcomeUser);
        TextView welcomeRole = (TextView) findViewById(R.id.welcomeRole);

        String name;
        String role;

        employeeArrayList = new ArrayList<>();
        recycler = findViewById(R.id.list_of_branch_recycler);

        adapter = new RecyclerAdapter(WelcomePage.this, employeeArrayList);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WelcomePage.this);

        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);


        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference().child("Users");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                role = null;
                name = null;
            } else {
                role = extras.getString("role");
                name = extras.getString("name");
            }
        } else {
            role = (String) savedInstanceState.getSerializable("role");
            name = (String) savedInstanceState.getSerializable("name");
        }

        if (name != null) {
            String msg = "Welcome " + name + "!";
            welcomeUser.setText(msg);
        }

        if (role != null) {
            String msg = "You are logged in as a " + role;
            welcomeRole.setText(msg);
        }


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (dataSnapshot.child("role").getValue(String.class).equals("EMPLOYEE")) {

                        Employee employee = dataSnapshot.getValue(Employee.class);
                        System.out.println(employee.getUsername());
                        if (employee.isAvailable())
                            employeeArrayList.add(employee);
                        System.out.println(employeeArrayList.size());

                    }
                }

                adapter = new RecyclerAdapter(WelcomePage.this, employeeArrayList);
                recycler.setAdapter(adapter);

                if(adapter.getItemCount() == 0)
                    Toast.makeText(WelcomePage.this, "No items to show", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String branchName = query;

                for (Employee employee: employeeArrayList) {
                    if (employee.getUsername().equals(branchName)){
                        Intent intent = new Intent(WelcomePage.this, ServicesOfSelectedBranch.class);
                        intent.putExtra("email", employee.getEmail());

                        startActivity(intent);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });




        return true;
    }


    public void buttonFunctionSignOut(View view) {
        if (view.getId() == R.id.buttonSignOut) {
            // DBHelper myDB = new DBHelper(WelcomePage.this);
            Intent intent = new Intent(WelcomePage.this, MainActivity.class);
            // Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }



}