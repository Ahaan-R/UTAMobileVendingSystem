package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

public class VehicleDetailsScreen extends Activity {
    Button viewInventory;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details_screen);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        TextView tvNameDesc, tvLocationDesc,tvVehicleTypeDesc,tvOperatorDesc,tvScheduleDesc,tvTotalRevenueDesc;
        tvNameDesc = findViewById(R.id.tvNameDesc);
        tvLocationDesc = findViewById(R.id.tvLocationDesc);
        tvVehicleTypeDesc = findViewById(R.id.tvVehicleTypeDesc);
        tvOperatorDesc = findViewById(R.id.tvOperatorDesc);
        tvScheduleDesc = findViewById(R.id.tvScheduleDesc);
        tvTotalRevenueDesc = findViewById(R.id.tvTotalRevenueDesc);
        int id = getIntent().getIntExtra("VehicleID", 1);

        String vehicleDetailScreenQuery = "select v.name, l.locationName, v.type, l.schedule, u.first_name, v.user_id " +
                                            "from (vehicle v LEFT JOIN location l on l.location_id = v.location_id) " +
                                            "LEFT JOIN user_details u on v.user_id = u.user_id WHERE v.vehicle_id = "+id;

        Cursor c = db.rawQuery(vehicleDetailScreenQuery, null);

        if (c.getCount() > 0){
            c.moveToFirst();
            Vehicle vehicle;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                tvNameDesc.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_NAME)));
                tvLocationDesc.setText(c.getString(c.getColumnIndex(Resources.LOCATION_NAME)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
                tvVehicleTypeDesc.setText(c.getInt(c.getColumnIndex(Resources.VEHICLE_TYPE)) == 1? VehicleType.CART.getDescription(): VehicleType.FOOD_TRUCK.getDescription());
                tvOperatorDesc.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)));
                //tvScheduleDesc.setText(c.getInt(c.getColumnIndex(Resources.LOCATION_SCHEDULE)));
                tvScheduleDesc.setText("2");
                tvTotalRevenueDesc.setText("1233");
            }
        }


        viewInventory= findViewById(R.id.viewInventoryBtn);
        viewInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(VehicleDetailsScreen.this,VehicleInventoryScreen.class);
                startActivity(myint);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }
}
