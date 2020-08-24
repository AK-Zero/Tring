package com.example.tring;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class TimerFrag extends Fragment {
    EditText timeentry;
    TextView timebox;
    Button multi, reset;
    long time_entered = 0;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = 0;
    Vibrator vibrator;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_timer, container, false);

        timeentry = v.findViewById(R.id.set_time);
        timebox = v.findViewById(R.id.timebox);
        multi = v.findViewById(R.id.start_pause);
        reset = v.findViewById(R.id.reset);
        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        timeentry.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN) &&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    String time = timeentry.getText().toString().trim();
                    int hy=0;
                    try {
                        LocalTime.parse(time);
                    } catch (DateTimeParseException | NullPointerException e) {
                        Toast t = Toast.makeText(v.getContext(),"Time entered is not of proper format!!!" , Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER|Gravity.BOTTOM,0,0);
                        t.show();
                        hy++;
                    }
                    if(hy==0) {
                        String[] vals = time.split(":");
                        int hour = Integer.parseInt(vals[0]);
                        int minute = Integer.parseInt(vals[1]);
                        int sec = Integer.parseInt(vals[2]);
                        time_entered = hour * 3600000 + minute * 60000 + sec * 1000;
                        if(time_entered>0) {
                            mTimeLeftInMillis = time_entered;
                            resetTimer();
                            vibrator.vibrate(100);
                        }
                        else{
                            Toast t = Toast.makeText(v.getContext(),"Ener time greater than 0..." , Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER|Gravity.BOTTOM,0,0);
                            t.show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                resetTimer();
            }
        });
        updateCountDownText();

        return v;
    }

    @SuppressLint("SetTextI18n")
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                vibrator.vibrate(1000);
                multi.setText("Start");
                multi.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        multi.setText("PAUSE");
        reset.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        multi.setText("Start");
        reset.setVisibility(View.VISIBLE);
    }
    private void resetTimer() {
        mTimeLeftInMillis = time_entered;
        updateCountDownText();
        reset.setVisibility(View.INVISIBLE);
        multi.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) ((mTimeLeftInMillis / 1000) % 3600) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours , minutes, seconds);
        timebox.setText(timeLeftFormatted);
    }

}
