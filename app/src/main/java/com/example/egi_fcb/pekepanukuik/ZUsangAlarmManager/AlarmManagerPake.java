package com.example.egi_fcb.pekepanukuik.ZUsangAlarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by Egi FCB on 25/09/2016.
 */

public class AlarmManagerPake extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

        //masukan kode
        Toast.makeText(context, "Alarm..........", Toast.LENGTH_LONG).show();
        wakeLock.release();
    }

    public void setAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerPake.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pendingIntent);
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, AlarmManagerPake.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}