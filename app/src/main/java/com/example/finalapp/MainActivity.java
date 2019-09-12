package com.example.finalapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mprofilelable;
    private TextView muserslabel;
    private TextView mnotificationlabel;
    private ViewPager mmainpager;
    private  PagerViewAdapter mpagervieadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mprofilelable =  (TextView) findViewById(R.id.profilelabel);
        muserslabel = (TextView) findViewById(R.id.userslabel);
        mnotificationlabel = (TextView) findViewById(R.id.notificationlabel);

        mmainpager = (ViewPager) findViewById(R.id.mainpager);

        mpagervieadapter =  new PagerViewAdapter(getSupportFragmentManager());
        mmainpager.setAdapter(mpagervieadapter);
        mprofilelable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mmainpager.setCurrentItem(0);
            }
        });
        muserslabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mmainpager.setCurrentItem(1);
            }
        });

        mnotificationlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mmainpager.setCurrentItem(2);
            }
        });

        mmainpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTabs(int position) {
        if (position == 0)
        {
            mprofilelable.setTextColor(getColor(R.color.texttabbright));
            mprofilelable.setTextSize(22);

            muserslabel.setTextColor(getColor(R.color.texttabbright));
            muserslabel.setTextSize(16);

            mnotificationlabel.setTextColor(getColor(R.color.texttabbright));
            mnotificationlabel.setTextSize(16);

        }
        if (position == 1)
        {
            mprofilelable.setTextColor(getColor(R.color.texttabbright));
            mprofilelable.setTextSize(16);

            muserslabel.setTextColor(getColor(R.color.texttabbright));
            muserslabel.setTextSize(22);

            mnotificationlabel.setTextColor(getColor(R.color.texttabbright));
            mnotificationlabel.setTextSize(16);

        }
        if (position == 2)
        {
            mprofilelable.setTextColor(getColor(R.color.texttabbright));
            mprofilelable.setTextSize(16);

            muserslabel.setTextColor(getColor(R.color.texttabbright));
            muserslabel.setTextSize(16);

            mnotificationlabel.setTextColor(getColor(R.color.texttabbright));
            mnotificationlabel.setTextSize(22);

        }
    }
}
