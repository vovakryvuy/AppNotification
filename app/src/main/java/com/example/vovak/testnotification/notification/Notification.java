package com.example.vovak.testnotification.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.vovak.testnotification.MainActivity;
import com.example.vovak.testnotification.R;

/**
 * Created by vovak on 28.03.2018.
 */

public class Notification  {
    private Context context;
    private int id;
    final NotificationCompat.Builder mBuilder;

    public Notification(Context context,int id){
        this.context = context;
        this.id = id;

        Intent  intent = new Intent(context, MainActivity.class);
        intent.setAction(MainActivity.NOTIFICATION_ACTION);
        intent.putExtra("id",id);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,111,
                        intent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context,"000")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle(context.getResources()
                        .getString(R.string.string_notification_title))
                .setContentText(context.getResources()
                        .getString(R.string.string_notification_text)+" "+id)
                .setPriority(android.app.Notification.PRIORITY_MAX)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(false);

        generateNotification(id);
    }

    private void generateNotification(int id){
        if(mBuilder!=null && id != 0){
            android.app.Notification notification =  mBuilder.build();
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(id,notification);
            Log.d("TAG", "generateNotification: id  = "+id);
        }
    }

    public void deleteNotification(int id){
        NotificationManager notificationMgr =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationMgr != null;
        notificationMgr.cancel(id);
        Log.d("TAG", "deleteNotification: id = "+id);
    }
}
