package com.example.byblosmobileapp;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FBHelper {

    private DatabaseReference databaseReference;
    private FirebaseDatabase db;

    // May have to store other data aside from User info in the future
    // So it is generalized by adding an instance variable for it
    private String child;

    private String result;

    public FBHelper () {
         db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
    }

    public FBHelper (String child) {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference().child(child);
        this.child = child;
    }

    public void setChild(String child) {
        this.child = child;
        databaseReference = databaseReference.child(child);
    }

    public Task<Void> addEmployee(Employee acc) {
        if (acc == null)
            throw new NullPointerException("Account cannot be null");

        //return databaseReference.push().setValue(acc);
        return databaseReference.child(acc.getEmail()).setValue(acc);
    }

    public Task<Void> addCustomer(Customer acc) {
        if (acc == null)
            throw new NullPointerException("Account cannot be null");

        //return databaseReference.push().setValue(acc);
        return databaseReference.child(acc.getEmail()).setValue(acc);
    }

    public String getRole(String username) {
        final String[] result = {""};

        setChild("Users");
        databaseReference.child(username).child("role").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result[0] = dataSnapshot.getValue(String.class);
                //do what you want with the likes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return result[0];
    }
    public String getEmail(String username) {
        result = "";
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Account account = postSnapshot.getValue(Account.class);
                    Log.d("FBHelper", username);
                    result = account.getEmail();
                    Log.d("FBHelper", "User name: " + account.getUsername() + ", email " + result);
                    //return result;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FBHelper", "error");
            }
        });

        Log.d("FBHelper", result);;
        return result;
    }
}
