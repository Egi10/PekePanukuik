package com.example.egi_fcb.pekepanukuik.ZUsangAlarmManager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Egi FCB on 25/09/2016.
 */

public class YourService extends Service {

    AlarmManagerPake alarmManagerPake= new AlarmManagerPake();

    public void onCreate(){
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flag, int startId){
        alarmManagerPake.setAlarm(this);
        return START_STICKY;
    }

    public void onStart(Intent intent, int startId){
        alarmManagerPake.setAlarm(this);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
