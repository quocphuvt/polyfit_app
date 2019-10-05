package com.example.polyfit_app.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.polyfit_app.Fragment.FindFriendFragment;
import com.example.polyfit_app.Fragment.FriendListFragment;
import com.example.polyfit_app.Fragment.HomeFragment;
import com.example.polyfit_app.Fragment.ProfileFragment;
import com.example.polyfit_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class Main2Activity extends AppCompatActivity {
    private BottomNavigationView mBottomNav;
    private FrameLayout mMainFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.main2_activity);
        mBottomNav = findViewById(R.id.bottom_nav);
        mMainFrame = findViewById(R.id.main_frame);
        Fragment messageFragment = new HomeFragment();
        setFragment(messageFragment);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Fragment messageFragment = new HomeFragment();
                        setFragment(messageFragment);
                        return true;
                    case R.id.friendFragment:
                        Fragment friendFragment = new FriendListFragment();
                        setFragment(friendFragment);
                        return true;
                    case R.id.findFriendFragment:
                        Fragment findFriend = new FindFriendFragment();
                        setFragment(findFriend);
                        return true;
                    case R.id.nav_profile:
                        Fragment profileFragment = new ProfileFragment();
                        setFragment(profileFragment);
                        return true;
                    default:
                        return false;
                }

            }
        });


    }

    public void setFragment(Fragment mFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, mFragment);
        fragmentTransaction.commit();
    }


}
