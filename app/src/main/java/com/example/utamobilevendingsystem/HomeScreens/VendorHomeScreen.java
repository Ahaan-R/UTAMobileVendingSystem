package com.example.utamobilevendingsystem.HomeScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.utamobilevendingsystem.ChangePassword;
import com.example.utamobilevendingsystem.LocationScreen;
import com.example.utamobilevendingsystem.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.utamobilevendingsystem.R;

public class VendorHomeScreen extends AppCompatActivity {
    String firstName,lastName,username,dob,phoneNummber,email,address,city,state,zip;
    int utaID;
    TextView fNameTV,lNameTV,usernameTV,dobTV,phoneNummberTV,emailTV,addressTV,cityTV,stateTV,zipTV,utaidTV;
    EditText emailET,addressET,cityET,stateET,zipET,phoneET,dobET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fNameTV = findViewById(R.id.fNameTV);
        lNameTV= findViewById(R.id.lNameTV);
        usernameTV= findViewById(R.id.userNameTV);
        dobTV= findViewById(R.id.dobTV);
        phoneNummberTV= findViewById(R.id.phoneTV);
        emailTV= findViewById(R.id.emailTV);
        addressTV= findViewById(R.id.addressTV);
        cityTV= findViewById(R.id.cityTV);
        stateTV= findViewById(R.id.stateTV);
        zipTV= findViewById(R.id.zipTV);
        utaidTV= findViewById(R.id.utaidTV);
        fetchSharedPref();
        setUserProfile();

    }

    private void setUserProfile() {
        fNameTV.setText("First Name: "+firstName);
        lNameTV.setText("Last Name: "+lastName);
        usernameTV.setText("Welcome, "+username);
        dobTV.setText("Date of Birth: ");
        dobET.setText(dob);
        phoneNummberTV.setText("Phone: ");
        phoneET.setText(phoneNummber);
        emailTV.setText("Email: ");
        emailET.setText(email);
        addressTV.setText("Address: ");
        addressET.setText(address);
        cityTV.setText("City: ");
        cityET.setText(city);
        stateTV.setText("State: ");
        stateET.setText(state);
        zipTV.setText("ZIP: ");
        zipET.setText(zip);
    }

    private void fetchSharedPref() {
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        firstName =prefs.getString("fname","");
        lastName =prefs.getString("lname","");
        username =prefs.getString("username","");
        dob =prefs.getString("dob","");
        phoneNummber =prefs.getString("phone","");
        email =prefs.getString("email","");
        address =prefs.getString("address","");
        city =prefs.getString("city","");
        state =prefs.getString("state","");
        zip =prefs.getString("zip","");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_location:
                viewLocationList();
                return true;
            case R.id.menu_view_orders:
                //startSettings();
                return true;
            case R.id.app_bar_search:
                //startSettings();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.change_password:
                changePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        Intent logout = new Intent(VendorHomeScreen.this, LoginActivity.class);
        startActivity(logout);
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(VendorHomeScreen.this, ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void viewLocationList(){
        Intent changePasswordIntent = new Intent(VendorHomeScreen.this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }

}
