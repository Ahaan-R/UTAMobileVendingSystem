package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.util.ArrayList;

public class VehicleScreen extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_screen);

        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();

        ArrayList<Vehicle> vehicleList = new ArrayList<>();

        ListView vehicleListView = (ListView) findViewById(R.id.vehicleList);

        String selectQuery = "select v.vehicle_id, v.name, v.type, v.availability, v.location_id, l.locationName from vehicle v left join location l on v.location_id = l.location_id";
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
                vehicle.setLocationName(c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
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
                intent.putExtra("vehicleID", tv.getText().toString());
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
        Intent myint = new Intent(VehicleScreen.this, VehicleScreen.class);
        startActivity(myint);
    }

    private void viewOrders() {
        Intent myint = new Intent(VehicleScreen.this, OrderDetails.class);
        startActivity(myint);
    }

    private void logout() {
        Intent logout = new Intent(VehicleScreen.this, LoginActivity.class);
        startActivity(logout);
    }
    public void openOperatorlist(){
        Intent intent= new Intent(this, OperatorList.class );
        intent.putExtra("callingActivity", ManagerHomeScreen.class.toString());
        startActivity(intent );
    }
    public void viewschedule(){

        Intent intent= new Intent(this, OperatorScheduleList.class );
        startActivity(intent );
    }

    private void viewLocationList(){
        Intent changePasswordIntent = new Intent(VehicleScreen.this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }

}
