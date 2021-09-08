package com.example.azkar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;

import com.example.azkar.Activity.SplashActivity;

public class Notification_reciever extends BroadcastReceiver {
    Notification notification;
    private static final String CHANNEL_ID = "this.is.my.channelId";//you can add any id you want
    @Override
    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        Intent notificationIntent = new Intent(context, SplashActivity.class);//on tap this activity will open

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SplashActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);//getting the pendingIntent

        Notification.Builder builder = new Notification.Builder(context);//building the notification
        String action = intent.getAction();
        if (action.equals("morning")){
             notification = builder.setContentTitle("Demo Notification")
                    .setContentText("New Notification ..")
                    .setTicker("New Message Alert!")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent).build();
        }
        else{
            notification = builder.setContentTitle("Demo Notification night")
                    .setContentText("New Notification night ..")
                    .setTicker("New Message Alert! night")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent).build();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//below creating notification channel, because of androids latest update, O is Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "NotificationDemo", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
    }
}
