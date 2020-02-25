package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class OperatorScheduleList extends AppCompatActivity {

    private Button VIEW_OPTR_SCHEDULE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_schedule_list);

        VIEW_OPTR_SCHEDULE=(Button) findViewById(R.id.optr_schedule_id);
        VIEW_OPTR_SCHEDULE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openoptrdeatils();
            }
        });

    }

    public void openoptrdeatils(){
        Intent intent =new Intent(this, OperatorSchedule.class);
        startActivity(intent );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }
}
