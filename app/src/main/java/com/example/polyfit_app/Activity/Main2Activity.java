package com.example.walkalarm;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.walkalarm.Classes.PagerAdapter;
import com.example.walkalarm.Fragments.AchievementsFragment;
import com.example.walkalarm.Fragments.AlarmFragment;
import com.example.walkalarm.Fragments.SettingsFragment;
import com.example.walkalarm.Fragments.StepsFragment;

public class MainActivity extends AppCompatActivity implements AlarmFragment.OnFragmentInteractionListener, StepsFragment.OnFragmentInteractionListener,
        AchievementsFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout=findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_alarm));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_steps));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_achievements));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_settings));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager=findViewById(R.id.pager);
        final PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
}
