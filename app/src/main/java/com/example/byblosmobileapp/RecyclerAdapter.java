package com.example.byblosmobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> implements Filterable {

    List<Employee> listOfBranches;
    List<Employee> listOfBranchesFull;

    Context context;


    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView branchName;
        TextView email;
        TextView hours;
        TextView address;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            branchName = itemView.findViewById(R.id.username_of_branch);
            email = itemView.findViewById(R.id.full_name_owner);
            hours = itemView.findViewById(R.id.branch_open_hours);
            address = itemView.findViewById(R.id.address_of_branch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Row: " + branchName.getText() + " was clicked!");

                    Intent intent = new Intent(context, ServicesOfSelectedBranch.class);

                    intent.putExtra( "email", email.getText());

                    context.startActivity(intent);
                }
            });
        }
    }

    public RecyclerAdapter (Context context, List<Employee> branches) {
        this.context = context;
        listOfBranches = branches;
        listOfBranchesFull = new ArrayList<>(branches);
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.branch_row, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {


        Employee employee = listOfBranches.get(position);
        System.out.println("Size of listOfB" + listOfBranches.size());
        holder.branchName.setText(employee.getUsername());
        holder.email.setText(employee.getEmail());
        holder.hours.setText("Open Hours: " + timeFormatter(employee.getOpenHours().get(0)) + ":" + timeFormatter(employee.getOpenHours().get(1)) + " to "
                + timeFormatter(employee.getOpenHours().get(2)) + ":" + timeFormatter(employee.getOpenHours().get(3)));
        holder.address.setText(employee.getAddress() );

    }

    private String timeFormatter (int minOrHour) {
        String formatted = "";
        if (minOrHour < 10 && minOrHour >= 0) {
            formatted = "0" + String.valueOf(minOrHour);
        }
        return formatted;
    }

    @Override
    public int getItemCount() {
        return listOfBranches.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Employee> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listOfBranchesFull);
            } else {
                String filterPattern = constraint.toString().trim();
                System.out.println(filterPattern);
                for (Employee branch : listOfBranchesFull) {

                    if (branch.getServices().stream().anyMatch(e -> e.toLowerCase().contains(filterPattern) || e.contains(filterPattern)) || branch.getFullName().contains(filterPattern) || branch.getUsername().contains(filterPattern)
                        || branch.getAddress().contains(filterPattern)) {
                        filteredList.add(branch);
                        System.out.println("Found a branch: " + branch.getUsername());
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listOfBranches.clear();
            listOfBranches.addAll((List) results.values);

            notifyDataSetChanged();

        }
    };



}
