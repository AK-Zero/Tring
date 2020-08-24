package com.example.tring;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class myService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    PendingIntent pendingIntent1;
    AlarmManager alarmManager;
    Intent intent1;
    List<AlarmManager> alarmManagers = new ArrayList<>();
    AlertReceiver alert = new AlertReceiver();
    Calendar c, c1;
    int dchoose;
    boolean taskset;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        int hour = intent.getIntExtra("hour", 0);
        int minute = intent.getIntExtra("minute", 0);
        int[] days = intent.getIntArrayExtra("daysdata");
        dchoose = intent.getIntExtra("dayschosen", 0);
        int achoose = intent.getIntExtra("audiochosen", 1);
        int secs = intent.getIntExtra("secs", 30);
        taskset = intent.getBooleanExtra("taskset", false);
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        c1 = c;
        if (taskset) {
            SharedPreferences pref = getSharedPreferences("Tring", MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            edit.putLong("settime", c.getTimeInMillis());
            edit.putBoolean("taskset", taskset);
            edit.putInt("dchoose",dchoose);
            edit.apply();
        }
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                10, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("ALARM")
                .setContentText("Alarm set at " + String.format(Locale.getDefault(), "%02d:%02d", hour, minute) + " Hours..")
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).setOngoing(false)
                .build();
        startForeground(1, notification);
        intent1 = new Intent(this, alert.getClass());
        intent1.putExtra("audiochosen", achoose);
        intent1.putExtra("secs", secs);
        intent1.putExtra("taskset", taskset);
        pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (dchoose == 0) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent1);
        } else {
            for (int i = 0; i < days.length; i++) {
                if (days[i] == 1) {
                    c.set(Calendar.DAY_OF_WEEK, i + 1);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManagers.add(alarmManager);
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), i + 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 7 * 24 * 3600 * 1000, pendingIntent2);
                }
            }
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Calendar.getInstance().getTimeInMillis() > c1.getTimeInMillis()) {
            if (dchoose == 0) {
                if (!taskset) {
                    Toast.makeText(this, "Alarm has already started!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Alarm Cancelled!!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Further Alarms Cancelled..", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Alarm Cancelled..", Toast.LENGTH_LONG).show();
        }
        alarmManager.cancel(pendingIntent1);
        for (int i = 0; i < alarmManagers.size(); i++) {
            alarmManagers.get(i).cancel(pendingIntent1);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
