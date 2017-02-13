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
import android.widget.TextView;

import com.task.my.task.R;
import com.task.my.task.adapter.YearAdapter;

import static com.task.my.task.MainActivity.startYear;
import static com.task.my.task.MainActivity.year;
import static com.task.my.task.MainActivity.yearList;


public class FragmentYear extends Fragment {

    //TODO: Restore store state when undergoing different lifecycle

    private yInterface mYear;

    public interface yInterface {
        void yearDone();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setYearList(startYear, startYear + 20);

    }

    private void setYearList(int start, int end) {
        yearList.clear();
        for(int i = start; i < end; i++) {
            yearList.add(i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_year, container, false);

//        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.grid_text_year, yearList);

        GridView gv = (GridView) view.findViewById(R.id.year_grid);
        YearAdapter adapter = new YearAdapter(getActivity());

        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                year = Integer.parseInt(tv.getText().toString());
                mYear.yearDone();
            }
        });



        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mYear = (yInterface) context;
    }



}
