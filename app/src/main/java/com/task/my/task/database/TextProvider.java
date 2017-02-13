package com.task.my.task.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;



public class TextProvider extends ContentProvider {

    private static final String AUTHORITY = "com.task.my.task.textprovider";
    private static final String BASE_PATH_DAYS = TableFields.DateEntry.TABLE_NAME;
    private static final String BASE_PATH_EVENT = TableFields.EventEntry.TABLE_NAME;
    public static final Uri URI_CONTENT_DAYS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DAYS);
    public static final Uri URI_CONTENT_EVENT = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_EVENT);

    //Constants to perform operation
    private static final int DAYS = 1;
    private static final int DAYS_ID = 2;
    private static final int EVENT = 3;
    private static final int EVENT_ID = 4;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH_DAYS, DAYS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_DAYS + "/#", DAYS_ID);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_EVENT, EVENT);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_EVENT + "/#", EVENT_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        //TODO: Change

        switch (uriMatcher.match(uri)) {
            case DAYS:
                return database.query(TableFields.DateEntry.TABLE_NAME, TableFields.DateEntry.DAYS_COLUMNS, s, strings1, null, null, null);
            case EVENT:
                return database.query(TableFields.EventEntry.TABLE_NAME, TableFields.EventEntry.EVENT_COLUMNS, s, strings1, null, null, null);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        //TODO: Change
        long id;
        switch (uriMatcher.match(uri)) {
            case DAYS:
                id = database.insert(TableFields.DateEntry.TABLE_NAME, null, contentValues);
                return Uri.parse(BASE_PATH_DAYS + "/" + id);
            case EVENT:
                id = database.insert(TableFields.EventEntry.TABLE_NAME, null, contentValues);
                return Uri.parse(BASE_PATH_EVENT + "/" + id);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        //TODO: Change
        switch (uriMatcher.match(uri)) {
            case DAYS:
                return database.delete(TableFields.DateEntry.TABLE_NAME, s, strings);
            case EVENT:
                return database.delete(TableFields.EventEntry.TABLE_NAME, s, strings);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        //TODO: Change

        switch (uriMatcher.match(uri)) {
            case DAYS:
                return database.update(TableFields.DateEntry.TABLE_NAME, contentValues, s, strings);
            case EVENT:
                return database.update(TableFields.EventEntry.TABLE_NAME, contentValues, s, strings);
        }
        return 0;
    }
}
