package com.task.my.task.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.my.task.R;

import static com.task.my.task.MainActivity.day;
import static com.task.my.task.MainActivity.month;
import static com.task.my.task.MainActivity.year;


//TODO: Remove

public class XFragmentText extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.text_dialog, container, false);

        TextView tv = (TextView) view.findViewById(R.id.text_title);
        tv.setText(day + "/" + month + "/" + year);

        return view;
    }
}
