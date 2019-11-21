package com.google.android;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReciever  extends WakefulBroadcastReceiver {

    private static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

        MainActivity instance =MainActivity.getInstance();

        instance.setAlarmText("Alarm ! Walk up walk up!!!");

        Uri alarmUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        if (alarmUri==null){

            alarmUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone=RingtoneManager.getRingtone(context , alarmUri);
         ringtone.play();
         setRingtone(ringtone);
        sendNotification(context);
    }


    private void sendNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context , 0 , new Intent(context , MainActivity.class) , 0);

        NotificationCompat.Builder  notificationCompat= new NotificationCompat.Builder(context).setContentTitle("Alarm walking now")
                .setSmallIcon(R.drawable.ic_launcher_background).setStyle(new NotificationCompat.BigTextStyle().bigText("alarm"))
                .setContentText("alarm");
        notificationCompat.setContentIntent(pendingIntent);
        notificationManager.notify(1 , notificationCompat.build());
    }


    private  static void setRingtone(Ringtone ringtone){
       AlarmReciever.ringtone=ringtone;
    }

    public static  Ringtone getRingtone() {
        return ringtone;
    }
}
