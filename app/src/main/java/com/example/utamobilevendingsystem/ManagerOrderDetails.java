package com.example.utamobilevendingsystem;

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
import android.view.MenuItem;
import android.widget.TextView;

import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.users.UserOrderDetails;
import com.example.utamobilevendingsystem.users.UserOrderDetailsAdapter;

import java.util.ArrayList;

public class ManagerOrderDetails extends AppCompatActivity {

    ArrayList<String> orderID = new ArrayList<>();
    ArrayList<String> orderItemID = new ArrayList<>();
    ArrayList<String> orderItemQuantity = new ArrayList<>();
    ArrayList<String> orderItemPrice = new ArrayList<>();
    ArrayList<String> orderStatusID = new ArrayList<>();
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    String TAG = "ManagerOrderDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order_details);
        Log.i(TAG, "ManagerOrderDetails: onCreate");

        dbHelper = new DatabaseHelper(this);
        db= dbHelper.getReadableDatabase();
        getData();
        initRecyclerView();
    }

    private void getData() {
        Log.i(TAG, "ManagerOrderDetails: getData");

        Cursor cursor = db.rawQuery("select order_id, sum(order_item_quantity), sum(order_item_price), order_status_id from orders group by order_id", null);
            if (cursor.getCount() >= 1) {
                int i = 0;
                while (cursor.moveToNext()) {
                    orderID.add(i, cursor.getString(0));
                    orderItemQuantity.add(i, cursor.getString(1));
                    orderItemPrice.add(i, cursor.getString(2));
                    orderStatusID.add(i, cursor.getString(3));
                    i += 1;
                }
            }
    }

    private void initRecyclerView() {
        Log.i(TAG, "ManagerOrderDetails: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerViewManager);
        UserOrderDetailsAdapter adapter = new UserOrderDetailsAdapter(ManagerOrderDetails.this, orderID , orderItemQuantity, orderItemPrice, orderStatusID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        menu.findItem(R.id.app_bar_search).setVisible(true);
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
                //startSettings();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_home:
                managerHome();
                return true;
            case R.id.change_password:
                changePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void managerHome() {
        Intent managerHome = new Intent(ManagerOrderDetails.this, ManagerHomeScreen.class);
        startActivity(managerHome);
    }

    private void viewOrders() {
        Intent viewOrders = new Intent(ManagerOrderDetails.this, ManagerOrderDetails.class);
        startActivity(viewOrders);
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent logout = new Intent(ManagerOrderDetails.this, LoginActivity.class);
        startActivity(logout);
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(ManagerOrderDetails.this, ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void viewLocationList(){
        Intent changePasswordIntent = new Intent(ManagerOrderDetails.this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }
}