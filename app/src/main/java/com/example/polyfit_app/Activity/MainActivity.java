package com.example.polyfit_app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager.widget.ViewPager;

import com.example.polyfit_app.Adapter.PagerAdapter;
import com.example.polyfit_app.Fragments.DishesFragment;
import com.example.polyfit_app.Fragments.HistoriesFragment;
import com.example.polyfit_app.Fragments.HomeFragment;
import com.example.polyfit_app.Fragments.ProfileFragment;
import com.example.polyfit_app.Fragments.DietsFragment;
import com.example.polyfit_app.Model.Reminder;
import com.example.polyfit_app.Model.Responses.BodypartResponse;
import com.example.polyfit_app.R;

import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.example.polyfit_app.Service.local.StepCountServices;
import com.example.polyfit_app.Service.remote.BodypartsAPI;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import devlight.io.library.ntb.NavigationTabBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, DishesFragment.OnFragmentInteractionListener,
        HistoriesFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {
    private NavigationTabBar tabBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        getReminder();
        runServices();
        tabBar = findViewById(R.id.tabs_main);
        addModelTab();
        final ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(adapter);
        tabBar.setViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void addModelTab() {
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.house),
                        getResources().getColor(R.color.noon)
                ).title("Trang chủ")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.diet),
                        getResources().getColor(R.color.morning)
                ).title("Món ăn")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.gym),
                        getResources().getColor(R.color.noon)
                ).title("Bài tập")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.skills),
                        getResources().getColor(R.color.morning)
                ).title("Thông tin")
                        .badgeTitle("icon")
                        .build()
        );
        tabBar.setModels(models);
    }

    //getAlarm
    private void getReminder(){
        List<Reminder> reminders= PolyfitDatabase.getInstance(MainActivity.this).reminderDAO().getReminder();
        for(int i=0;i<reminders.size();i++){
            Log.e("PHAYTV",reminders.get(i).getHour()+"");
        }
    }

    private void runServices() {
        Intent serviceIntent = new Intent(this, StepCountServices.class);
        ContextCompat.startForegroundService(this, serviceIntent);
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
