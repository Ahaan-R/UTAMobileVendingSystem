package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderSummary extends AppCompatActivity {

    DatabaseHelper dbHelper;
    String TAG = "OrderSummary";
    SQLiteDatabase db;
    TextView sandwichQuantity, drinkQuantity, snackQuantity;
    TextView sandwichPrice, drinkPrice, snackPrice;
    TextView totalPrice;
    ArrayList<String> orderItemQuantity = new ArrayList<>();
    ArrayList<String> orderItemPrice = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        getAndPutDataUsers();
    }

    private void getAndPutDataManagers() {

    }

    private void getAndPutDataUsers() {
        Log.i(TAG, "OrderSummary: getAndPutDataUsers");
        sandwichPrice = findViewById(R.id.sandwichPrice);
        drinkPrice = findViewById(R.id.drinkPrice);
        snackPrice = findViewById(R.id.snackPrice);
        sandwichQuantity = findViewById(R.id.sandwichQuantity);
        drinkQuantity = findViewById(R.id.drinkQuantity);
        snackQuantity = findViewById(R.id.snackQuantity);
        totalPrice = findViewById(R.id.totalPrice);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        int uid = prefs.getInt("userid", 0);
        Intent intent = getIntent();
        String orderID = intent.getStringExtra("orderID");
        String totalprice = intent.getStringExtra("totalPrice");
        String context = intent.getStringExtra("context");
        Cursor cursor = db.rawQuery("SELECT order_item_quantity, order_item_price FROM orders WHERE order_id=" + orderID, null);
        int loopCounter = 0;
        int counter = 0;
        int i = 0;
        while (cursor.moveToNext()) {
            if (loopCounter == 0) {
                counter = 0;
                orderItemPrice.add(counter, cursor.getString(1));
                sandwichPrice.setText(orderItemPrice.get(counter));
                i += 1;
                orderItemQuantity.add(counter, cursor.getString(0));
                sandwichQuantity.setText(orderItemQuantity.get(counter));
            } else if (loopCounter == 1) {
                counter = 1;
                orderItemPrice.add(counter, cursor.getString(1));
                drinkPrice.setText(orderItemPrice.get(counter));
                i += 1;
                orderItemQuantity.add(counter, cursor.getString(0));
                drinkQuantity.setText(orderItemQuantity.get(counter));
            } else if (loopCounter == 2) {
                counter = 2;
                orderItemPrice.add(counter, cursor.getString(0));
                snackPrice.setText(orderItemPrice.get(counter));
                i += 1;
                orderItemQuantity.add(counter, cursor.getString(1));
                snackQuantity.setText(orderItemQuantity.get(counter));
            }

            loopCounter += 1;
            i += 1;
        }
        totalPrice.setText(totalprice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }
}
