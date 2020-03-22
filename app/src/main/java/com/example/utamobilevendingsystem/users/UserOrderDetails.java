package com.example.utamobilevendingsystem.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.utamobilevendingsystem.DatabaseHelper;
import com.example.utamobilevendingsystem.OrderSummary;
import com.example.utamobilevendingsystem.R;

import java.util.ArrayList;

public class UserOrderDetails extends AppCompatActivity {

    ArrayList<String> orderIdFromCart = new ArrayList<>();
    ArrayList<String> userIdFromCart = new ArrayList<>();
    ArrayList<String> orderID = new ArrayList<>();
    ArrayList<String> orderItemID = new ArrayList<>();
    ArrayList<String> orderItemQuantity = new ArrayList<>();
    ArrayList<String> orderItemPrice = new ArrayList<>();
    ArrayList<String> orderStatusID = new ArrayList<>();
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    String TAG = "UserOrderDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        dbHelper = new DatabaseHelper(this);
        db= dbHelper.getReadableDatabase();
        getData();
        initRecyclerView();

    }

    private void getData() {
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        int uid =prefs.getInt("userid",0);
        Cursor getOrders = db.rawQuery("SELECT * FROM user_order where user_id="+uid,null);
        if (getOrders.getCount() != 0){
            int i = 0, j=0;
            while(getOrders.moveToNext()){
                j = 0;
                userIdFromCart.add(i,getOrders.getString(j));
                j += 1;
                orderIdFromCart.add(i,getOrders.getString(j));
            }
        }
        int numberOfOrders = orderIdFromCart.size();
        int orderNumber = 0;
        while (numberOfOrders != 0) {
            Cursor cursor = db.rawQuery("SELECT order_id, order_item_id, sum(order_item_quantity), sum(order_item_price), order_status_id FROM orders where order_id=" + orderIdFromCart.get(orderNumber), null);
            if (cursor.getCount() != 0) {
                StringBuffer buffer = new StringBuffer();
                int i = 0, j = 0;
                while (cursor.moveToNext()) {
                    j = 0;
                    orderID.add(i, cursor.getString(j));
                    j += 1;
                    orderItemID.add(i, cursor.getString(j));
                    j += 1;
                    orderItemQuantity.add(i, cursor.getString(j));
                    j += 1;
                    orderItemPrice.add(i, cursor.getString(j));
                    j += 1;
                    orderStatusID.add(i, cursor.getString(j));
                    i += 1;
                }
            }
            orderNumber+=1;
            numberOfOrders-=1;
        }

    }

    private void initRecyclerView() {
        Log.d(TAG, "UserOrderDetails: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        UserOrderDetailsAdapter adapter = new UserOrderDetailsAdapter(UserOrderDetails.this, orderID, orderItemID , orderItemQuantity, orderItemPrice, orderStatusID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }

}

