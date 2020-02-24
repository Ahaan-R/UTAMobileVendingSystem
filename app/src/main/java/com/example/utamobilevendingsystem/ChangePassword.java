package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utamobilevendingsystem.domain.UserDetails;

import java.io.Serializable;

public class ChangePassword extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    EditText passwordET, reEnterPasswordET;
    Button changeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordET = findViewById(R.id.passwordET);
        reEnterPasswordET  = findViewById(R.id.reEnterPasswordET);
        changeBtn = findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = passwordET.getText().toString();
                String reEnterPass = reEnterPasswordET.getText().toString();
                if(pass.isEmpty() || reEnterPass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter both the fields..!",Toast.LENGTH_SHORT).show();
                } else if(validatePassword()){
                    updatePassword(passwordET.getText().toString());
                    Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    passwordET.getText().clear();
                    reEnterPasswordET.getText().clear();
                    Toast.makeText(getApplicationContext(), "Entered Passwords Do Not Match..!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validatePassword(){
        String password = passwordET.getText().toString();
        String reEnterPassword = reEnterPasswordET.getText().toString();
        if(password.equalsIgnoreCase(reEnterPassword)){
            return true;
        }
        return false;
    }

    public void updatePassword(String password){
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        int userID = prefs.getInt("userid",0);
        Log.i(" userId","user ID  "+userID);
        ContentValues cv = new ContentValues();
        cv.put(Resources.USER_CREDS_PASSWORD, password);
        String tableName = Resources.TABLE_USER_CREDS;
        int value = db.update(Resources.TABLE_USER_CREDS,cv ,"user_id = "+ userID, null);
    }
}
