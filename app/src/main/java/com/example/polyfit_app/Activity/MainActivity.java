package com.example.polyfit_app.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager.widget.ViewPager;

import com.example.polyfit_app.Activity.Login.LoginActivity;
import com.example.polyfit_app.Adapter.PagerAdapter;
import com.example.polyfit_app.Fragments.DishesFragment;
import com.example.polyfit_app.Fragments.HistoriesFragment;
import com.example.polyfit_app.Fragments.HomeFragment;
import com.example.polyfit_app.Fragments.ProfileFragment;
import com.example.polyfit_app.Fragments.DietsFragment;
import com.example.polyfit_app.Model.Reminder;
import com.example.polyfit_app.Model.Responses.BodypartResponse;
import com.example.polyfit_app.Model.Responses.RoutineResponse;
import com.example.polyfit_app.Model.Responses.UserResponse;
import com.example.polyfit_app.Model.Routine;
import com.example.polyfit_app.Model.RoutineRequest;
import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.R;

import com.example.polyfit_app.Service.Reminder.ReminderServices;
import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.example.polyfit_app.Service.local.StepCountServices;
import com.example.polyfit_app.Service.remote.BodypartsAPI;
import com.example.polyfit_app.Service.remote.PolyFitService;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.example.polyfit_app.Service.remote.RoutineAPI;
import com.example.polyfit_app.Utils.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Calendar;
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
    List<Routine> routines;
    private RoutineAPI routineAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        getReminder();
        Retrofit retrofit = RetrofitClient.getInstance();
        routineAPI = retrofit.create(RoutineAPI.class);
        runServices();
        tabBar = findViewById(R.id.tabs_main);
        addModelTab();
        final ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(adapter);
        tabBar.setViewPager(viewPager);
        if(isNetworkConnected()){
            Log.e("PhayTran","Connected to internet!!!!");
            getAndSaveRoutine();
        }else {
            Toast.makeText(this, "Please check your connection!!!", Toast.LENGTH_SHORT).show();
        }
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

    private void runServices() {
        Intent serviceIntent = new Intent(this, StepCountServices.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }


    //get routine
    private void getAndSaveRoutine() {
        routines = PolyfitDatabase.getInstance(getApplicationContext()).routineDAO().getRoutine();
        if (!routines.isEmpty()) {
            postRoutine(routines);
        }
        if(routines.isEmpty()){
           Log.e("PhayTran","List empty!!!");
        }
    }

    private void postRoutine(List<Routine> routineList) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
        for (int i = 0; i < routineList.size(); i++) {
            Log.e("getRoutine", routineList.get(i).getStepCount() + "");
            RoutineRequest routine = new RoutineRequest(routineList.get(i).getStepCount(), routineList.get(i).getTimePractice(), (routineList.get(i).getStepCount() * 4) + "", sharedPreferences.getInt("id", 0));
            Call<RoutineResponse> callRoutine = routineAPI.createRoutine(routine);
            callRoutine.enqueue(new Callback<RoutineResponse>() {
                @Override
                public void onResponse(Call<RoutineResponse> call, Response<RoutineResponse> response) {
                    if (response.isSuccessful()) {
                        Log.e("routine", "save success");
                        PolyfitDatabase.getInstance(MainActivity.this).routineDAO().deleteAll();
                    }
                    if (!response.isSuccessful()) {
                        Log.e("routine", response.code() + " : " + response.body());
                    }

                }

                @Override
                public void onFailure(Call<RoutineResponse> call, Throwable t) {
                    Log.e("routine", "save failed" + "\n" + call.request() + " :: " + call.toString());
                }
            });
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
