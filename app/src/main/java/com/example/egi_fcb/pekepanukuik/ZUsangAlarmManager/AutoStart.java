package com.example.egi_fcb.pekepanukuik.ZUsangAlarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Egi FCB on 25/09/2016.
 */

public class AutoStart extends BroadcastReceiver {

    AlarmManagerPake alarmManagerPake = new AlarmManagerPake();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            alarmManagerPake.setAlarm(context);
        }
    }
}
