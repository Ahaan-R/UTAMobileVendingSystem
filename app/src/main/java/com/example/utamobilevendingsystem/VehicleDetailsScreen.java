package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.text.DecimalFormat;

public class VehicleDetailsScreen extends AppCompatActivity {
    Button viewInventory;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView tvNameDesc, tvLocationDesc,tvVehicleTypeDesc,tvOperatorDesc,tvScheduleDesc,tvTotalRevenueDesc;
    Switch toggleAvailability;
    String vehicleID;
    final int OPERATOR_REQUEST_CODE = 1111;
    final int LOCATION_REQUEST_CODE = 2222;

    final String VEHICLE_DETAILS_SCREEN_QUERY = "select v.name, l.locationName, v.type, v.availability, l.schedule, u.first_name, v.user_id, v.schedule_time " +
            "from vehicle v LEFT JOIN location l on l.location_id = v.location_id " +
            "LEFT JOIN user_details u on v.user_id = u.user_id WHERE v.vehicle_id = ?";

    final String VEHICLE_TOTAL_REVENUE = "SELECT sum(O.order_item_price) FROM orders O LEFT JOIN vehicle V ON O.order_vehicle_id=V.vehicle_id WHERE O.order_vehicle_id = ? GROUP BY O.order_id";

    final String LOCATION_SCHEDULE_QUERY = "select schedule from location where locationName = ?";

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
        vehicleID = getIntent().getStringExtra("vehicleID");

        Cursor c = db.rawQuery(VEHICLE_DETAILS_SCREEN_QUERY, new String[]{vehicleID});

        if (c.getCount() > 0){
            c.moveToFirst();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                tvNameDesc.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_NAME)));
                tvLocationDesc.setText(c.getString(c.getColumnIndex(Resources.LOCATION_NAME)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
                tvVehicleTypeDesc.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_TYPE)).contains("Cart")? VehicleType.CART.getDescription(): VehicleType.FOOD_TRUCK.getDescription());
                tvOperatorDesc.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)));
                tvScheduleDesc.setText(("".equalsIgnoreCase(c.getString(c.getColumnIndex(Resources.VEHICLE_SCHEDULE_TIME))) ||c.getString(c.getColumnIndex(Resources.VEHICLE_SCHEDULE_TIME)) == null) ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.VEHICLE_SCHEDULE_TIME)));
                toggleAvailability.setChecked(c.getString(c.getColumnIndex(Resources.VEHICLE_AVAILABILITY)).equalsIgnoreCase(Status.AVAILABLE.getDescription()) ? true : false);
            }
        }
        c = db.rawQuery(VEHICLE_TOTAL_REVENUE, new String[]{vehicleID});

        float totalCost = 0;
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                totalCost += Float.valueOf(c.getString(0)) * 1.0825;
            }
        }
        DecimalFormat df = new DecimalFormat("####0.00");
        tvTotalRevenueDesc.setText(String.valueOf(df.format(totalCost)));

        toggleAvailability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    updateVehicleAvaiablity(Status.AVAILABLE);
                } else {
                    updateVehicleAvaiablity(Status.UNAVAILABLE);
                }
            }
        });
        viewInventory= findViewById(R.id.viewInventoryBtn);
        viewInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(VehicleDetailsScreen.this,VehicleInventoryScreen.class);
                myint.putExtra("vehicleID", vehicleID);
                startActivity(myint);
            }
        });


        tvOperatorDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), OperatorList.class );
                intent.putExtra("vehicleID", vehicleID);
                intent.putExtra("callingActivity", VehicleDetailsScreen.class.toString());
                startActivityForResult(intent, OPERATOR_REQUEST_CODE );
            }
        });

        tvLocationDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), LocationScreen.class );
                intent.putExtra("vehicleID", vehicleID);
                intent.putExtra("callingActivity", VehicleDetailsScreen.class.toString());
                startActivityForResult(intent, LOCATION_REQUEST_CODE );
            }
        });

        tvScheduleDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvLocationDesc.getText().toString().equals(Status.UNASSIGNED.getDescription())){
                    Toast.makeText(getApplicationContext(), "Please Assign location before setting the schedule..!", Toast.LENGTH_SHORT).show();
                } else {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(VehicleDetailsScreen.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Cursor c = db.rawQuery(LOCATION_SCHEDULE_QUERY,  new String[] {tvLocationDesc.getText().toString()});
                            c.moveToFirst();
                            int locationSchedule = c.getInt(c.getColumnIndex(Resources.LOCATION_SCHEDULE));
                            int closingTime = hourOfDay+locationSchedule;
                            tvScheduleDesc.setText(hourOfDay + ":" + (minute < 10? "0"+minute : minute) +" - "+ closingTime +":" + (minute < 10? "0"+minute : minute));
                            updateVehicleScheduleTime(hourOfDay, minute, closingTime);
                        }
                    }, 0, 0, true);
                    timePickerDialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == OPERATOR_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            tvOperatorDesc.setText(data.getStringExtra("userName"));
        }

        if (requestCode == LOCATION_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            tvLocationDesc.setText(data.getStringExtra("locationName"));
            if(Status.UNASSIGNED.getDescription().equalsIgnoreCase(data.getStringExtra("locationName"))){
                tvScheduleDesc.setText(Status.UNASSIGNED.getDescription());
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(Resources.VEHICLE_SCHEDULE_TIME, "");
            db.update(Resources.TABLE_VEHICLE, contentValues, "vehicle_id = ?", new String[]{vehicleID});
        }
    }

    private void updateVehicleScheduleTime(int hourOfDay, int minute, int closingTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resources.VEHICLE_SCHEDULE_TIME, hourOfDay + ":" + (minute < 10? "0"+minute : minute) +" - "+ closingTime +":" + (minute < 10? "0"+minute : minute));
        db.update(Resources.TABLE_VEHICLE, contentValues, "vehicle_id = ?", new String[]{vehicleID});
    }

    private void updateVehicleAvaiablity(Status status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resources.VEHICLE_AVAILABILITY, status.getDescription());
        db.update(Resources.TABLE_VEHICLE,contentValues, "vehicle_id = ?", new String[] {vehicleID});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        SharedPreferences preferences = getSharedPreferences("currUser", MODE_PRIVATE);
        String role = preferences.getString("userRole","");
        if("Manager".equalsIgnoreCase(role)){
            menu.findItem(R.id.app_bar_search).setVisible(true);
        }
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
            case R.id.menu_home:
                SharedPreferences preferences = getSharedPreferences("currUser", MODE_PRIVATE);
                String role = preferences.getString("userRole","");
                role= role+"HomeScreen";
                try {
                    Class<?> cls = Class.forName("com.example.utamobilevendingsystem.HomeScreens."+role);
                    Intent homeIntent = new Intent(this, cls);
                    startActivity(homeIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.change_password:
                changePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void vehicleSearch() {
        Intent myint = new Intent(this, VehicleScreen.class);
        startActivity(myint);
    }

    private void viewOrders() {
        Intent myint = new Intent(this, OrderDetails.class);
        startActivity(myint);
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent logout = new Intent(this, LoginActivity.class);
        startActivity(logout);
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(this, ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void viewLocationList(){
        Intent changePasswordIntent = new Intent(this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }
}
