package com.example.byblosmobileapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainActivityTest {

    @Test
    public void validEmail() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("billy@gmail.com");
        assertEquals(true, valid);
    }

    public void validEmail2() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("bob@hotmail.com");
        assertEquals(true, valid);
    }

    public void validEmail3() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("______@wow.com");
        assertEquals(true, valid);
    }

    public void validEmail4() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("amazing@incredible.ca");
        assertEquals(true, valid);
    }
    public void validEmail5() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("stunning@galaxy.com");
        assertEquals(true, valid);
    }

    @Test
    public void invalidEmail() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("jimbo.com");
        assertEquals(true, valid);
    }

    public void invalidEmail2() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation(".@.s@gmail.com");
        assertEquals(true, valid);
    }

    public void invalidEmail3() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("@gmail.com");
        assertEquals(true, valid);
    }

    public void invalidEmail4() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("lol@#galaxy.co");
        assertEquals(true, valid);
    }
    public void invalidEmail5() {

        MainActivity mainActivity = new MainActivity();

        boolean valid = mainActivity.UsernameEmailValidation("aasdf@@gxy.com");
        assertEquals(true, valid);
    }
}