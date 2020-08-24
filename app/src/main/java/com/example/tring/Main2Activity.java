package com.example.tring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

public class Main2Activity extends AppCompatActivity {

    RecyclerView glist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        glist = findViewById(R.id.glist);
        glist.setLayoutManager(new LinearLayoutManager(this));
        glist.setAdapter(new myadapter1(this));


    }
}
