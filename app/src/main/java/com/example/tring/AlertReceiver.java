package com.example.tring;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.provider.Settings;

import android.os.Handler;
import java.util.logging.LogRecord;

public class AlertReceiver extends BroadcastReceiver {

    MediaPlayer mp;
    Vibrator vibrator;
    @Override
    public void onReceive(Context context, Intent intent) {
        int achosen = intent.getIntExtra("audiochosen" , 1);
        int secs = intent.getIntExtra("secs" , 30);
        boolean taskset = intent.getBooleanExtra("taskset",false);
        vibrator = (Vibrator)context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        if(achosen == 1){
            mp = MediaPlayer.create(context , R.raw.axwell);
        }
        else if(achosen == 2){
            mp = MediaPlayer.create(context , R.raw.bella);
        }
        else if(achosen == 3){
            mp = MediaPlayer.create(context , R.raw.rockstar);
        }
        mp.start();
        mp.setLooping(true);
        vibrator.vibrate(1500);
        if(!taskset) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mp.stop();
                }
            }, secs * 1000);
        }
    }

}
