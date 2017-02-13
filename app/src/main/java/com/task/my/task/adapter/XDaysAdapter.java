package com.task.my.task.adapter;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.task.my.task.database.DBOpenHelper;
import com.task.my.task.R;
import com.task.my.task.database.TableFields;

import java.util.List;

import static com.task.my.task.MainActivity.year;

//TODO: Remove

public class XDaysAdapter extends BaseAdapter {

    private Context mContext;
    private Boolean active;
    private List<Integer> daysList;
    private int month;

    public XDaysAdapter(Context mContext, List<Integer> daysList, int month) {
        this.mContext = mContext;
        this.daysList = daysList;
        this.month = month;
        active = false;
    }

    @Override
    public int getCount() {
        return daysList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView tv;

        //TODO: Check
        int current = daysList.get(position);



        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());

        if (convertView == null) {
            tv = new TextView(mContext);
            tv.setPadding(padding, padding, padding, padding);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tv.setTextColor(Color.BLACK);

            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv = (TextView) convertView;
        }


        tv.setText(Integer.toString(current));

        if (current == 1 && !active) {
            active = true;
        } else if (current == 1) {
            active = false;
        }

        //Redundant
        if (position == 0 && current == 1)
            active = true;

        if (!active) {
            tv.setTextColor(Color.WHITE);
        }


        //TODO: Eliminate Bad Practices
        if (active) {

            DBOpenHelper helper = new DBOpenHelper(mContext);
            SQLiteDatabase db = helper.getReadableDatabase();

            int count = (int) DatabaseUtils.queryNumEntries(db, TableFields.EventEntry.TABLE_NAME,
                    TableFields.EventEntry.EVENT_CREATED + " = ? and " + TableFields.EventEntry.EVENT_CHECKED + " = 1",
                    new String[]{year + "-" + month + "-" + current});
//        Check for row existence
            int rowExists = (int) DatabaseUtils.queryNumEntries(db, TableFields.EventEntry.TABLE_NAME,
                    TableFields.EventEntry.EVENT_CREATED + " = ?",
                    new String[]{year + "-" + month + "-" + current});


            if (count > 3) {
                tv.setBackgroundResource(R.drawable.ic_flag_win);

            } else if (rowExists > 0) {
                tv.setBackgroundResource(R.drawable.ic_flag_loss);
            }

            db.close();
        }

        return tv;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {

        return (position - daysList.get(position) < 6 && position - daysList.get(position) > -2);

    }
}
