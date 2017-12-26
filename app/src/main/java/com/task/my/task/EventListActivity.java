package com.task.my.task;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.task.my.task.adapter.EventsAdapter;
import com.task.my.task.database.TableFields;
import com.task.my.task.database.TextProvider;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> textList = new ArrayList<>();
        List<Integer> winList = new ArrayList<>();

        Cursor cursor = getContentResolver().query(TextProvider.URI_CONTENT_DAYS, TableFields.DateEntry.DAYS_COLUMNS, null, null,
                TableFields.DateEntry.DAYS_DATE);

        if (cursor.moveToFirst()) {
            do {

                textList.add(cursor.getString(cursor.getColumnIndex(TableFields.DateEntry.DAYS_DATE)));
                winList.add(cursor.getInt(cursor.getColumnIndex(TableFields.DateEntry.DAYS_WIN)));

            }while(cursor.moveToNext());
        }

        cursor.close();

        ListView lv = (ListView) findViewById(R.id.event_list);

        EventsAdapter adapter = new EventsAdapter(this, textList, winList);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(EventListActivity.this, "You Clicked on " + i, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, TextProvider.URI_CONTENT_DAYS, TableFields.DateEntry.DAYS_COLUMNS, null, null, TableFields.DateEntry.DAYS_DATE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
