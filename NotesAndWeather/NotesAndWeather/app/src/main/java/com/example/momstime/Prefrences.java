package com.example.momstime;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.example.momstime.Models.WeatherModel;

public class Prefrences {
    private static String PREF_NAME="Dairy_Pref";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public Prefrences(Context context) {
        this.context = context;
    }

    public void saveLoc(Location location){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("lat", String.valueOf(location.getLatitude()));
        editor.putString("long", String.valueOf(location.getLongitude()));
        editor.putBoolean("loc",true);
        editor.apply();
    }


    public void saveWeather(String city, String temp, String desc, String humidity){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

        editor=sharedPreferences.edit();
        editor.putString("city", city);
        editor.putString("temp", temp);
        editor.putString("desc", desc);
        editor.putString("humi", humidity);

        editor.apply();
    }

    public boolean isLocAvailable(){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("loc",false);
    }


    public String getLat(){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("lat",null);
    }
    public String getLong(){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("long",null);
    }


    public WeatherModel getWeather(){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

        WeatherModel weatherModel=new WeatherModel();

        weatherModel.setCity(sharedPreferences.getString("city",null));
        weatherModel.setTemp(sharedPreferences.getString("temp",null));
        weatherModel.setDesc(sharedPreferences.getString("desc",null));
        weatherModel.setHumidity(sharedPreferences.getString("humi",null));


        return weatherModel;
    }
}
