package com.example.momstime.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.momstime.LocationActivity;
import com.example.momstime.Prefrences;
import com.example.momstime.R;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFrag extends Fragment  {

RequestQueue requestQueue;
TextView cTv,tTv,dTv,hTv;

Prefrences prefrences;
    public WeatherFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
         prefrences=new Prefrences(getActivity());
        requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        cTv=view.findViewById(R.id.cTv);
        tTv=view.findViewById(R.id.tTv);
        dTv=view.findViewById(R.id.dTv);
        hTv=view.findViewById(R.id.hTv);





        checkLoca();


        return view;
    }

    private void setWeatherUpdate() {

          if (prefrences.getWeather().getTemp()!=null){


              hTv.setText(prefrences.getWeather().getHumidity()+" %");
              cTv.setText(prefrences.getWeather().getCity());
              tTv.setText(prefrences.getWeather().getTemp()+" F");
              dTv.setText(prefrences.getWeather().getDesc());
          }



    }

    private void checkWeather() {



        String lat=prefrences.getLat();
        String lon=prefrences.getLong();

      // String url= "https://api.openweathermap.org/data/2.5/weather?lat=31.5204&lon=74.3587&appid=c1811530a436eb59f74f63711abd83b4&units=Imperial";
       String urll= "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=c1811530a436eb59f74f63711abd83b4&units=Imperial";

                JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,urll,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {




                    String temp=response.getJSONObject("main").getString("temp");
                    String humidity=response.getJSONObject("main").getString("humidity");
                    String desc=response.getJSONArray("weather").getJSONObject(0).getString("description");
                    String city=response.getString("name");

                    prefrences.saveWeather(city,temp,desc,humidity);
                      setWeatherUpdate();
//


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), ""+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) ;

        requestQueue.add(request);
     //   requestQueue.start();
    }

    private void checkLoca() {
        if (!prefrences.isLocAvailable()){
            startActivity(new Intent(getContext(), LocationActivity.class));
        }

    }


    @Override
    public void onStart() {
        super.onStart();

        setWeatherUpdate();
        checkWeather();
    }
}