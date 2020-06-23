package com.omidipoor.cla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.omidipoor.cla.fragment.fragment_login;
import com.omidipoor.cla.fragment.fragment_register;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new fragment_login());
        pagerAdapter.addFragment(new fragment_register());
        viewPager.setAdapter(pagerAdapter);

    }

}