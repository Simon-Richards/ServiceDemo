package com.example.servicedemo.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Starting in Android O, we get nice crashy crashes for notifications that don't have a channel set for them.
 */
public class NotificationChannelManager {
    private static final String TAG = "NotificationChannelMana";

    // Use this channel ID for all notifications. If you make any changes to the setup you need to modify the ID.. hence the number suffix :/
    public static String CHANNEL_ONE_ID = "com.example.servicedemo.001";
    public static String CHANNEL_ONE_NAME = "Demo Notifications";


    /**
     * Call this to set up our notification channel, we'll use it later for all notifications.
     * Call on Application startup.
     * @param context
     */
    public static void setupNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.i(TAG, "Calling setupNotificationChannel for CHANNEL_ONE_ID = " + CHANNEL_ONE_ID + ", name = " + CHANNEL_ONE_NAME);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, android.app.NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);

            notificationChannel.setSound(null, null);

            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            android.app.NotificationManager manager = (android.app.NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            manager.createNotificationChannel(notificationChannel);
        }
    }
}
