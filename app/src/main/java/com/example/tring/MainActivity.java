package com.example.tring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tring.ui.main.SectionsPagerAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements myadapter1.reset {
    ViewPager viewPager;
    RecyclerView glist;
    myadapter1 adapter = new myadapter1(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("Tring",MODE_PRIVATE);
        boolean stat = pref.getBoolean("taskset",false);
        Long settime = pref.getLong("settime",Long.MAX_VALUE);
        if(stat && Calendar.getInstance().getTimeInMillis()>settime){
            setContentView(R.layout.activity_main2);
            adapter.setreset(MainActivity.this);
            glist = findViewById(R.id.glist);
            glist.setLayoutManager(new LinearLayoutManager(this));
            glist.setAdapter(adapter);
        }else {
            setContentView(R.layout.activity_main);
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            viewPager.setCurrentItem(1, true);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
        }
    }


    @Override
    public void resetpage() {
        SharedPreferences pref = getSharedPreferences("Tring",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("taskset",false);
        edit.putLong("settime",Long.MAX_VALUE);
        edit.apply();
        int dchoose = pref.getInt("dchoose" , 0);
        if(dchoose==0) {
            stopService(new Intent(this, myService.class));
        }
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }
}