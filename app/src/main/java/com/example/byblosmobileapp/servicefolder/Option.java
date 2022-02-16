package com.example.byblosmobileapp.servicefolder;

import java.io.Serializable;
import java.util.ArrayList;

public class Option implements Serializable {

    private ArrayList<String> options = new ArrayList<>();
    private String description = "";
    private int indexOfSelectedOption = -1;

    public Option() { }

    public Option(String description, String options) {
        if (options == null) {
            this.options = null;
        } else {
            String[] optionsArray = options.split(",");
            for (int i = 0; i < optionsArray.length; i++) {
                this.options.add(optionsArray[i]);
            }
        }
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAnOption(String o) {
        options.add(o);
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void selectedOption(String o) {
        //boolean exists = false;
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(o)) {
                //exists = true;
                indexOfSelectedOption = i;
            }
        }


    }
}
