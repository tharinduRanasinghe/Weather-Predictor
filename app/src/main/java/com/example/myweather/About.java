package com.example.myweather;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class About extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //displaying data
        String about="This is an app which provides the day time \n \t \t temperature, \n \t \t weather condition, \n \t \t humidity as a percentage, \nfor eight consecutive days including today.\n \nUser can determine the city and temperature unit. \n \nDesigned by : s14109";
        TextView about_text = (TextView) findViewById(R.id.about);
        about_text.setText(about);
    }

}