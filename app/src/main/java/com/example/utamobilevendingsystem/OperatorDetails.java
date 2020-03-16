package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.UserDetails;

public class OperatorDetails extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    TextView odFirstNameDesctv, odLastNameDesctv, odPhoneDesctv, odEmailDesctv, odLocationDesctv, odVehicleDesctv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_details);

        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        odFirstNameDesctv = findViewById(R.id.odFirstNameDesctv);
        odLastNameDesctv = findViewById(R.id.odLastNameDesctv);
        odPhoneDesctv = findViewById(R.id.odPhoneDesctv);
        odEmailDesctv = findViewById(R.id.odEmailDesctv);
        odLocationDesctv = findViewById(R.id.odLocationDesctv);
        odVehicleDesctv = findViewById(R.id.odVehicleDesctv);

        String userID = getIntent().getStringExtra("userID");
        String operatorDetaolsQuery = "select u.first_name, u.last_name,u.emailid, u.phone, v.name, l.locationName from " +
                "user_details u LEFT join operator_vehicle o on o.user_id = u.user_id " +
                "LEFT JOIN vehicle v on v.vehicle_id = o.vehicle_id " +
                "LEFT JOIN location l on v.location_id = l.location_id " +
                "where u.user_id = ?";

        Cursor c = db.rawQuery(operatorDetaolsQuery, new String[] {userID});

        if (c.getCount() > 0){
            c.moveToFirst();
            UserDetails user;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                odFirstNameDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)));
                odLastNameDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_LNAME)));
                odPhoneDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_PHONE)));
                odEmailDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_EMAIL_ID)));
                odLocationDesctv.setText(c.getString(c.getColumnIndex(Resources.LOCATION_NAME))== null ? Status.UNASSIGNED.getDescription(): c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
                odVehicleDesctv.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_NAME))== null ? Status.UNASSIGNED.getDescription(): c.getString(c.getColumnIndex(Resources.VEHICLE_NAME)));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }

}
