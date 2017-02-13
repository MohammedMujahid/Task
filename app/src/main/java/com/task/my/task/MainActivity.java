package com.task.my.task;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.task.my.task.fragment.DaysPagerAdapter;
import com.task.my.task.fragment.FragmentDays;
import com.task.my.task.fragment.FragmentYear;
import com.task.my.task.fragment.TextDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements
        FragmentYear.yInterface, FragmentDays.DaysInterface, TextDialog.dialogInterface {

    public static List<Integer> yearList;

    public static List<String> monthList;
    public static int year, month, day;
    private static Calendar cal;
    public static int startYear;

    //TODO: Create Constants
    private byte state;
    private TextView header;
    private FragmentManager manager;
    private List<FragmentDays> fragmentList;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getWindow().setBackgroundDrawableResource(R.drawable.background);

        yearList = new ArrayList<>();
        monthList = new ArrayList<>(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
        header = (TextView) findViewById(R.id.header);
        cal = Calendar.getInstance();
        startYear = (cal.get(Calendar.YEAR) / 20) * 20 + 1;
        state = 1;
        year = 0;

        if (savedInstanceState != null) {
            startYear = savedInstanceState.getInt("startYear");
            state = savedInstanceState.getByte("state");
            header.setText(savedInstanceState.getString("headerText"));
            year = savedInstanceState.getInt("year");
            findViewById(R.id.tabLayout).setVisibility(View.GONE);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        manager = getSupportFragmentManager();


        showFragment();


    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putByte("state", state);
        outState.putString("headerText", header.getText().toString());
        outState.putInt("startYear", startYear);
        outState.putInt("year", year);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu) {

            Toast.makeText(this, "Has no action", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFragment() {

        if (state == 1) {

            if (year == 0) {
                year = cal.get(Calendar.YEAR);
            }
            month = cal.get(Calendar.MONTH) + 1;
            day = cal.get(Calendar.DATE);

            header.setText(String.valueOf(year));

            addPager();

        }
    }

    private void initializeList() {

        fragmentList = new ArrayList<>(12);

        Bundle b;
        FragmentDays fragment;

        for (int i = 0; i < 12; i++) {

            b = new Bundle(1);
            fragment = new FragmentDays();
            b.putInt(FragmentDays.MONTH_KEY, i + 1);
            fragment.setArguments(b);
            fragmentList.add(fragment);

        }

    }

    private void addPager() {

        initializeList();

        DaysPagerAdapter adapter = new DaysPagerAdapter(getSupportFragmentManager(), fragmentList);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(month - 1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                month = position + 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setVisibility(View.VISIBLE);

    }


    private void removePager() {


        tabLayout.removeAllTabs();

        viewPager.removeAllViewsInLayout();

        tabLayout.setVisibility(GONE);


    }


    @Override
    public void yearDone() {


        header.setText(String.valueOf(year));

        if (manager.findFragmentById(R.id.fragment_container) != null) {
            manager
                    .beginTransaction()
                    .remove(manager.findFragmentById(R.id.fragment_container))
                    .commit();
        }

        findViewById(R.id.fragment_container).setVisibility(GONE);

        addPager();

        state = 1;

    }


    @Override
    public void daysDone() {

        TextDialog dialog = new TextDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), null);


    }

    public void goLeft(View view) {


        //TODO: Check for transition
        Fragment oldFragment, newFragment;
        if (state == 0) {
            startYear = yearList.get(0) - yearList.size();
            oldFragment = manager.findFragmentById(R.id.fragment_container);
            newFragment = new FragmentYear();

            manager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.custom_slide_in_right, R.anim.custom_slide_out_right)
                    .remove(oldFragment)
                    .add(R.id.fragment_container, newFragment)
                    .commit();

        } else {

            year = year - 1;

            header.setText(String.valueOf(year));

            viewPager.removeAllViewsInLayout();
            addPager();

        }
    }

    public void goRight(View view) {

        Fragment oldFragment, newFragment;
        if (state == 0) {
            startYear = yearList.get(yearList.size() - 1) + 1;
            oldFragment = manager.findFragmentById(R.id.fragment_container);
            newFragment = new FragmentYear();


            manager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.custom_slide_in_left, R.anim.custom_slide_out_left)
                    .remove(oldFragment)
                    .add(R.id.fragment_container, newFragment)
                    .commit();
        } else {
            year = year + 1;

            header.setText(String.valueOf(year));

            viewPager.removeAllViewsInLayout();
            addPager();

        }


    }

    public void goBack(View view) {


        if (state == 0) {

            int currentYear = (cal.get(Calendar.YEAR) / 20) * 20 + 1;

            if (startYear > currentYear) {

                startYear = currentYear;
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.custom_slide_in_right, R.anim.custom_slide_out_right)
                        .remove(manager.findFragmentById(R.id.fragment_container))
                        .add(R.id.fragment_container, new FragmentYear())
                        .commit();
            } else if (startYear < currentYear) {
                startYear = currentYear;
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.custom_slide_in_left, R.anim.custom_slide_out_left)
                        .remove(manager.findFragmentById(R.id.fragment_container))
                        .add(R.id.fragment_container, new FragmentYear())
                        .commit();
            } else {
                //TODO: Custom Toast
                Toast.makeText(this, "Not Reactive", Toast.LENGTH_SHORT).show();
            }

        } else {


            removePager();

            manager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.custom_entry_zoom_out, 0)
                    .add(R.id.fragment_container, new FragmentYear())
                    .commit();

            findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

            state = 0;

            header.setText(R.string.year_text);
        }

    }

    @Override
    public void restartPager() {

        removePager();
        addPager();


    }
}
