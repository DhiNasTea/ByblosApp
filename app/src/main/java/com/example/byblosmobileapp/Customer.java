package com.example.byblosmobileapp;

public class Customer extends Account{
    private Role role;


    public Customer(String username, String password, String email, String firstName, String lastName
    , Role role) {
        super(username, password, email, firstName, lastName);
        this.role = Role.CUSTOMER;
    }
    public Customer() {}

    public Role getRole() {
        return role;
    }
}
