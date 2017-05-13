package com.vishnu.notificationmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InstallReceiver extends BroadcastReceiver {
    public InstallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        System.out.println("receiver called");
        intent.setClass(context, DialogActivity.class);
     //  intent.setComponent(new ComponentName(context.getPackageName(),DialogActivity.class.getName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
