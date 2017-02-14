package com.task.my.task.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.task.my.task.MainActivity.monthList;

//TODO: Remove

public class XMonthAdapter extends BaseAdapter {

    private Context mContext;

    public XMonthAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return monthList.length;
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

        int pad = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mContext.getResources().getDisplayMetrics());

        if (convertView == null) {
            tv = new TextView(mContext);
            tv.setPadding(pad, pad, pad, pad);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tv.setTextColor(Color.BLACK);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv = (TextView) convertView;
        }

        tv.setText(monthList[position]);

        return tv;
    }
}