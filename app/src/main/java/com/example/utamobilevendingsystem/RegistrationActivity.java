package com.example.utamobilevendingsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class RegistrationActivity extends AppCompatActivity {

    ViewPager viewPager;
    Fragment fragment;
    EditText firstName;
    EditText lastName;
    EditText enterPassword;
    EditText confirmPassword;
    Button submitButton;
    SQLiteDatabase dbObject;
    DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        enterPassword = findViewById(R.id.enterPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = firstName.getText().toString();
                String lname = lastName.getText().toString();
                String password = enterPassword.getText().toString();
                String confirmPwd = confirmPassword.getText().toString();

                if (password.equals(confirmPwd) && (password) != "") {
                    ContentValues values = new ContentValues();
                    Toast.makeText(RegistrationActivity.this, "Username and password don't match", Toast.LENGTH_SHORT).show();
//                    dbObject.insert();
                    Intent moveToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(moveToLogin);
                }
                else if(!password.isEmpty() && !confirmPwd.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please enter values", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}



