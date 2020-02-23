package com.example.utamobilevendingsystem;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
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
    SQLiteDatabase db;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        db= dbHelper.getWritableDatabase();
        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=usernameET.getText().toString();
                password=passwordET.getText().toString();
                if(fetch(username,password)=="user"){
                    Intent myInt = new Intent(LoginActivity.this, UserHomeScreen.class);
                    startActivity(myInt);
                }
                else if(fetch(username,password)=="vendor"){
                    Intent myInt = new Intent(LoginActivity.this, UserHomeScreen.class);
                    startActivity(myInt);
                }
                else if(fetch(username,password)=="manager"){
                    Intent myInt = new Intent(LoginActivity.this, UserHomeScreen.class);
                    startActivity(myInt);
                }
                else{
                    usernameET.getText().clear();
                    passwordET.getText().clear();
                    usernameET.setError("Please enter correct username and password");
                }
            }
        });



    }


    public String fetch(String username,String password){
        String selectQuery = "SELECT  * FROM " + Resources.TABLE_USER_CREDS + " WHERE "
                + Resources.USER_CREDS_USERNAME + " = " + "'"+username +"'"+" AND " + Resources.USER_CREDS_PASSWORD  +" = "+"'"+password+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null){
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

        if (c1 != null){
            c1.moveToFirst();
        }

        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(c1.getInt((c1.getColumnIndex(Resources.USER_DETAILS_ID))));
        userDetails.setAddress(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_ADDRESS))));
        userDetails.setCity(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_CITY))));
        userDetails.setDob(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_DOB))));
        userDetails.setEmail(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_EMAIL_ID))));
        userDetails.setlName(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_LNAME))));
        userDetails.setfName(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_FNAME))));
        userDetails.setState(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_STATE))));
        userDetails.setPhoneNummber(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_PHONE))));
        userDetails.setUtaID(c.getInt((c1.getColumnIndex(Resources.USER_DETAILS_UTA_ID))));
        userDetails.setZIP(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_ZIP))));
        return userRole;
    }


}
