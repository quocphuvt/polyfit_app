package com.example.polyfit_app.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.polyfit_app.fragment.DietsFragment;
import com.example.polyfit_app.fragment.ExerciseFragment;
import com.example.polyfit_app.fragment.HomeFragment;
import com.example.polyfit_app.fragment.ProfileFragment;
import com.example.polyfit_app.models.User;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    private User user;

    public PagerAdapter(FragmentManager fm, int numberOfTabs, User user) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.user = user;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                HomeFragment homeFragment = new HomeFragment(user);
                return homeFragment;
            case 1:
                DietsFragment dietsFragment = new DietsFragment(user);
                return dietsFragment;
            case 2:
                ExerciseFragment exerciseFragment = new ExerciseFragment(user);
                return exerciseFragment;
            case 3:
                ProfileFragment profileFragment = new ProfileFragment(user);
                return profileFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
