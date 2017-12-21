package com.matrixcare.trinity.Services;

import android.app.Notification;
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

import org.joda.time.DateTime;

import java.util.Map;

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
            Map<String, String> data = remoteMessage.getData();
            String ClientId = data.get("FirstName");
            String FirstName = data.get("FirstName");
            String LastName = data.get("LastName");
            String StartTime = data.get("StartTime");
            String Address1 = data.get("Address1");
            String Address2 = data.get("Address2");
            String City = data.get("City");
            String StateOrProvince = data.get("StateOrProvince");
            String PostalCode = data.get("PostalCode");
            String Country = data.get("Country");

            String smallMessage = "Open Shift at " + getPrintedDate(StartTime);
            String bigMessage = "Time: " +  getPrintedDate(StartTime) +
                    "\nClient: " + FirstName + " " + LastName +
                    "\nAddress: " + Address1 + " " + Address2 + " " + City + ", " + StateOrProvince;

            sendNotification(smallMessage, bigMessage);
        }

        if(remoteMessage.getNotification() != null)
        {
            Log.d(TAG, "Message body: " + remoteMessage.getNotification().getBody());
            //sendNotification(remoteMessage.getNotification().getBody());
        }
    }




    private void sendNotification(String smallMessage, String bigMessage) {
        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

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
                    .setContentText(smallMessage)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(bigMessage))
                    .addAction (0, "Accept", pendingIntent)
                    .addAction (0, "Dismiss", null);
        }
        else
        {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Caregiver Request")
                    .setContentText(smallMessage)
                    .setContentIntent(pendingIntent);
        }
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }

    public String getPrintedDate(String dateTimeString) {
        DateTime dateTime = DateTime.parse(dateTimeString);
        return dateTime.toString("MM/dd/yyyy K:mm a");
    }


    }
