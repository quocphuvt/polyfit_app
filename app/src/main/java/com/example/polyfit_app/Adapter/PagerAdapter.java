package com.example.walkalarm.Classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.walkalarm.Fragments.AchievementsFragment;
import com.example.walkalarm.Fragments.AlarmFragment;
import com.example.walkalarm.Fragments.SettingsFragment;
import com.example.walkalarm.Fragments.StepsFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {


    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs=numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch(i)
        {
            case 0:
                AlarmFragment alarmFragment=new AlarmFragment();
                return alarmFragment;
            case 1:
                StepsFragment stepsFragment=new StepsFragment();
                return stepsFragment;
            case 2:
                AchievementsFragment achievementsFragment=new AchievementsFragment();
                return achievementsFragment;
            case 3:
                SettingsFragment settingsFragment=new SettingsFragment();
                return settingsFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
