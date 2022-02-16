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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceApproval extends AppCompatActivity {
    String branchEmail;
    DatabaseReference db;
    FirebaseDatabase fb;
    DatabaseReference requests;

    LinearLayout layoutRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_approval);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            branchEmail = extras.getString("employeeEmail");
        }

        branchEmail = branchEmail.replace(".", "|");

        fb = FirebaseDatabase.getInstance();
        db = fb.getReference().child("Users");
        requests = db.child(branchEmail).child("serviceRequests");


        layoutRequests = findViewById(R.id.layout_list);

        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot request: snapshot.getChildren()) {
                    View serviceRequested = getLayoutInflater().inflate(R.layout.row_service_request, null, false);
                    EditText serviceName, startTime, endTime;
                    serviceName = (EditText) serviceRequested.findViewById(R.id.service_info_field);
                    startTime = (EditText) serviceRequested.findViewById(R.id.startTime);
                    endTime = (EditText) serviceRequested.findViewById(R.id.endTime);

                    serviceName.setText(request.child("title").getValue().toString());
                    startTime.setText("Not available");
                    endTime.setText("Not available");

                    layoutRequests.addView(serviceRequested);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void onClickAccept (View view) {
        int result = checkOnlyOneCheckBoxOn();
        if (result != -1) {
            Toast.makeText(this, "Service Accepted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ServiceApproval.this, EmployeeBranchActivity.class);
            intent.putExtra("email", branchEmail);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Please select ONE service", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickReject (View view) {
        int result = checkOnlyOneCheckBoxOn();
        if (result != -1) {
            Toast.makeText(this, "Service Rejected successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ServiceApproval.this, EmployeeBranchActivity.class);
            intent.putExtra("email", branchEmail);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Please select ONE service", Toast.LENGTH_SHORT).show();
        }

    }

    private int checkOnlyOneCheckBoxOn() {
        int j = 0;
        int checkbox = -1;

        for (int i = 0; i < layoutRequests.getChildCount(); i++) {
            View v = layoutRequests.getChildAt(i);
            System.out.println(v.toString());

            v = v.findViewById(R.id.serviceToReviewCheckBox);

            if (1 == -1) {
                //validate your EditText here
            } else if (v instanceof CheckBox) {
                //validate CheckBox
                if (((CheckBox) v).isChecked()) {
                    System.out.println("checked");
                    j++;
                    checkbox = i;
                }
            } //etc. If it fails anywhere, just return false.
        }

        if (j==1)
            return checkbox;
        else
            return -1;
    }

}