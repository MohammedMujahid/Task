package com.task.my.task.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.task.my.task.R;
import com.task.my.task.adapter.XMonthAdapter;

import static com.task.my.task.MainActivity.month;


//TODO: Remove

public class XFragmentMonth extends Fragment {

    public MonthInterface mMonth;

    public interface MonthInterface {
        void monthDone();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_month, container, false);



        GridView gv = (GridView) view.findViewById(R.id.month_grid);
        XMonthAdapter adapter = new XMonthAdapter(getActivity());
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                month = i + 1;
                mMonth.monthDone();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMonth = (MonthInterface) context;
    }

}
