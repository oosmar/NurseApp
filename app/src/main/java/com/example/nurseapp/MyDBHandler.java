package com.example.nurseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.database.Cursor;

import java.util.HashMap;

public class MyDBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MY_DATABASE";
    // User table name
    public static final String TABLE_NAME = "patient";
    // User Table Columns names
    public static final String NAME = "patient_name";
    public static final String SURENAME = "patient_surname";
    public static final String BLOOD = "patient_blood_preasure";
    public static final String HEART = "patient_heart_rate";
    private final Context context;
    private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + NAME + " TEXT," + SURENAME
            + " TEXT," + BLOOD + " TEXT," + HEART + " TEXT, " + "PRIMARY KEY" + "("  +
            NAME + ","  + SURENAME + ")" + ")";
    private String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addemp(String name, String sureName, String blood, String heart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDBHandler.NAME, name);
        values.put(MyDBHandler.SURENAME, sureName);
        values.put(MyDBHandler.BLOOD, blood);
        values.put(MyDBHandler.HEART, heart);
        long status = db.insert(TABLE_NAME, null, values);
        if (status <= 0) {
            Toast.makeText(context, "Insertion Unsuccessful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insertion Successful", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteUser(String name, String sureName) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete by id
        long s = db.delete(TABLE_NAME, NAME + " = ?" + " AND " + SURENAME + " = ?",
                new String[]{name, sureName});
        if (s <= 0) {
            Toast.makeText(context, "Deletion Unsuccessful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deletion Successful", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public HashMap<String,String>  load(String name, String sureName) {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME  + " WHERE ";

      //  if(!name.isEmpty())
            query = query +  NAME + " like " + "'%" + name + "%'";

        if(!sureName.isEmpty())
            query = query +  " and " + SURENAME + " like " + "'%" + sureName + "%'";

        query = query + " LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        HashMap<String,String> map = new HashMap<String,String>();

        try {
            cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                map.put(cursor.getColumnName(0),cursor.getString(0));
                map.put(cursor.getColumnName(1),cursor.getString(1));
                map.put(cursor.getColumnName(2),cursor.getString(2));
                map.put(cursor.getColumnName(3),cursor.getString(3));
                System.getProperty("line.separator");
            }
            cursor.close();
        } catch (Exception e){
            System.console().printf("ddsgafhfdh");
        }

        db.close();
        return map;
    }

    public void edit(String name, String sureName, String blood, String heart) {
        String result = "";
        String query = "UPDATE " + TABLE_NAME + " SET " ;

        query = query + BLOOD + " = '" + blood + "',";
        query = query + HEART + " = '" + heart + "'";
        query = query + " WHERE " + NAME + " = '" + name +  "'";
        query = query + " AND " +  SURENAME + " = '" + sureName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            /*cursor = db.rawQuery(query, null);
            cursor.close();*/
            db.execSQL(query);
        } catch (Exception e){
            System.console().printf("ddsgafhfdh");
        }

        db.close();
    }
}