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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

public class VehicleDetailsScreen extends AppCompatActivity {
    Button viewInventory;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView tvNameDesc, tvLocationDesc,tvVehicleTypeDesc,tvOperatorDesc,tvScheduleDesc,tvTotalRevenueDesc;
    Switch toggleAvailability;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details_screen);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        tvNameDesc = findViewById(R.id.tvNameDesc);
        tvLocationDesc = findViewById(R.id.tvLocationDesc);
        tvVehicleTypeDesc = findViewById(R.id.tvVehicleTypeDesc);
        tvOperatorDesc = findViewById(R.id.tvOperatorDesc);
        tvScheduleDesc = findViewById(R.id.tvScheduleDesc);
        tvTotalRevenueDesc = findViewById(R.id.tvTotalRevenueDesc);
        toggleAvailability = findViewById(R.id.switchAvaiability);
        String id = getIntent().getStringExtra("vehicleID");

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
                tvVehicleTypeDesc.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_TYPE)).contains("Cart")? VehicleType.CART.getDescription(): VehicleType.FOOD_TRUCK.getDescription());
                tvOperatorDesc.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
                tvScheduleDesc.setText(c.getString(c.getColumnIndex(Resources.LOCATION_SCHEDULE)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
                tvTotalRevenueDesc.setText("1233");
            }
        }

        toggleAvailability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(), "avalable", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not avalable", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewInventory= findViewById(R.id.viewInventoryBtn);
        viewInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(VehicleDetailsScreen.this,VehicleInventoryScreen.class);
                myint.putExtra("vehicleID", id);
                startActivity(myint);
            }
        });


        tvOperatorDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), OperatorList.class );
                intent.putExtra("vehicleID", id);
                intent.putExtra("callingActivity", VehicleDetailsScreen.class.toString());
                startActivityForResult(intent, 123 );
            }
        });

        tvLocationDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), LocationScreen.class );
                intent.putExtra("vehicleID", id);
                intent.putExtra("callingActivity", VehicleDetailsScreen.class.toString());
                startActivityForResult(intent, 124 );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            tvOperatorDesc.setText(data.getStringExtra("userName"));
        }

        if (requestCode == 124 && resultCode == RESULT_OK && data != null) {
            tvLocationDesc.setText(data.getStringExtra("locationName"));
        }
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
        Intent myint = new Intent(VehicleDetailsScreen.this, VehicleScreen.class);
        startActivity(myint);
    }

    private void viewOrders() {
        Intent myint = new Intent(VehicleDetailsScreen.this, OrderDetails.class);
        startActivity(myint);
    }

    private void logout() {
        Intent logout = new Intent(VehicleDetailsScreen.this, LoginActivity.class);
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
        Intent changePasswordIntent = new Intent(VehicleDetailsScreen.this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }
}
