package com.task.my.task.fragment;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.task.my.task.R;
import com.task.my.task.database.QueryHandler;
import com.task.my.task.database.TableFields;
import com.task.my.task.database.TextProvider;

import java.util.ArrayList;
import java.util.List;

import static com.task.my.task.MainActivity.day;
import static com.task.my.task.MainActivity.month;
import static com.task.my.task.MainActivity.year;


public class TextDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Boolean rowExists;
    private String selectedDate;
    private dialogInterface mDialog;
    private int position;
    private View view;
    private LayoutInflater inflater;
    private LinearLayout listParent;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.text_dialog, null);
        listParent = (LinearLayout) view.findViewById(R.id.linearLayout);

        rowExists = false;
        position = 0;

        //Standard Date format in SQLite
        selectedDate = year + "-" + month + "-" + day;

        getLoaderManager().initLoader(0, null, this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);


        TextView tv = (TextView) view.findViewById(R.id.text_title);
        tv.setText(day + "/" + month + "/" + year);



        Button addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener();
            }
        });

        final QueryHandler handler = new QueryHandler(getContext().getContentResolver());

        builder
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        List<ContentValues> cList = new ArrayList<>();

                        ContentValues c;
                        LinearLayout child;
                        EditText eText;
                        CheckBox cBox;
                        int listSize, count = 0;

                        for (int j = 0; j < position; j++) {

                            child = (LinearLayout) listParent.findViewById(j);
                            eText = (EditText) child.findViewById(R.id.editText);
                            cBox = (CheckBox) child.findViewById(R.id.checkBox);

                            c = new ContentValues();    //Check Size TODO:

                            if (!TextUtils.isEmpty(eText.getText().toString())) {
                                c.put(TableFields.EventEntry.EVENT_TEXT, eText.getText().toString());
                                c.put(TableFields.EventEntry.EVENT_CREATED, selectedDate);

                                if (cBox.isChecked()) {
                                    count++;
                                    c.put(TableFields.EventEntry.EVENT_CHECKED, TableFields.EventEntry.CHECKED);
                                }
                                cList.add(c);
                            }

                        }

                        listSize = cList.size();

                        if (rowExists) {

//                            getContext().getContentResolver().delete(TextProvider.URI_CONTENT_EVENT, TableFields.EventEntry.EVENT_CREATED + "=?", new String[] {selectedDate});
                            handler.startDelete(1, null, TextProvider.URI_CONTENT_EVENT, TableFields.EventEntry.EVENT_CREATED + "=?", new String[]{selectedDate});

                        }


                        for (int j = 0; j < listSize; j++) {
//                            getContext().getContentResolver().insert(TextProvider.URI_CONTENT_EVENT, cList.get(j));
                            handler.startInsert(0, null, TextProvider.URI_CONTENT_EVENT, cList.get(j));
                        }

                        c = new ContentValues(2);
                        c.put(TableFields.DateEntry.DAYS_DATE, selectedDate);

                        if (count > listSize/2 + 1) {
                            c.put(TableFields.DateEntry.DAYS_WIN, TableFields.DateEntry.CHECKED);
                        }

                        //TODO: Insert Record to Table

                        mDialog.restartPager();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (rowExists) {

//                            getContext().getContentResolver().delete(TextProvider.URI_CONTENT_EVENT, TableFields.EventEntry.EVENT_CREATED + "=?", new String[] {selectedDate});
                            handler.startDelete(1, null, TextProvider.URI_CONTENT_EVENT, TableFields.EventEntry.EVENT_CREATED + "=?", new String[]{selectedDate});

                            //TODO: Delete Record from Table

                            mDialog.restartPager();

                        }
                    }
                });



        return builder.create();

    }



    private void buttonListener() {

        LinearLayout child = (LinearLayout) listParent.findViewById(position - 1);
        EditText et = (EditText) child.findViewById(R.id.editText);

        if (!TextUtils.isEmpty(et.getText().toString())) {
            addToList(0, null, false);

            final ScrollView scroll = (ScrollView) view.findViewById(R.id.eventScroll);

            scroll.post(new Runnable() {
                @Override
                public void run() {
                     scroll.scrollTo(0, scroll.getBottom());
                }
            });
        }
    }


    private void addToList(int check, String text, Boolean hasText) {

        LinearLayout listLayout = (LinearLayout) inflater.inflate(R.layout.my_list, null);
        listLayout.setId(position);

        EditText et = (EditText) listLayout.findViewById(R.id.editText);
        if (hasText) {
            et.setText(text);
        } else {
            et.setHint("Task " + (position + 1));
        }

        if (check == TableFields.EventEntry.CHECKED) {

            CheckBox box = (CheckBox) listLayout.findViewById(R.id.checkBox);
            box.setChecked(true);

        }
        listParent.addView(listLayout, position++);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] date = {selectedDate};
        return new CursorLoader(getActivity(),
                TextProvider.URI_CONTENT_EVENT,
                TableFields.EventEntry.EVENT_COLUMNS,
                TableFields.EventEntry.EVENT_CREATED + "=?",
                date, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()) {
            rowExists = true;

            do {
                addToList(data.getInt(data.getColumnIndex(TableFields.EventEntry.EVENT_CHECKED)),
                        data.getString(data.getColumnIndex(TableFields.EventEntry.EVENT_TEXT)),
                        true);
            } while (data.moveToNext());
        }

        addToList(0, null, false);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface dialogInterface {
        void restartPager();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDialog = (dialogInterface) context;
    }


}
