package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utamobilevendingsystem.domain.UserDetails;

import java.io.Serializable;

public class ChangePassword extends AppCompatActivity {

    DatabaseHelper dbHelper= dbHelper = new DatabaseHelper(this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    EditText passwordET, reEnterPasswordET;
    Button changeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordET = findViewById(R.id.passwordET);
        reEnterPasswordET  = findViewById(R.id.reEnterPasswordET);
        changeBtn = findViewById(R.id.changeBtn);
        int userId = 1;
        if(validatePassword()){
            updatePassword(passwordET.getText().toString(), userId);
            Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
            startActivity(intent);
        } else {
            passwordET.getText().clear();
            reEnterPasswordET.getText().clear();
            Toast.makeText(getApplicationContext(), "Entered Passwords Do Not Match..!!",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validatePassword(){
        String password = passwordET.getText().toString();
        String reEnterPassword = reEnterPasswordET.getText().toString();
        if(password.equalsIgnoreCase(reEnterPassword)){
            return true;
        }
        return false;
    }

    public void updatePassword(String password, int userID){
        ContentValues cv = new ContentValues();
        cv.put(Resources.USER_CREDS_PASSWORD, password);
        String tableName = Resources.TABLE_USER_CREDS;

        int value = db.update(Resources.USER_CREDS_PASSWORD,cv ,"user_id = "+ userID, null);
    }
}
