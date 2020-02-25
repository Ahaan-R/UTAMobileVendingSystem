package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class OrderDetails extends AppCompatActivity {
    Button placeOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        placeOrder = findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfooddetails();

            }
        });
    }
    public void openfooddetails(){
        Intent intent =new Intent(this, OrderSummary.class);
        startActivity(intent );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }
}

//    Intent myintent = new Intent(OrderDetails.this,OrderSummary.class);
//    startActivity(myintent);
////////
//    public void onClick(View v) {
//        openoptrdeatils();
//    }
//});
//
//        }
//
//public void openoptrdeatils(){
//        Intent intent =new Intent(this, OperatorDetails.class);
//        startActivity(intent );
//        }