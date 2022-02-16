package com.example.byblosmobileapp.servicefolder;

import java.io.Serializable;

public class NumericalQuantity implements Serializable {

    private String description;

    private int quantity;


    public NumericalQuantity() {}

    public NumericalQuantity(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(Object o) {
        if (o == null) {
            throw new NullPointerException();
        } else if (o.getClass() != getClass()){
            return false;
        } else {
            NumericalQuantity oNQ = (NumericalQuantity) o;
            if (oNQ.getDescription().equals(getDescription())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
