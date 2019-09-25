package com.example.finalapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalapp.models.SystemUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mprofilelable;
    private TextView muserslabel;
    private TextView mnotificationlabel;
    private TextView mfindlabel;
    private ViewPager mmainpager;
    private EditText msg,reciver;
    private Button send;
    private Integer intial;
    private  PagerViewAdapter mpagervieadapter;
    public  static Integer currentitem ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemUser us = new SystemUser("G A P Induwara","IT18140712","G1",2,2,"Computing","s");

//        Intent gotouserreg = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(gotouserreg);



        if(currentitem != null) {
            intial = currentitem;
        }
        else
        {
            intial = 0;
        }
        mprofilelable =  (TextView) findViewById(R.id.profilelabel);
        muserslabel = (TextView) findViewById(R.id.userslabel);
        mnotificationlabel = (TextView) findViewById(R.id.notificationlabel);
        mfindlabel = (TextView) findViewById(R.id.findlabel);

        mmainpager = (ViewPager) findViewById(R.id.mainpager);

        mpagervieadapter =  new PagerViewAdapter(getSupportFragmentManager());
        mmainpager.setAdapter(mpagervieadapter);
        mmainpager.setCurrentItem(intial);




        mprofilelable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setcurrent(0);
                mmainpager.setCurrentItem(currentitem);
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
               setcurrent(3);
                mmainpager.setCurrentItem(currentitem);
            }
        });

        mfindlabel.setOnClickListener(new View.OnClickListener() {
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

    public  void setcurrent(int itemnumber)
    {
        currentitem =  itemnumber;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTabs(int position) {
        if (position == 0)
        {
            setcurrent(0);
            mprofilelable.setTextColor(getColor(R.color.texttabbright));
            mprofilelable.setTextSize(22);

            muserslabel.setTextColor(getColor(R.color.texttabbright));
            muserslabel.setTextSize(16);

            mnotificationlabel.setTextColor(getColor(R.color.texttabbright));
            mnotificationlabel.setTextSize(16);

            mfindlabel.setTextColor(getColor(R.color.texttabbright));
            mfindlabel.setTextSize(16);



        }
        if (position == 1)
        {
            setcurrent(1);
            mprofilelable.setTextColor(getColor(R.color.texttabbright));
            mprofilelable.setTextSize(16);

            muserslabel.setTextColor(getColor(R.color.texttabbright));
            muserslabel.setTextSize(22);

            mnotificationlabel.setTextColor(getColor(R.color.texttabbright));
            mnotificationlabel.setTextSize(16);

            mfindlabel.setTextColor(getColor(R.color.texttabbright));
            mfindlabel.setTextSize(16);

        }
        if (position == 2)
        {
            setcurrent(2);
            mprofilelable.setTextColor(getColor(R.color.texttabbright));
            mprofilelable.setTextSize(16);

            muserslabel.setTextColor(getColor(R.color.texttabbright));
            muserslabel.setTextSize(16);

            mnotificationlabel.setTextColor(getColor(R.color.texttabbright));
            mnotificationlabel.setTextSize(16);

            mfindlabel.setTextColor(getColor(R.color.texttabbright));
            mfindlabel.setTextSize(22);

        }
        if (position == 3)
        {
            setcurrent(3);
            mprofilelable.setTextColor(getColor(R.color.texttabbright));
            mprofilelable.setTextSize(16);

            muserslabel.setTextColor(getColor(R.color.texttabbright));
            muserslabel.setTextSize(16);

            mnotificationlabel.setTextColor(getColor(R.color.texttabbright));
            mnotificationlabel.setTextSize(22);

            mfindlabel.setTextColor(getColor(R.color.texttabbright));
            mfindlabel.setTextSize(16);

        }

    }
}
