package com.example.viewpractice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.viewpractice.fragments.MainFragment;
import com.example.viewpractice.fragments.TextFragment;

public class MainActivity extends AppCompatActivity {
    private MainFragment mainFragment;
    //    private ListFragment mainFragment;
    private TextFragment dashFragment;
    private TextFragment notiFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    fragmentTransaction.show(mainFragment).hide(dashFragment).hide(notiFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_dashboard:
                    dashFragment.setText(getString(R.string.title_dashboard));
                    fragmentTransaction.hide(mainFragment).show(dashFragment).hide(notiFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_notifications:
                    notiFragment.setText(getString(R.string.title_notifications));
                    fragmentTransaction.hide(mainFragment).hide(dashFragment).show(notiFragment).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mainFragment = new MainFragment();
//        mainFragment = new ListFragment();
        dashFragment = new TextFragment();
        notiFragment = new TextFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flt_content, mainFragment, getString(R.string.title_home))
                .add(R.id.flt_content, dashFragment, getString(R.string.title_dashboard))
                .add(R.id.flt_content, notiFragment, getString(R.string.title_notifications))
                .show(mainFragment)
                .hide(dashFragment)
                .hide(notiFragment)
                .commitAllowingStateLoss();
        //浏览器启动app，并获取数据
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

}
