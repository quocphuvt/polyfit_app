package com.example.polyfit_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.polyfit_app.fragment.meals.MorningFragment;
import com.example.polyfit_app.fragment.meals.NightFragment;
import com.example.polyfit_app.fragment.meals.NoonFragment;
import com.example.polyfit_app.R;
import com.example.polyfit_app.model.Diet;
import com.example.polyfit_app.model.Dishes;
import com.example.polyfit_app.model.Meal;
import com.example.polyfit_app.model.response.DietsResponse;
import com.example.polyfit_app.service.remote.DietsAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import devlight.io.library.ntb.NavigationTabBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MealsActivity extends AppCompatActivity {
    private NavigationTabBar tab_meals;
    private ViewPager pager_meals;
    private Toolbar toolbar;
    private Retrofit retrofit = RetrofitClient.getInstance();
    private DietsAPI dietsAPI;

    private void initView() {
        tab_meals = findViewById(R.id.tab_meals);
        pager_meals = findViewById(R.id.pager_meals);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dietsAPI = retrofit.create(DietsAPI.class);
        initView();

        Intent i = getIntent();
        String dietTitle = i.getStringExtra("title");
        int dietId = i.getIntExtra("id", 0);
        toolbar.setTitle("Chế độ ăn " + dietTitle.toLowerCase());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addModelTab();
        setDietData(dietId);
    }

    private void setMealTabsData(Diet diet) {
        MealsPagerAdapter mealsPagerAdapter = new MealsPagerAdapter(getSupportFragmentManager(), diet);
        pager_meals.setAdapter(mealsPagerAdapter);
        pager_meals.setOffscreenPageLimit(3);
        tab_meals.setViewPager(pager_meals);
        tab_meals.setModelIndex(0);
    }

    private void setDietData(int dietId) {
        Call<DietsResponse> dietsResponseCall = dietsAPI.getDietData(dietId);
        dietsResponseCall.enqueue(new Callback<DietsResponse>() {
            @Override
            public void onResponse(Call<DietsResponse> call, Response<DietsResponse> response) {
                if(response.isSuccessful()) {
                    DietsResponse dietsResponse = response.body();
                    if(dietsResponse.getStatus() == 0) {
                        setMealTabsData(dietsResponse.getObject());
                    }
                }
            }

            @Override
            public void onFailure(Call<DietsResponse> call, Throwable t) {

            }
        });
    }

    private void addModelTab() {
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.tab_morning),
                        getResources().getColor(R.color.morning)
                ).title("Sáng")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.tab_noon),
                        getResources().getColor(R.color.noon)
                ).title("Trưa")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.tab_night),
                        getResources().getColor(R.color.night)
                ).title("Tối")
                        .badgeTitle("state")
                        .build()
        );
        tab_meals.setModels(models);
    }
}

class MealsPagerAdapter extends FragmentStatePagerAdapter {
    private Diet diet;
    public MealsPagerAdapter(@NonNull FragmentManager fm, Diet diet) {
        super(fm);
        this.diet = diet;
    }

    private Meal getMeal(String mealText) {
        Meal meal = null;
        for(Meal m: diet.getMeals()) {
            if(m.getTitle().toLowerCase().contains(mealText) == true) {
                meal = m;
            }
        }

        return meal;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MorningFragment(getMeal("sáng").getDishes());
                break;
            case 1:
                fragment = new NoonFragment(getMeal("trưa").getDishes());
                break;
            case 2:
                fragment = new NightFragment(getMeal("tối").getDishes());
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
