package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.util.ArrayList;

public class VehicleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_screen);
        ListView vehicleListView = (ListView) findViewById(R.id.vehicleList);

        Vehicle v1 = new Vehicle("Vehicle 1",VehicleType.FOOD_TRUCK, Status.AVAILABLE, 1);
        Vehicle v2 = new Vehicle("Vehicle 2",VehicleType.CART, Status.AVAILABLE, 2);
        Vehicle v3 = new Vehicle("Vehicle 3",VehicleType.FOOD_TRUCK, Status.AVAILABLE, 3);
        Vehicle v4 = new Vehicle("Vehicle 4",VehicleType.CART, Status.AVAILABLE, 4);
        Vehicle v5 = new Vehicle("Vehicle 5",VehicleType.CART, Status.AVAILABLE, 5);
        Vehicle v6 = new Vehicle("Vehicle 6",VehicleType.CART, Status.AVAILABLE, 6);
        Vehicle v7 = new Vehicle("Vehicle 7",VehicleType.CART, Status.AVAILABLE, 7);

        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(v1);vehicleList.add(v2);vehicleList.add(v3);vehicleList.add(v4);
        vehicleList.add(v5);vehicleList.add(v6);vehicleList.add(v7);

        VehicleListAdapter adapter = new VehicleListAdapter(this, R.layout.vehicle_list_adaptor_view_layout, vehicleList);
        vehicleListView.setAdapter(adapter);

        
    }
}
