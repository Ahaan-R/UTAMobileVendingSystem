package com.example.utamobilevendingsystem;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
import com.example.utamobilevendingsystem.HomeScreens.VendorHomeScreen;
import com.example.utamobilevendingsystem.domain.UserCredentials;
import com.example.utamobilevendingsystem.domain.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText usernameET,passwordET;
    String username,password;
    TextView register;
    SQLiteDatabase db;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        register = findViewById(R.id.register);
        db= dbHelper.getWritableDatabase();
        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=usernameET.getText().toString();
                password=passwordET.getText().toString();
                switch (fetch(username, password)) {
                    case "user": {
                        Intent myInt = new Intent(LoginActivity.this, UserHomeScreen.class);
                        startActivity(myInt);
                        break;
                    }
                    case "vendor": {
                        Intent myInt = new Intent(LoginActivity.this, VendorHomeScreen.class);
                        startActivity(myInt);
                        break;
                    }
                    case "manager": {
                        Intent myInt = new Intent(LoginActivity.this, ManagerHomeScreen.class);
                        startActivity(myInt);
                        break;
                    }
                    default:
                        usernameET.getText().clear();
                        passwordET.getText().clear();
                        usernameET.setError("Please enter correct username and password");
                        break;
                }
            }
        });



    }

    public void insert(){
        ContentValues contentValues=new ContentValues();
        contentValues.put("user_id","1");
        contentValues.put("username","test");
        contentValues.put("password","pass123");
        contentValues.put("role","user");
        contentValues.put("recovery","");
        db.insert(Resources.TABLE_USER_CREDS,null, contentValues);
        ContentValues cv= new ContentValues();
        cv.put("user_id","1");
        cv.put("username","test");
        cv.put("first_name","Prajwal");
        cv.put("last_name","Prasad");
        cv.put("uta_id","1001");
        cv.put("dob","11/11/2019");
        cv.put("phone","9876666111");
        cv.put("emailid","pp@gmail.com");
        cv.put("city","blr");
        cv.put("state","KA");
        cv.put("zip","560079");
        db.insert(Resources.TABLE_USER_DETAILS,null, cv);
    }

    public String fetch(String username,String password){
        String selectQuery = "SELECT  * FROM " + Resources.TABLE_USER_CREDS + " WHERE "
                + Resources.USER_CREDS_USERNAME + " = " + "'"+username +"'"+" AND " + Resources.USER_CREDS_PASSWORD  +" = "+"'"+password+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() > 0){
            c.moveToFirst();
        }
        else{
            return "";
        }

        UserCredentials userCredentials = new UserCredentials();
        int  uid=c.getInt(c.getColumnIndex(Resources.USER_CREDS_USER_ID));
        String userRole = c.getString(c.getColumnIndex(Resources.USER_CREDS_ROLE));
        String profileQuery= "SELECT * FROM "+ Resources.TABLE_USER_DETAILS+ " WHERE " +  Resources.USER_DETAILS_ID + " = " + uid;
        Cursor c1 = db.rawQuery(profileQuery, null);

        if (c1.getCount() >0){
            c1.moveToFirst();
        }

        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.putInt("userid",(c1.getInt((c1.getColumnIndex(Resources.USER_DETAILS_ID)))));
        editor.putInt("utaid",(c1.getInt((c1.getColumnIndex(Resources.USER_DETAILS_UTA_ID)))));
        editor.putString("address",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_ADDRESS)))));
        editor.putString("username",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_USERNAME)))));
        editor.putString("city",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_CITY)))));
        editor.putString("state",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_STATE)))));
        editor.putString("email",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_EMAIL_ID)))));
        editor.putString("fname",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_FNAME)))));
        editor.putString("lname",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_LNAME)))));
        editor.putString("phone",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_PHONE)))));
        editor.putString("dob",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_DOB)))));
        editor.putString("zip",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_ZIP)))));
        editor.apply();
        return userRole;
    }


    public void navigateToRegister(View view) {
        register = findViewById(R.id.register);
        Intent navigateToRegister = new Intent(this, RegistrationActivity.class);
        startActivity(navigateToRegister);
    }
}
