package com.task.my.task.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//TODO: Remove

public class XDaysNameAdapter extends BaseAdapter {

    private Context mContext;
    private String[] dayName;

    public XDaysNameAdapter(Context mContext, String[] dayName) {
        this.mContext = mContext;
        this.dayName = dayName;
    }

    @Override
    public int getCount() {
        return dayName.length;
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


        tv.setText(dayName[position]);

        return tv;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
