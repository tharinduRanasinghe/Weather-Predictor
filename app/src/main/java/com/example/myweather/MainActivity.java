package com.example.myweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    List<String> temperature;
    List<String> weatherCon ;
    List<String> weatherDes ;
    List<String> date;
    List<String> humidity;
    List<Integer> weatherIcon;
    Geocoder geocoder ;
    String content;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //calling refreshData method
        refreshData();
    }



    public void refreshData() {

        //creating instance of FetchData class
        FetchData weather = new FetchData();

        try {

            //Initiating variables
            temperature = new ArrayList<>();
            weatherCon = new ArrayList<>();
            weatherDes = new ArrayList<>();
            date = new  ArrayList<>();
            humidity = new  ArrayList<>();
            weatherIcon = new  ArrayList<>();
            geocoder = new Geocoder(this);

            //getting preference variable by creating SharedPreference instance
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            String tempUnit = prefs.getString("temp", "metric");
            String cityName = prefs.getString("city","Chicago");

            //getting address details from city name to get latitude and longitude
            List<Address> address1 = geocoder.getFromLocationName(cityName,1);
            Address addr = address1.get(0);

            content = weather.execute("https://api.openweathermap.org/data/2.5/onecall?lat="+addr.getLatitude()+"&lon="+addr.getLongitude()+"&units="+tempUnit+"&exclude=hourly,current,minutely,alerts&appid=d662b602504c072691d7d7e0d2a107f0").get();


            //getting whole json string
            JSONObject jsonObject = new JSONObject(content);

            //extracting data array from json string
            JSONArray weatherData = jsonObject.getJSONArray("daily");

            //loop to get all json objects from data jsonarray
            for (int i =0; i<weatherData.length() ;i++){
                JSONObject secondObject = weatherData.getJSONObject(i);
                JSONArray condition = secondObject.getJSONArray("weather");

                for (int j =0; j<condition.length() ;j++){
                    JSONObject thirdObject = condition.getJSONObject(j);

                    //adding values to the lists
                    weatherCon.add(thirdObject.getString("main"));
                    weatherDes.add(thirdObject.getString("description"));
                    weatherIcon.add(getIcon(thirdObject.getString("main")));

                }

                JSONObject celciustemp = secondObject.getJSONObject("temp");
                temperature.add(celciustemp.getString("day"));

                date.add(secondObject.getString("dt"));
                humidity.add(secondObject.getString("humidity"));



            }

            //getting address from lattitude and longitude
            List<Address> address2 = geocoder.getFromLocation(jsonObject.getDouble("lat"), jsonObject.getDouble("lon"), 1);



            //creating instance of CustomListAdapter to list the items as a listview
            CustomListAdapter adapter = new CustomListAdapter(this,weatherCon,temperature,date,weatherIcon);
            ListView list = (ListView) findViewById(R.id.list_view);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DayDetails.class);

                    //send data of selected list item to the  DayDetail activity on item click
                    intent.putExtra("date", date.get(position));
                    intent.putExtra("wIcon",weatherIcon.get(position));
                    intent.putExtra("temp",temperature.get(position));
                    intent.putExtra("wDescription",weatherDes.get(position));
                    intent.putExtra("humidity",humidity.get(position));
                    intent.putExtra("country", address2.get(0).getCountryName());
                    intent.putExtra("city", address2.get(0).getLocality());

                    //start Daydetail activity on item click
                    startActivity(intent);


                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Assigning weather icon that is relevant to weather condition
    public int getIcon(String weatherCondition){
        int result = 0;
        switch (weatherCondition) {
            case "Clear":
                result = R.drawable.clear_sky;
                break;
            case "Clouds":
                result = R.drawable.clouds;
                break;
            case "Drizzle":
                result= R.drawable.drizzle;
                break;
            case "Fog":
                result= R.drawable.fog;
                break;
            case "Rain":
                result= R.drawable.rain;
                break;
            case "Snow":
                result= R.drawable.snow;
                break;
            case "Thunderstorm":
                result= R.drawable.thunderstorm;
                break;
            case "Tornado":
                result= R.drawable.tornado;
                break;

        }

        return result;
    }

    //on resuming the activity
    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}