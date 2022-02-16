package com.example.byblosmobileapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employee extends Account implements Serializable {
    private Role role;
    private Branch branch;
    private String address;
    private ArrayList<String> services = new ArrayList<>();
    private ArrayList<Service> serviceRequests = new ArrayList<>();
    private boolean available;
    private ArrayList<Integer> openHours = new ArrayList<>();

    public Employee(String username, String password, String email, String firstName, String lastName,
                    Role role, String address) {
        super(username, password, email, firstName, lastName);
        this.role = Role.EMPLOYEE;
        this.address = address;
        services.add(new String("DummyHead"));
        openHours.add(-1);
        openHours.add(-1);
        openHours.add(-1);
        openHours.add(-1);
        serviceRequests.add(new Service("dummy"));
    }

    public Employee() {}

    public ArrayList<Service> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(ArrayList<Service> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public void addServiceRequest(Service s) {
        serviceRequests.add(s);
    }

    public void setOpenHours(ArrayList<Integer> openHours) {
        this.openHours = openHours;
    }

    public ArrayList<Integer> getOpenHours() {
        return openHours;
    }

    public void setStartHour(int startH) {
        if (startH < 0 || startH >24)
            throw new IllegalArgumentException("Minutes must be within 0 and 60");
        openHours.set(0, startH);
    }
    public void setStartMin(int startM) {
        if (startM < 0 || startM > 60)
            throw new IllegalArgumentException("Minutes must be within 0 and 60");
        openHours.set(1, startM);
    }

    public void setEndHour(int endH) {
        if (endH< 0|| endH > 24)
            throw new IllegalArgumentException("Minutes must be within 0 and 60");
        openHours.set(2, endH);
    }

    public void setEndMin(int endM) {
        if (endM < 0 || endM > 60)
            throw new IllegalArgumentException("Minutes must be within 0 and 60");
        openHours.set(3,  endM);
    }


    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void addService(String service) {
        services.add(service);
    }


    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public Role getRole() {
        return role;
    }

    public Branch returnBranch() {
        return branch;
    }

    public String getBranchAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

}
