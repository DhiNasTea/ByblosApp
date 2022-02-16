package com.example.byblosmobileapp;

import java.util.ArrayList;

public class Account {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;


    static ArrayList<Account> listOfAccounts;

    public enum Role {EMPLOYEE, CUSTOMER, ADMIN};

    public Account() {}

    public Account(String username, String password, String email, String firstName,
                   String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private boolean isPasswordOK(String password) {
        if (password.length() < 6) {
            return false;
        }
        return true;
    }

    // returns null if no such account exists
    private Account searchAccounts(Account accountToFind) {
        for (Account myAccount : listOfAccounts) {
            //if (myAccount == accountToFind) {
            //    return myAccount;
            //}
            if (myAccount.equals(accountToFind)) {
                return myAccount;
            }
        }
        return null;
    }

    private boolean equals(Account otherAccount) {
        return username.equals(otherAccount.getUsername()) &&
                password.equals(otherAccount.getPassword()) &&
                email.equals(otherAccount.getEmail()) &&
                firstName.equals(otherAccount.getFirstName()) &&
                lastName.equals(otherAccount.getLastName());
    }


}
