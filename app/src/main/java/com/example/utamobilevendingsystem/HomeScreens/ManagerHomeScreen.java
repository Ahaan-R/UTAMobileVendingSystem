package com.example.utamobilevendingsystem.HomeScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.utamobilevendingsystem.LocationScreen;
import com.example.utamobilevendingsystem.LoginActivity;
import com.example.utamobilevendingsystem.OperatorDetails;
import com.example.utamobilevendingsystem.OperatorList;
import com.example.utamobilevendingsystem.OperatorScheduleList;
import com.example.utamobilevendingsystem.OrderDetails;
import com.example.utamobilevendingsystem.VehicleScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.utamobilevendingsystem.R;

public class ManagerHomeScreen extends AppCompatActivity {
    String fName,lName,username,dob,phoneNummber,email,address,city,state,zip;
    private Button VIEW_OPTR,VIEW_SCHEDULE;
    TextView fNameTV,lNameTV,usernameTV,dobTV,phoneNummberTV,emailTV,addressTV,cityTV,stateTV,zipTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_screen);
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
        fetchSharedPref();
        setUserProfile();

        VIEW_OPTR= (Button) findViewById(R.id.viewoperator);
        VIEW_SCHEDULE=(Button) findViewById(R.id.schedule_id);
        VIEW_OPTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOperatorlist();
            }
        });

        VIEW_SCHEDULE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewschedule();
            }
        });

    }

    private void setUserProfile() {
        fNameTV.setText("First Name: "+fName);
        lNameTV.setText("Last Name: "+lName);
        usernameTV.setText("Welcome, "+username);
        dobTV.setText("Date of Birth: "+dob);
        phoneNummberTV.setText("Phone: "+phoneNummber);
        emailTV.setText("Email: "+email);
        addressTV.setText("Address: "+address);
        cityTV.setText("City: "+city);
        stateTV.setText("State: "+state);
        zipTV.setText("ZIP: "+zip);
    }

    private void fetchSharedPref() {
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        fName =prefs.getString("fname","");
        lName =prefs.getString("lname","");
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
                viewOrders();
                return true;
            case R.id.app_bar_search:
                vehicleSearch();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.change_password:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void vehicleSearch() {
        Intent myint = new Intent(ManagerHomeScreen.this, VehicleScreen.class);
        startActivity(myint);
    }

    private void viewOrders() {
        Intent myint = new Intent(ManagerHomeScreen.this, OrderDetails.class);
        startActivity(myint);
    }

    private void logout() {
        Intent logout = new Intent(ManagerHomeScreen.this, LoginActivity.class);
        startActivity(logout);
    }
    public void openOperatorlist(){

        Intent intent= new Intent(this, OperatorList.class );
        startActivity(intent );
    }
    public void viewschedule(){

        Intent intent= new Intent(this, OperatorScheduleList.class );
        startActivity(intent );
    }

    private void viewLocationList(){
        Intent changePasswordIntent = new Intent(ManagerHomeScreen.this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }

}
