package com.vishnu.notificationmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by VISHNUPRASAD on 26/03/16.
 */
public class AppUninstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(intent.getBooleanExtra(Intent.EXTRA_REPLACING,false));
        if(!intent.getBooleanExtra(Intent.EXTRA_REPLACING,false))
        {
            context.getSharedPreferences("blocked_apps",0).edit().remove(intent.getData().getSchemeSpecificPart()).apply();
        }
    }
}
