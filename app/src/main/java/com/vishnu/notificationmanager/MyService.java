package com.vishnu.notificationmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MyService extends Service {
    private WindowManager windowManager;
    RelativeLayout relativeLayout;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager =(WindowManager) this.getSystemService(WINDOW_SERVICE);
        relativeLayout = new RelativeLayout(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         //windowManager.addView();
        return super.onStartCommand(intent, flags, startId);
    }
}
