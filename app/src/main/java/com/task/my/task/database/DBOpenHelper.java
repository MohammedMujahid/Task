package com.task.my.task.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;


    private static final String DAYS_TABLE_CREATE = "CREATE TABLE " + TableFields.DateEntry.TABLE_NAME + "( "
            + TableFields.DateEntry._ID + " INTEGER PRIMARY KEY, "
            + TableFields.DateEntry.DAYS_DATE + " TEXT default CURRENT_DATE, "
            + TableFields.DateEntry.DAYS_WIN + " INTEGER default 0" + " )";


    private static final String EVENT_TABLE_CREATE = "CREATE TABLE " + TableFields.EventEntry.TABLE_NAME + "( "
            + TableFields.EventEntry._ID + " INTEGER PRIMARY KEY, "
            + TableFields.EventEntry.EVENT_CREATED + " TEXT default CURRENT_DATE, "
            + TableFields.EventEntry.EVENT_TEXT + " TEXT, "
            + TableFields.EventEntry.EVENT_CHECKED + " INTEGER default 0" + ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DAYS_TABLE_CREATE);
        sqLiteDatabase.execSQL(EVENT_TABLE_CREATE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableFields.DateEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableFields.EventEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }


}
