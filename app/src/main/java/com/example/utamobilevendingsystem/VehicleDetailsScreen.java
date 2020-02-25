package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VehicleDetailsScreen extends AppCompatActivity {
    Button viewInventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details_screen);
        viewInventory= findViewById(R.id.viewInventoryBtn);
        viewInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(VehicleDetailsScreen.this,VehicleInventoryScreen.class);
                startActivity(myint);
            }
        });
    }
}
