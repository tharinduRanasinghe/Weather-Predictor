package com.example.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //start MainActivity after 3 seconds
                Intent homeIntent = new Intent(com.example.myweather.HomeActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
            },SPLASH_TIME_OUT
        );
    }
}