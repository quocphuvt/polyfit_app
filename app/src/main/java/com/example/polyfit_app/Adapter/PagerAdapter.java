package com.example.polyfit_app.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.polyfit_app.Fragments.DishesFragment;
import com.example.polyfit_app.Fragments.HistoriesFragment;
import com.example.polyfit_app.Fragments.HomeFragment;
import com.example.polyfit_app.Fragments.ProfileFragment;
import com.example.polyfit_app.Fragments.DietsFragment;
import com.example.polyfit_app.Fragments.QuotesFragment;
import com.example.polyfit_app.Fragments.TutorialCardFragment;
import com.example.polyfit_app.Model.Tutorial;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                DishesFragment dietsFragment = new DishesFragment();
                return dietsFragment;
            case 2:
                QuotesFragment quotesFragment = new QuotesFragment();
                return quotesFragment;
            case 3:
                ProfileFragment profileFragment = new ProfileFragment();
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
