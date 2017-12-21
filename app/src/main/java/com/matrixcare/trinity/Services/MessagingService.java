package com.matrixcare.trinity.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.matrixcare.trinity.Activities.MainActivity;
import com.matrixcare.trinity.R;

/**
 * Created by rlenoci on 12/20/2017.
 */

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().size() > 0)
        {
            Log.d(TAG, "Message data: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData().get("body"));
        }

        if(remoteMessage.getNotification() != null)
        {
            Log.d(TAG, "Message body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }


    private void sendNotification(String body) {
        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder;
        String NOTIFICATION_CHANNEL_ID = "0";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Requests", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Caregiver Request Notifications");
            notificationManager.createNotificationChannel(notificationChannel);


            notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Caregiver Request")
                    .setContentText(body)
                    .setContentIntent(pendingIntent);
        }
        else
        {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Caregiver Request")
                    .setContentText(body)
                    .setContentIntent(pendingIntent);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}
