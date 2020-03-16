package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

public class VehicleInventoryScreen extends AppCompatActivity {

    final String VEHICLE_INVENTORY_QUERY = "select i.item_name, v.quantity from vehicle_inventory v join item i on v.item_id = i.item_id where v.vehicle_id = ?";
    final String VEHICLE_INVENTORY_UPDATE_QUERY = "update vehicle_inventory set quantity = ? where item_id = ? and vehicle_id = ?";
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    EditText swichAvl, drinksAvl, snacksAvl;
    Button updateInventoryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_inventory_screen);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        swichAvl = findViewById(R.id.swichAvl);
        drinksAvl = findViewById(R.id.drinksAvl);
        snacksAvl = findViewById(R.id.snacksAvl);
        updateInventoryBtn = findViewById(R.id.updateInventoryBtn);

        String vehicleID  = getIntent().getStringExtra("vehicleID");
        Cursor c = db.rawQuery(VEHICLE_INVENTORY_QUERY, new String[] {vehicleID});

        if (c.getCount() > 0){
            c.moveToFirst();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                switch (c.getString(c.getColumnIndex(Resources.ITEM_NAME))){
                    case "Sandwiches": swichAvl.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_INVENTORY_QUANTITY))); break;
                    case "Drinks": drinksAvl.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_INVENTORY_QUANTITY))); break;
                    case "Snacks": snacksAvl.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_INVENTORY_QUANTITY))); break;
                }
            }
        }

        updateInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Resources.VEHICLE_INVENTORY_QUANTITY,Integer.valueOf(swichAvl.getText().toString()));
                db.update(Resources.TABLE_VEHICLE_INVENTORY,contentValues, "item_id = ? and vehicle_id = ?", new String[] {"1", vehicleID});
                contentValues = new ContentValues();
                contentValues.put(Resources.VEHICLE_INVENTORY_QUANTITY,Integer.valueOf(drinksAvl.getText().toString()));
                db.update(Resources.TABLE_VEHICLE_INVENTORY,contentValues, "item_id = ? and vehicle_id = ?", new String[] {"2", vehicleID});
                contentValues = new ContentValues();
                contentValues.put(Resources.VEHICLE_INVENTORY_QUANTITY,Integer.valueOf(snacksAvl.getText().toString()));
                db.update(Resources.TABLE_VEHICLE_INVENTORY,contentValues, "item_id = ? and vehicle_id = ?", new String[] {"3", vehicleID});
                Toast.makeText(getApplicationContext(), "Inventory Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VehicleInventoryScreen.this, ManagerHomeScreen.class);
                startActivity(intent);
            }
        });

        //Intent intent = new Intent(VehicleInventoryScreen.this, VehicleDetailsScreen.class);
        //startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }
}
