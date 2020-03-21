package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
import com.example.utamobilevendingsystem.domain.Status;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderConfirmation extends AppCompatActivity {
    int userId;
    TextView swichQty,swichPrice,drinksQty,drinksPrice,snacksQty,snacksPrice,total;
    int swichQuantity,drinksQuantity,snacksQuantity;
    String switchAmt,drinksAmt,snacksAmt;
    double totalAmt;
    private static DecimalFormat df = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        Intent newint = getIntent();
        userId=newint.getIntExtra("userid",0);
        totalAmt=newint.getDoubleExtra("totalPrice",0.0);
        swichQty = findViewById(R.id.swichQty);
        swichPrice = findViewById(R.id.swichPrice);
        drinksQty = findViewById(R.id.drinksQty);
        drinksPrice = findViewById(R.id.drinksPrice);
        snacksQty = findViewById(R.id.snacksQty);
        snacksPrice = findViewById(R.id.snacksPrice);
        total = findViewById(R.id.totalPrice);
        total.setText("$"+ df.format(totalAmt));
        fetchCurrOrder();
    }

    private void fetchCurrOrder() {
        SharedPreferences preferences = getSharedPreferences("userCart", MODE_PRIVATE);
        swichQuantity=preferences.getInt("swichQty",0);
        drinksQuantity=preferences.getInt("drinksQty",0);
        snacksQuantity=preferences.getInt("snacksQty",0);
        switchAmt=preferences.getString("swichPrice","");
        drinksAmt=preferences.getString("drinksPrice","");
        snacksAmt=preferences.getString("snacksPrice","");
        swichQty.setText(String.valueOf(swichQuantity));
        swichPrice.setText(switchAmt);
        drinksQty.setText(String.valueOf(drinksQuantity));
        drinksPrice.setText(drinksAmt);
        snacksQty.setText(String.valueOf(snacksQuantity));
        snacksPrice.setText(snacksAmt);
        confirmOrder();
    }

    private void confirmOrder() {
        SQLiteDatabase db = DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        String lastOrder = "SELECT "+Resources.ORDER_ID +" FROM "+Resources.TABLE_ORDER;
        Cursor c = db.rawQuery(lastOrder, null);
        int order_id=1;
        if (c.getCount() > 0) {
            c.moveToLast();
            order_id = c.getInt(c.getColumnIndex(Resources.ORDER_ID)) + 1;
        }
        ContentValues order = new ContentValues();
        order.put("order_id",order_id);
        order.put("order_item_id",1);
        order.put("order_item_quantity",swichQuantity);
        order.put("order_item_price",switchAmt);
        order.put("order_status_id",Status.CONFIRMED.getDescription());
        db.insert(Resources.TABLE_ORDER,null, order);
        order.put("order_id",order_id);
        order.put("order_item_id",2);
        order.put("order_item_quantity",drinksQuantity);
        order.put("order_item_price",drinksAmt);
        order.put("order_status_id",Status.CONFIRMED.getDescription());
        db.insert(Resources.TABLE_ORDER,null, order);
        order.put("order_id",order_id);
        order.put("order_item_id",3);
        order.put("order_item_quantity",snacksQuantity);
        order.put("order_item_price",snacksAmt);
        order.put("order_status_id",Status.CONFIRMED.getDescription());
        db.insert(Resources.TABLE_ORDER,null, order);

        ContentValues userOrders = new ContentValues();
        userOrders.put("user_id",userId);
        userOrders.put("order_id",order_id);
        db.insert(Resources.TABLE_USER_ORDER,null, userOrders);

        String deleteCart = "DELETE FROM "+Resources.TABLE_CART+" WHERE "+Resources.CART_ID +" = "+userId ;
        db.execSQL(deleteCart);
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
