package com.example.polyfit_app.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.polyfit_app.Adapter.PagerAdapter;
import com.example.polyfit_app.Fragments.DishesFragment;
import com.example.polyfit_app.Fragments.HistoriesFragment;
import com.example.polyfit_app.Fragments.HomeFragment;
import com.example.polyfit_app.Fragments.ProfileFragment;
import com.example.polyfit_app.Fragments.DietsFragment;
import com.example.polyfit_app.Model.Reminder;
import com.example.polyfit_app.Model.Responses.BodypartResponse;
import com.example.polyfit_app.Model.Routine;
import com.example.polyfit_app.R;

import com.example.polyfit_app.Service.Reminder.ReminderServices;
import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.example.polyfit_app.Service.local.StepCountServices;
import com.example.polyfit_app.Service.remote.BodypartsAPI;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, DishesFragment.OnFragmentInteractionListener,
        HistoriesFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        getReminder();

        showToken();
        runServices();
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

    public void showToken() {

        Log.e("token", FirebaseInstanceId.getInstance().getToken());
    }


    private void runServices() {
        Intent serviceIntent = new Intent(this, StepCountServices.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }





}
