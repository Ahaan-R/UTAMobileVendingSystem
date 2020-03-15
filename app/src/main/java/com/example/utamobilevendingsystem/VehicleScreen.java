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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.util.ArrayList;

public class VehicleScreen extends Activity {
    Button vehicleDetails;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_screen);

        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();

        ArrayList<Vehicle> vehicleList = new ArrayList<>();

        vehicleDetails = findViewById(R.id.vehicleDetails);
        ListView vehicleListView = (ListView) findViewById(R.id.vehicleList);

        String selectQuery = "SELECT  * FROM " + Resources.TABLE_VEHICLE;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() > 0){
            c.moveToFirst();
            Vehicle vehicle;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                vehicle = new Vehicle();
                vehicle.setVehicleId(c.getInt(c.getColumnIndex(Resources.VEHICLE_ID)));
                vehicle.setVehicleName(c.getString(c.getColumnIndex(Resources.VEHICLE_NAME)));
                vehicle.setVehicleType("Food Truck".equalsIgnoreCase(c.getString(c.getColumnIndex(Resources.VEHICLE_TYPE))) ? VehicleType.FOOD_TRUCK : VehicleType.CART);
                vehicle.setAvailability((c.getInt(c.getColumnIndex(Resources.VEHICLE_AVAILABILITY)) == 6? Status.AVAILABLE : Status.UNAVAILABLE));
                vehicle.setLocationId(c.getInt(c.getColumnIndex(Resources.VEHICLE_LOCATION_ID)));
                vehicleList.add(vehicle);
            }
        }

        VehicleListAdapter adapter = new VehicleListAdapter(this, R.layout.vehicle_list_adaptor_view_layout, vehicleList);
        vehicleListView.setAdapter(adapter);
        vehicleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = view.findViewById(R.id.vehicleID);
                Intent intent = new Intent(VehicleScreen.this,VehicleDetailsScreen.class);
                intent.putExtra("vehicleID", Integer.parseInt(tv.getText().toString()));
                startActivity(intent);
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
