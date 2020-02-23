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
import com.example.utamobilevendingsystem.domain.UserCredentials;
import com.example.utamobilevendingsystem.domain.UserDetails;
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
        insert();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=usernameET.getText().toString();
                password=passwordET.getText().toString();
                if(fetch(username,password)){
                    Intent myInt = new Intent(LoginActivity.this,MainActivity.class);
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

    public void insert(){
        ContentValues contentValues=new ContentValues();
        contentValues.put("user_id","1");
        contentValues.put("username","test");
        contentValues.put("password","pass123");
        contentValues.put("role","user");
        contentValues.put("recovery","");
        db.insert(Resources.TABLE_USER_CREDS,null, contentValues);
    }

    public boolean fetch(String username,String password){
        String selectQuery = "SELECT  * FROM " + Resources.TABLE_USER_CREDS + " WHERE "
                + "username" + " = " + username +" AND " + "password"+" = "+password;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        else{
            return false;
        }
        return true;
    }


}
