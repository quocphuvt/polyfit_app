package com.example.polyfit_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.polyfit_app.Fragments.Meals.MorningFragment;
import com.example.polyfit_app.Fragments.Meals.NightFragment;
import com.example.polyfit_app.Fragments.Meals.NoonFragment;
import com.example.polyfit_app.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.Objects;

public class MealsActivity extends AppCompatActivity {
    private NavigationTabStrip tab_meals;
    private ViewPager pager_meals;
    private Toolbar toolbar;

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
        initView();
        configTabs();

        Intent i = getIntent();
        String dietTitle = i.getStringExtra("title");
        toolbar.setTitle("Chế độ ăn " + dietTitle.toLowerCase());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pager_meals.setOffscreenPageLimit(3);
        MealsPagerAdapter mealsPagerAdapter = new MealsPagerAdapter(getSupportFragmentManager());
        pager_meals.setAdapter(mealsPagerAdapter);
        tab_meals.setViewPager(pager_meals);
    }

    private void configTabs() {
        tab_meals.setStripColor(Color.RED);
        tab_meals.setStripWeight(5);
        tab_meals.setStripFactor(2);
        tab_meals.setStripType(NavigationTabStrip.StripType.LINE);
        tab_meals.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        tab_meals.setTypeface("fonts/typeface.ttf");
        tab_meals.setCornersRadius(3);
        tab_meals.setAnimationDuration(300);
        tab_meals.setInactiveColor(this.getResources().getColor(R.color.content));
        tab_meals.setActiveColor(this.getResources().getColor(R.color.title));
        tab_meals.setTitles("Sáng", "Trưa", "Tối");
    }
}

class MealsPagerAdapter extends FragmentStatePagerAdapter {

    public MealsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MorningFragment();
                break;
            case 1:
                fragment = new NoonFragment();
                break;
            case 2:
                fragment = new NightFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
