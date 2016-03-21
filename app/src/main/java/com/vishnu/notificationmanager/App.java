package com.vishnu.notificationmanager;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by VISHNUPRASAD on 19/03/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
