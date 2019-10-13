package com.example.polyfit_app.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.polyfit_app.Adapter.PagerAdapter;
import com.example.polyfit_app.Fragments.HistoriesFragment;
import com.example.polyfit_app.Fragments.HomeFragment;
import com.example.polyfit_app.Fragments.ProfileFragment;
import com.example.polyfit_app.Fragments.DietsFragment;
import com.example.polyfit_app.Model.Reminder;
import com.example.polyfit_app.R;

import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, DietsFragment.OnFragmentInteractionListener,
        HistoriesFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        getReminder();
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_diet));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_history));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_user));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    //getAlarm
    private void getReminder(){
        List<Reminder> reminders= PolyfitDatabase.getInstance(Main2Activity.this).reminderDAO().getReminder();
        for(int i=0;i<reminders.size();i++){
            Log.e("PHAYTV",reminders.get(i).getHour()+"");
        }
    }


//    //Register alarm
//    if (chk_monday.isChecked()) {
//        forday(2);
//    } else if (chk_tuesday.isChecked()) {
//        forday(3);
//    } else if (chk_wednesday.isChecked()) {
//        forday(4);
//    } else if (chk_thursday.isChecked()) {
//        forday(5);
//    } else if (chk_friday.isChecked()) {
//        forday(6);
//    } else if (chk_sat.isChecked()) {
//        forday(7);
//    } else if (chk_sunday.isChecked()) {
//        forday(1);
//    }
//
//    public void forday(int week) {
//
//        calSet.set(Calendar.DAY_OF_WEEK, week);
//        calSet.set(Calendar.HOUR_OF_DAY, hour);
//        calSet.set(Calendar.MINUTE, minuts);
//        calSet.set(Calendar.SECOND, 0);
//        calSet.set(Calendar.MILLISECOND, 0);
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                calSet.getTimeInMillis(), 1 * 60 * 60 * 1000, pendingIntent);
//    }
}
