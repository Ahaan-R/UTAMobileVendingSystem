package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OperatorList extends AppCompatActivity {

    private Button VIEW_OPTR_DETAILS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);

        VIEW_OPTR_DETAILS=(Button) findViewById(R.id.optr_schedule_id);
        VIEW_OPTR_DETAILS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openoptrdeatils();
            }
        });

    }

    public void openoptrdeatils(){
        Intent intent =new Intent(this, OperatorDetails.class);
        startActivity(intent );
    }
}
