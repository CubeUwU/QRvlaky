package com.example.qrvlaky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "train_database";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_NAME = "trains";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CISLO = "cislo";
    public static final String COLUMN_POZNAMKA = "poznamka";
    public static final String COLUMN_DATUM = "datum";
    public static final String COLUMN_SORT = "sort";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_CISLO + " TEXT," +
                COLUMN_POZNAMKA + " TEXT," +
                COLUMN_DATUM + " TEXT," +
                COLUMN_SORT + " TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, Note: All data will be deleted!
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

}