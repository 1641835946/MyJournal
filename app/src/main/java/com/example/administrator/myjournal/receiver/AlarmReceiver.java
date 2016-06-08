package com.example.administrator.myjournal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.myjournal.service.BootService;

/**
 * Created by Administrator on 2016/6/4.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent bootIntent = new Intent();
        //bootIntent.setAction("com.example.administrator..myjournal.service.BootService");
        Intent bootIntent = new Intent(context, BootService.class);
        context.startService(bootIntent);
    }

}
