package com.vishnu.notificationmanager;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.onesignal.OneSignal;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        OneSignal.startInit(getApplicationContext()).init();
    }
}
