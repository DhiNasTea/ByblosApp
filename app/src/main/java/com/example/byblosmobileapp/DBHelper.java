package com.example.byblosmobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "ByblosStorage.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "byblos_storage";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_ROLE = "role";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        addUser("admin@admin.com", "admin", "admin", "admin", "admin", String.valueOf(Account.Role.ADMIN));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creates a table with columns for email, username, password
        // first name, and last name
        String query = "CREATE TABLE " +  TABLE_NAME + " ("  +
                        COLUMN_EMAIL + " TEXT UNIQUE, " +
                        COLUMN_USERNAME + " TEXT UNIQUE, " +
                        COLUMN_PASSWORD + " TEXT, "  +
                        COLUMN_FIRST_NAME + " TEXT, " +
                        COLUMN_LAST_NAME + " TEXT, " +
                        COLUMN_ROLE  + " TEXT);";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(String email, String username, String password,
                           String firstName, String lastName, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // makes sure the email is valid
        if (!verifyEmail(email))
            return false;

        if(!verifyName(firstName, lastName))
            return false;

        // Checks if the email already exists, since each account must have unique email and username
        if (!ifEmailOrUserExists(email, username)) {
            cv.put(COLUMN_EMAIL, email);
            cv.put(COLUMN_USERNAME, username);
            cv.put(COLUMN_PASSWORD, password);
            cv.put(COLUMN_FIRST_NAME, firstName);
            cv.put(COLUMN_LAST_NAME, lastName);
            cv.put(COLUMN_ROLE, role);

        } else {
            System.out.println("email there");
            return false;
        }

        // Inserts the data into the sqlite database
        long insert = db.insert(TABLE_NAME, null, cv);
        db.close();

        // Return whether the insertion was successful
        return insert > 0;
    }

    public boolean ifEmailOrUserExists(String email, String username) {

        if (email == null && username == null)
            return false;

        String query;

        // If one option is null or both are not null, check if email exists
        if (email == null) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " LIKE '" + username + "'";
        } else {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " LIKE '" + email + "'";
        }


        SQLiteDatabase db = this.getReadableDatabase();

        // Get the result
        Cursor c = db.rawQuery(query, null);

        // If it can point to the first result, a record exists
        return c.moveToFirst();
    }


    public boolean passwordValidity(String username, String password) {
        if (username == null) {
            return false;
        }

        String dbPassword = "";
        dbPassword = retrieveInfo(username, 2);
        if (dbPassword == null) {
            return false;
        } else if (dbPassword.equals(password)){
            return  true;
        } else {
            return false;
        }
    }

    public String retrieveInfo(String username, int index) {
        // index 0 -> email, 1 -> username, 2 -> password, 3 -> first name, 4 -> last name, 5 -> role

        if (username == null)
            return null;

        if (index < 0 || index > 5)
            throw new IllegalArgumentException("Index must be >= 0 and <= 5");

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " LIKE '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(query, null);

        // Point to the first account record in the result
        result.moveToFirst();

        // Index 5 of the table contains the role
        if (result.moveToFirst()) {
            return result.getString(index);
        } else {
            return null;
        }
    }

    public boolean verifyEmail (String email) {
        int indexOfAt, indexOfDot;

        indexOfAt = email.indexOf("@");
        indexOfDot = email.indexOf(".");

        int lastIndex = email.length() - 1;

        // if the email does not contain the @ symbol or a dot, the email is invalid
        if (indexOfAt == -1 || indexOfDot == -1)
            return false;

        // we don't want the @ symbol or the dot at the end or the beginning of the email
        if (indexOfAt == 0 || indexOfAt == lastIndex || indexOfDot == 0 || indexOfDot == lastIndex)
            return false;

        // we need the @ symbol to be before the dot and have at least one character between them

        return indexOfDot - indexOfAt >= 2;
    }

    public boolean verifyName (String firstName, String lastName) {
        char[] chars = firstName.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)){
                return false;
            }
        }

        chars = lastName.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}
