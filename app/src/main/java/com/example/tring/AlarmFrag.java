package com.example.tring;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tring.R;

import java.awt.font.TextAttribute;
import java.util.Calendar;
import java.util.Locale;

public class AlarmFrag extends Fragment implements TimePickerDialog.OnTimeSetListener , MenuItem.OnMenuItemClickListener {

    private Button picker, saver,cancel;
    Calendar c = Calendar.getInstance();
    boolean stat = false  ;
    TextView mon , tue , wed , thur , fri , sat , sun;
    int[] days = new int[7];
    int dchoose=0;
    TextView audopt;
    int audiochosen = 1;
    EditText secs;
    int secscount = 30;
    Switch task;
    Boolean taskset = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_alarm, container, false);

        picker = v.findViewById(R.id.pick);
        saver = v.findViewById(R.id.save);
        mon = v.findViewById(R.id.mon);
        tue = v.findViewById(R.id.tue);
        wed = v.findViewById(R.id.wed);
        thur = v.findViewById(R.id.thur);
        fri = v.findViewById(R.id.fri);
        sat = v.findViewById(R.id.sat);
        sun = v.findViewById(R.id.sun);
        cancel = v.findViewById(R.id.cancel);
        audopt = v.findViewById(R.id.audiooptions);
        audopt.setOnCreateContextMenuListener(this);
        secs = v.findViewById(R.id.secs);
        task = v.findViewById(R.id.switch1);

        Intent intent = new Intent();
        final long touchtime = intent.getLongExtra("time",-10);
        if(touchtime>c.getTimeInMillis()){
            getActivity().stopService(new Intent(v.getContext(), myService.class));
        }
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFrag timepicker = new TimePickerFrag();
                timepicker.setListener(AlarmFrag.this);
                timepicker.show(getActivity().getSupportFragmentManager(), "TimePicker");
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days[0]==0){
                    sun.setBackgroundResource(R.drawable.bgfordays_v2);
                    days[0]=1;
                    dchoose++;
                }else if(days[0]==1){
                    sun.setBackgroundResource(R.drawable.bgfordays);
                    days[0]=0;
                    dchoose--;
                }
            }
        });
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days[1]==0){
                    mon.setBackgroundResource(R.drawable.bgfordays_v2);
                    days[1]=1;
                    dchoose++;
                }else if(days[1]==1){
                    mon.setBackgroundResource(R.drawable.bgfordays);
                    days[1]=0;
                    dchoose--;
                }
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days[2]==0){
                    tue.setBackgroundResource(R.drawable.bgfordays_v2);
                    days[2]=1;
                    dchoose++;
                }else if(days[2]==1){
                    tue.setBackgroundResource(R.drawable.bgfordays);
                    days[2]=0;
                    dchoose--;
                }
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days[3]==0){
                    wed.setBackgroundResource(R.drawable.bgfordays_v2);
                    days[3]=1;
                    dchoose++;
                }else if(days[3]==1){
                    wed.setBackgroundResource(R.drawable.bgfordays);
                    days[3]=0;
                    dchoose--;
                }
            }
        });
        thur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days[4]==0){
                    thur.setBackgroundResource(R.drawable.bgfordays_v2);
                    days[4]=1;
                    dchoose++;
                }else if(days[4]==1){
                    thur.setBackgroundResource(R.drawable.bgfordays);
                    days[4]=0;
                    dchoose--;
                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days[5]==0){
                    fri.setBackgroundResource(R.drawable.bgfordays_v2);
                    days[5]=1;
                    dchoose++;
                }else if(days[5]==1){
                    fri.setBackgroundResource(R.drawable.bgfordays);
                    days[5]=0;
                    dchoose--;
                }
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days[6]==0){
                    sat.setBackgroundResource(R.drawable.bgfordays_v2);
                    days[6]=1;
                    dchoose++;
                }else if(days[6]==1){
                    sat.setBackgroundResource(R.drawable.bgfordays);
                    days[6]=0;
                    dchoose--;
                }
            }
        });
        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(stat) {
                    stat=false;
                    if(!secs.getText().toString().isEmpty()){
                        secscount = Integer.parseInt(secs.getText().toString().trim());
                    }
                    taskset = task.isChecked();
                    Intent intent = new Intent(v.getContext(), myService.class);
                    intent.putExtra("hour", c.get(Calendar.HOUR_OF_DAY));
                    intent.putExtra("minute", c.get(Calendar.MINUTE));
                    intent.putExtra("daysdata",days);
                    intent.putExtra("dayschosen",dchoose);
                    intent.putExtra("audiochosen",audiochosen);
                    intent.putExtra("secs",secscount);
                    intent.putExtra("taskset",taskset);
                    ContextCompat.startForegroundService(v.getContext(), intent);
                    Toast t = Toast.makeText(v.getContext() , "Alarm set for " + String.format(Locale.getDefault(), "%02d:%02d", c.get(Calendar.HOUR_OF_DAY) , c.get(Calendar.MINUTE)) + " Hours..." , Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER|Gravity.BOTTOM ,0,0);
                    t.show();
                }
                else{
                    Toast t = Toast.makeText(v.getContext() , "Pick time first!!", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER|Gravity.BOTTOM ,0,0);
                    t.show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(v.getContext(), myService.class));
            }
        });
        task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){
                  secs.setVisibility(View.INVISIBLE);

              }
              else {
                  secs.setVisibility(View.VISIBLE);
              }
            }
        });
        return v;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderIcon(R.drawable.ic_audiotrack_black_24dp);
        menu.setHeaderTitle("Choose Audios");
        MenuItem audio1 = menu.add(Menu.NONE , 1 , 1 , "Axwell Ingrosso");
        MenuItem audio2 = menu.add(Menu.NONE , 2 , 2 , "Bella Ciao");
        MenuItem audio3 = menu.add(Menu.NONE , 3 , 3 , "Rockstar");

        audio1.setOnMenuItemClickListener(this);
        audio2.setOnMenuItemClickListener(this);
        audio3.setOnMenuItemClickListener(this);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY , hourOfDay);
        c.set(Calendar.MINUTE , minute);
        c.set(Calendar.SECOND , 0);
        stat=true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case 1: {
                audiochosen = 1;
                return true;
            }
            case 2: {
                audiochosen = 2;
                return true;
            }
            case 3: {
                audiochosen = 3;
                return true;
            }
        }
        return false;
    }
}
