package com.example.polyfit_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.example.polyfit_app.Fragments.Meals.MorningFragment;
import com.example.polyfit_app.Fragments.Meals.NightFragment;
import com.example.polyfit_app.Fragments.Meals.NoonFragment;
import com.example.polyfit_app.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class MealsActivity extends AppCompatActivity {
    private NavigationTabStrip tab_meals;
    private ViewPager pager_meals;

    private void initView() {
        tab_meals = findViewById(R.id.tab_meals);
        pager_meals = findViewById(R.id.pager_meals);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        initView();
        configTabs();
        MealsPagerAdapter mealsPagerAdapter = new MealsPagerAdapter(getSupportFragmentManager());
        pager_meals.setAdapter(mealsPagerAdapter);
        tab_meals.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tab_meals.setTabIndex(position);
            }

            @Override
            public void onPageSelected(int position) {
                tab_meals.setTabIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab_meals.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                pager_meals.setCurrentItem(index);
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
    }
    
    private void configTabs() {
        tab_meals.setTabIndex(0, true);
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
