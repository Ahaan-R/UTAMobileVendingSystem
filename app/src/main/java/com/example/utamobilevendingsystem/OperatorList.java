package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.domain.UserDetails;

import java.util.ArrayList;

public class OperatorList extends Activity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String vehicleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);

        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        vehicleID = getIntent().getStringExtra("vehicleID");

        ArrayList<UserDetails> operatorList = new ArrayList<>();
        ListView operatorListView = (ListView) findViewById(R.id.lvOperatorList);

        String selectQuery = "select ud.first_name, ud.last_name, ud.user_id from user_details ud, user_creds uc where ud.user_id=uc.user_id and uc.role = \"Operator\";";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() > 0){
            c.moveToFirst();
            UserDetails user;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                user = new UserDetails();
                user.setfName(c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)));
                user.setlName(c.getString(c.getColumnIndex(Resources.USER_DETAILS_LNAME)));
                user.setUserId(c.getInt(c.getColumnIndex(Resources.USER_CREDS_USER_ID)));
                operatorList.add(user);
            }
        }

        OperatorListAdapter adapter = new OperatorListAdapter(this, R.layout.activity_operator_list_adapter, operatorList);
        operatorListView.setAdapter(adapter);

        if((getIntent().getStringExtra("callingActivity")).contains("ManagerHomeScreen")){
            operatorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv = view.findViewById(R.id.textViewOperatorID);
                    Intent intent = new Intent(OperatorList.this,OperatorDetails.class);
                    intent.putExtra("userID", tv.getText().toString());
                    startActivity(intent);
                }
            });
        } else {
            operatorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String userID = ((TextView)view.findViewById(R.id.textViewOperatorID)).getText().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Resources.VEHICLE_USER_ID, userID);
                    db.update(Resources.TABLE_VEHICLE,contentValues, "vehicle_id = ?", new String[] {vehicleID});

                    Intent output = new Intent();
                    output.putExtra("userName", ((TextView)view.findViewById(R.id.textViewOperatorName)).getText().toString());
                    setResult(RESULT_OK, output);
                    finish();
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }
}
