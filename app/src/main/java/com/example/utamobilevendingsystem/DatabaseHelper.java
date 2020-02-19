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
    private static final String DATABASE_NAME = "utamvs";
    private static final String TABLE_USER_CREDS = "user_creds";
    private static final int DATABASE_VERSION = 1;

    //USER_CREDENTIALS COLUMN NAMES
    private static final String USER_CRED_USERID = "userID";
    private static final String USER_CRED_USERNAME = "username";
    private static final String USER_CRED_PASSWORD = "password";
    private static final String USER_CRED_RECOVERY = "recovery";

    // USER_CREDENTIALS table create statement
    private static final String CREATE_TABLE_USER_CREDS = "CREATE TABLE "
            + TABLE_USER_CREDS + "(" + USER_CRED_USERID + " INTEGER PRIMARY KEY," + USER_CRED_USERNAME
            + " TEXT," + USER_CRED_PASSWORD + " TEXT," + USER_CRED_RECOVERY
            + " INTEGER" + ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_CREDS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_CREDS);
        //create new tables
        onCreate(db);

    }

    public long createUserCredentials(User_Credentials user_credentials, int[] userIDs) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_CRED_USERID, user_credentials.getUserID());
        values.put(USER_CRED_USERNAME, user_credentials.getUserName());
        values.put(USER_CRED_PASSWORD, user_credentials.getPassword());
        values.put(USER_CRED_RECOVERY, user_credentials.getRecoverCode());

        // insert row
        long todo_id = db.insert(TABLE_USER_CREDS, null, values);

        return todo_id;
    }
    public User_Credentials getUserCredentials(long todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER_CREDS + " WHERE "
                + USER_CRED_USERID + " = " + todo_id;

        Log.e("DatabaseHelper", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User_Credentials user_credentials = new User_Credentials();
        user_credentials.setUserID(c.getInt(c.getColumnIndex(USER_CRED_USERID)));
        user_credentials.setUserName((c.getString(c.getColumnIndex(USER_CRED_USERNAME))));
        user_credentials.setPassword(c.getString(c.getColumnIndex(USER_CRED_PASSWORD)));
        user_credentials.setRecoverCode(c.getInt(c.getColumnIndex(USER_CRED_RECOVERY)));

        return user_credentials;
    }
    public List<User_Credentials> getAllUserCredentials() {
        List<User_Credentials> todos = new ArrayList<User_Credentials>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER_CREDS;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User_Credentials user_credentials = new User_Credentials();
                user_credentials.setUserID(c.getInt(c.getColumnIndex(USER_CRED_USERID)));
                user_credentials.setUserName((c.getString(c.getColumnIndex(USER_CRED_USERNAME))));
                user_credentials.setPassword(c.getString(c.getColumnIndex(USER_CRED_PASSWORD)));
                user_credentials.setRecoverCode(c.getInt(c.getColumnIndex(USER_CRED_RECOVERY)));

                // adding to todo list
                todos.add(user_credentials);
            } while (c.moveToNext());
        }

        return todos;
    }
}

