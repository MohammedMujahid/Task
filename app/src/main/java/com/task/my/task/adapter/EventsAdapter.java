package com.task.my.task.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.my.task.R;

import java.util.List;

/**
 * Created by Jameel on 15-Feb-17.
 */

public class EventsAdapter extends ArrayAdapter {

    private List<String> eventList;
    private List<Integer> winList;
    private LayoutInflater mInflater;

    public EventsAdapter(Context context, List<String> list, List<Integer> wList) {

        super(context, R.layout.event_list, list);

        eventList = list;
        winList = wList;
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView tv;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.event_list, parent, false);

        }

        tv = (TextView) convertView.findViewById(R.id.event_textView);
        tv.setText(eventList.get(position));

        ImageView iv = (ImageView) convertView.findViewById(R.id.win);

        if (winList.get(position) == 1) {
            iv.setBackgroundResource(R.drawable.ic_flag_win);
        } else {
            iv.setBackgroundResource(R.drawable.ic_flag_loss);
        }

        return convertView;
    }
}
