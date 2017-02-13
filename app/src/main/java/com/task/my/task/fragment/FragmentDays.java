package com.task.my.task.fragment;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.task.my.task.R;
import com.task.my.task.database.DBOpenHelper;
import com.task.my.task.database.TableFields;

import static com.task.my.task.MainActivity.day;
import static com.task.my.task.MainActivity.year;

public class FragmentDays extends Fragment implements View.OnClickListener {

    private DaysInterface mDays;
    private int daysList[];
    private int month;
    public static final String MONTH_KEY = "Month";


    public interface DaysInterface {

        void daysDone();
    }

    private void initializeDays() {
        int position, currentDays, previousDays;
        int k = 0;
        daysList = new int[42];

        if (getArguments() != null) {
            month = getArguments().getInt(MONTH_KEY);

            position = dayCode(month);
            currentDays = days(month);
            previousDays = days(month - 1);

            for (int i = previousDays - position + 1; i <= previousDays; i++, k++)
                daysList[k] = i;

            for (int i = 1; i <= currentDays; i++, k++)
                daysList[k] = i;

            for (int i = 1; (k + 1) % 7 != 0; i++, k++)
                daysList[k] = i;

            int num = daysList[k - 1] > 27 ? 1 : daysList[k - 1] + 1;

            while (k < 42) {
                daysList[k++] = num++;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initializeDays();

        View view = inflater.inflate(R.layout.fragment_days, container, false);

        String[] dayName = {"S", "M", "T", "W", "T", "F", "S"};

        TableRow row = (TableRow) view.findViewById(R.id.day_name);

        for (int i = 0; i < 7; i++) {

            TextView tv = (TextView) View.inflate(getActivity(), R.layout.table_cell, null);
            tv.setText(dayName[i]);
            row.addView(tv);

        }

//        GridView gv = (GridView) view.findViewById(R.id.days_grid);
//        DaysAdapter adapter = new DaysAdapter(getActivity(), daysList, month);

//
//        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
//        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), daysList, month, getFragmentManager());
//
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7, GridLayoutManager.VERTICAL, false);
//        rv.setLayoutManager(layoutManager);
//        rv.setAdapter(adapter);


//        gv.setAdapter(adapter);
//
//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView tv = (TextView) view;
//                day = Integer.parseInt(tv.getText().toString());
//                mDays.daysDone();
//            }
//        });

        addDays(view);


        return view;
    }

    private void addDays(View view) {

        TableLayout layout = (TableLayout) view.findViewById(R.id.month_table);
        TableRow row;
        TextView tv;

        int current, count, rowNum, position;
        DBOpenHelper helper = new DBOpenHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();

        for (int i = 0; i < 6; i++) {

            row = (TableRow) View.inflate(getActivity(), R.layout.table_row, null);

            for (int j = 0; j < 7; j++) {

                position = i * 6 + i + j;
                current = daysList[position];


                tv = (TextView) View.inflate(getActivity(), R.layout.table_cell, null);
                tv.setText(String.valueOf(current));

                if (position - current < 6 && position - current > -2) {

                    tv.setOnClickListener(this);

                    count = (int) DatabaseUtils.queryNumEntries(db, TableFields.EventEntry.TABLE_NAME,
                            TableFields.EventEntry.EVENT_CREATED + " = ? and " + TableFields.EventEntry.EVENT_CHECKED + " = " + TableFields.EventEntry.CHECKED,
                            new String[]{year + "-" + month + "-" + current});
                    rowNum = (int) DatabaseUtils.queryNumEntries(db, TableFields.EventEntry.TABLE_NAME,
                            TableFields.EventEntry.EVENT_CREATED + " = ?",
                            new String[]{year + "-" + month + "-" + current});


                    if (count >= rowNum/2 + 1) {
                        tv.setBackgroundResource(R.drawable.ic_flag_win);

                    } else if (rowNum > 0) {
                        tv.setBackgroundResource(R.drawable.ic_flag_loss);
                    }


                } else {
                    tv.setTextColor(Color.WHITE);
                }
                row.addView(tv);
            }
            layout.addView(row);
        }



        db.close();


    }

    @Override
    public void onClick(View view) {

        TextView tv = (TextView) view;
        day = Integer.parseInt(tv.getText().toString());
        mDays.daysDone();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDays = (DaysInterface) context;
    }


    private byte yearCode(long year) {
        long lsbs, msbs, corrector, leaps;
        lsbs = year % 100;
        msbs = year / 100;
        leaps = lsbs / 4;
        if (msbs % 4 == 0) corrector = 0;
        else if (msbs % 4 == 1) corrector = 5;
        else if (msbs % 4 == 2) corrector = 3;
        else corrector = 1;
        return (byte) ((lsbs + leaps + corrector) % 7);
    }


    private int days(int m) {
        if (m == 4 || m == 6 || m == 9 || m == 11)
            return 30;
        else if (m == 2) {
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
                return 29;
            else return 28;
        } else return 31;
    }

    private int monthCode(int i) {
        switch (i) {
            case 1:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    return 5;
                else return 6;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    return 1;
                else return 2;
            case 3:
            case 11:
                return 2;
            case 4:
            case 7:
                return 5;
            case 5:
                return 0;
            case 6:
                return 3;
            case 8:
                return 1;
            case 9:
            case 12:
                return 4;
            case 10:
                return 6;
        }
        return 0;

    }

    private int dayCode(int i) {
        return (1 + monthCode(i) + yearCode(year)) % 7;
    }

}
