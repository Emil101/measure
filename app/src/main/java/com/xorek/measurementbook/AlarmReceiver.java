package com.xorek.measurementbook;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    String name;
    int i;



    @Override
    public void onReceive(Context context, Intent intent) {

        name = intent.getStringExtra("name");
        Log.d("Name:",name );
        i = intent.getIntExtra("id", 0);
        Log.d("I:", String.valueOf(i));





        createNotification(context, "Measurement Book", "Due Delivery for Client "+name.toUpperCase()+".", "Delivery Alert");


    }

    private void createNotification(Context context, String msg, String msgText , String alert) {

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MeasurementBook.class), 0);



        NotificationCompat.Builder nBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.logo_icon))
                .setContentTitle(msg)
                .setContentText(msgText)
                .setTicker(alert);

        nBuilder.setContentIntent(notificIntent);
        nBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        nBuilder.setAutoCancel(true);

        NotificationManager nNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nNotification.notify(i,nBuilder.build());


    }
}
