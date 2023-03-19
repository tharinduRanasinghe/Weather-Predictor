package com.example.myweather;

import android.app.Activity;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;
    private  List<String> condition;
    private  List<String> temperature;
    private  List<String> date;
    private List<Integer> weatherIcon;



    public CustomListAdapter(@NonNull Activity context,List<String> condition,List<String> temperature,List<String> date,List<Integer> weatherIcon) {
        super(context, R.layout.my_list,condition);
        this.context = context;
        this.condition=condition;
        this.temperature = temperature;
        this.date=date;
        this.weatherIcon=weatherIcon;


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.my_list,null,true);

        //Displaying the day of the week
        long emills = Long.parseLong(date.get(position))*1000;
        long ctime= System.currentTimeMillis();

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String dateString1 = formatter2.format(new Date(emills));
        String dateString2 = formatter2.format(new Date(ctime));

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayString = formatter.format(emills);
        TextView textView1 = (TextView) rowView.findViewById(R.id.itemname1);
        if (dateString1.equals(dateString2)){
            textView1.setText(dayString+"(Today)");
        }else{
            textView1.setText(dayString);
        }

        //Displaying weather condition
        TextView textView2 = (TextView) rowView.findViewById(R.id.itemname2);
        textView2.setText(condition.get(position));

        TextView textView3 = (TextView) rowView.findViewById(R.id.itemname3);
        textView3.setText(temperature.get(position)+"°");


        //displaying weather icon
        ImageView img = (ImageView) rowView.findViewById(R.id.icon);
        img.setImageResource(weatherIcon.get(position));

        return rowView;
    }
}
