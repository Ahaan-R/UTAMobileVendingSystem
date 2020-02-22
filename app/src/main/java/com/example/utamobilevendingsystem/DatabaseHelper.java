package com.example.utamobilevendingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.utamobilevendingsystem.Tables.User_Credentials;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Resources.DATABASE_NAME, null, Resources.DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DatabaseHelper", "Database successfully created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //onUpgrade(db, 1, 2);
        Log.i("DatabaseHelper","Oncreate()");
        db.execSQL(Resources.CREATE_TABLE_USER_CREDENTIALS);
        db.execSQL(Resources.CREATE_TABLE_USER_DETAILS);
        db.execSQL(Resources.CREATE_TABLE_ITEM);
        db.execSQL(Resources.CREATE_TABLE_STATUS);
        db.execSQL(Resources.CREATE_TABLE_LOCATION);
        db.execSQL(Resources.CREATE_TABLE_VEHICLE);
        db.execSQL(Resources.CREATE_TABLE_VEHICLE_INVENTORY);
        db.execSQL(Resources.CREATE_TABLE_CART);
        db.execSQL(Resources.CREATE_TABLE_ORDER);
        db.execSQL(Resources.CREATE_TABLE_USER_ORDER);
        db.execSQL(Resources.CREATE_TABLE_OPERATOR_VEHICLE);
        db.execSQL(Resources.CREATE_TABLE_PAYMENTS);
        db.execSQL(Resources.CREATE_TABLE_USER_CART);
        Log.i("DatabaseHelper","Table creation successful)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        Log.i("DatabaseHelper","onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_USER_CART);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_PAYMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_OPERATOR_VEHICLE);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_USER_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_VEHICLE_INVENTORY);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_VEHICLE);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_USER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + Resources.TABLE_USER_CREDS);
        Log.i("DatabaseHelper","Table Drop successful)");
        //create new tables
        onCreate(db);
    }
}

