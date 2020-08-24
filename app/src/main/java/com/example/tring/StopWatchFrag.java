package com.example.tring;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StopWatchFrag extends Fragment {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    ImageButton play, pause, reset;
    Button lap;
    Vibrator vibrator;
    RecyclerView laplist;
    myadapter adapter;
    List<Integer> itemnos = new ArrayList<>();
    List<Long> lapstops = new ArrayList<>();
    List<String> splits = new ArrayList<>();
    List<String> laps = new ArrayList<>();
    boolean isZero = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_stopwatch, container, false);

        chronometer = v.findViewById(R.id.chronometer);
        play = v.findViewById(R.id.play);
        pause = v.findViewById(R.id.pause);
        reset = v.findViewById(R.id.reset);
        lap = v.findViewById(R.id.lap);
        laplist = v.findViewById(R.id.laplist);
        laplist.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new myadapter(itemnos , splits , laps);
        laplist.setAdapter(adapter);
        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                    isZero = false;
                    vibrator.vibrate(100);
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                    vibrator.vibrate(100);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                isZero = true;
                vibrator.vibrate(100);
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running && !isZero) {
                    laplist.setVisibility(View.VISIBLE);
                    itemnos.add(itemnos.size() + 1);
                    long lapstop = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
                    lapstops.add(lapstop);
                    int lminutes = (int) (lapstop) / 60;
                    int lseconds = (int) (lapstop) % 60;
                    long splitstop;
                    if (lapstops.size() == 1) {
                        splitstop = lapstops.get(lapstops.size() - 1);
                    } else {
                        splitstop = lapstops.get(lapstops.size() - 1) - lapstops.get(lapstops.size() - 2);
                    }
                    if(splitstop<0){
                        splitstop = lapstops.get(lapstops.size() - 1);
                    }
                    int sminutes = (int) (splitstop) / 60;
                    int sseconds = (int) (splitstop) % 60;
                    String lapstopformatted = String.format(Locale.getDefault(), "%02d:%02d", lminutes, lseconds);
                    String splitstopformatted = String.format(Locale.getDefault(), "%02d:%02d", sminutes, sseconds);
                    laps.add(lapstopformatted);
                    splits.add(splitstopformatted);
                    adapter.notifyDataSetChanged();
                    vibrator.vibrate(100);
                }
                else{
                    Toast t = Toast.makeText(v.getContext(),"Stopwatch hasn't been started yet!!" , Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER|Gravity.BOTTOM,0,0);
                    t.show();
                }
            }
        });

        return v;
    }
}
