package com.vishnu.notificationmanager;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;


public class NotificationService extends NotificationListenerService {
Handler handler ;
    Gson gson= new Gson();
    Runnable runnable;
    SharedPreferences sharedPreferences ;
    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        System.out.println("listerner connected");
        handler = new Handler(Looper.getMainLooper());
        sharedPreferences = this.getSharedPreferences("notification_list",0);

    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationPosted(sbn, rankingMap);
        try {
            for (String a : sbn.getNotification().extras.keySet()) {
               // System.out.println(a);
            }
            System.out.println(getIntent(sbn.getNotification().contentIntent).toUri(0));
            Bundle extras = sbn.getNotification().extras;
            for(String a :extras.keySet())
            {
                System.out.println("key:"+a +" "+ extras.getString(a));
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", extras.getString("android.title"));
            jsonObject.put("message", extras.getString("android.text"));
            jsonObject.put("intentUri", getIntent(sbn.getNotification().contentIntent).toUri(0));
            sharedPreferences.edit().putString(String.valueOf(sharedPreferences.getAll().size()),jsonObject.toString()).apply();
            // showToast(sbn.getNotification().extras.keySet().toArray()[0]);
        }
        catch (Exception e)

        {
            e.printStackTrace();
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("listerner created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("listener destroyed");
    }



    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        for(String a :sbn.getNotification().extras.keySet())
        {
            System.out.println(a);
        }
        System.out.println(getIntent(sbn.getNotification().contentIntent).toUri(0));
        try {
            Intent.parseUri("#Intent;component=com.saavn.android/.NotActivity;S.messageId=1317;S.saavnLink=saavn%3A%2F%2Fplay%2Falbum%2FW44UgnK1JqQ_;end",0);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // sbn.getNotification().contentIntent
        // showToast(sbn.getNotification().extras.toString());
    }

    private void showToast(final String msg)
    {

        runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());

                Toast.makeText(NotificationService.this,msg,Toast.LENGTH_SHORT).show();
            }
        };
        handler.post(runnable);
    }
    public Intent getIntent(PendingIntent pendingIntent) throws IllegalStateException {
        try {
            Method getIntent = PendingIntent.class.getDeclaredMethod("getIntent");
            return (Intent) getIntent.invoke(pendingIntent);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
