package com.example.momstime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.example.momstime.Fragments.WeatherFrag;
import com.example.momstime.Fragments.ScheduleFrag;
import com.example.momstime.Fragments.NotesFrag;
import com.example.momstime.Fragments.RemindersFrag;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigation;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    FrameLayout container;

    TextView tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawer=findViewById(R.id.drawer);
        navigation=findViewById(R.id.navigation);
        container=findViewById(R.id.container);

        toolbar=findViewById(R.id.toolbar);
        tt=toolbar.findViewById(R.id.toolbarTv);
        configureToolBar();
        configureDrawerView();

        tt.setText("Notes");
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new NotesFrag()).commit();
        navigation.getMenu().getItem(0).setCheckable(true);


    }
    private void configureToolBar() {

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);
        tt.setText("Digital Dairy");
        getSupportActionBar().setDisplayShowTitleEnabled(false);



    }

    private void configureDrawerView() {

        navigation.setNavigationItemSelectedListener(this);
        navigation.setItemIconTintList(null);
        drawerToggle=new ActionBarDrawerToggle(this,drawer,toolbar ,0, 0);
        drawerToggle.setDrawerIndicatorEnabled(true);
        TooltipCompat.setTooltipText(navigation,"more options");
        drawer.addDrawerListener(drawerToggle);



    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.notes :
                drawer.closeDrawer(GravityCompat.START);
                tt.setText("Notes");
              getSupportFragmentManager().beginTransaction().replace(R.id.container,new NotesFrag()).commit();
                break;

            case R.id.schedules :
                tt.setText("Schedules");
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new ScheduleFrag()).commit();

                break;

            case R.id.remind :
                tt.setText("Reminders");
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new RemindersFrag()).commit();
                break;


            case R.id.weather :
                tt.setText("Weather");
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new WeatherFrag()).commit();
                break;


        }
        return true;
    }





}