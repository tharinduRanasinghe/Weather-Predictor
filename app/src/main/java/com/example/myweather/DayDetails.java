package com.example.myweather;


import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;


import android.icu.text.SimpleDateFormat;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class DayDetails extends BaseActivity {
    Geocoder geocoder;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);

        //setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //calling load data function
        loadData();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadData() {

        //receiving data from MainActivity on item click
        Bundle data = getIntent().getExtras();

        //creating instance of geocoder class
        geocoder = new Geocoder(this);

        //getting date, format it and display it on text view
        TextView txt1 = (TextView)findViewById(R.id.text1);
        if(data.getString("date")!= null)
        {
            long emills = Long.parseLong(data.getString("date"))*1000;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = formatter.format(emills);
            txt1.setText(dateString);
        }

        //display city and country on text view
        TextView txt2 = (TextView)findViewById(R.id.text2);
        if(data.getString("city")!= null){
            txt2.setText(data.getString("city")+"/"+data.getString("country"));
        }

        //displaying weather icon
        ImageView img1 = (ImageView) findViewById(R.id.img1);
        if(data.getInt("wIcon")!= 0){
            img1.setImageResource(data.getInt("wIcon"));
        }

        //displaying temperature
        TextView txt3 = (TextView)findViewById(R.id.text3);
        if(data.getString("temp")!= null){
            txt3.setText(data.getString("temp")+" °C");
        }

        //displaying weather description
        TextView txt4 = (TextView)findViewById(R.id.text4);
        if(data.getString("wDescription")!= null){
            txt4.setText(data.getString("wDescription").toUpperCase());
        }

        //displaying humidity
        TextView txt5 = (TextView)findViewById(R.id.text5);
        if(data.getString("humidity")!= null){
            txt5.setText("Humidity: " + data.getString("humidity") + "%");
        }


    }


}