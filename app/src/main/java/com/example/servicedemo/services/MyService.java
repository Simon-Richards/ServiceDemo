package com.example.servicedemo.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.servicedemo.notifications.NotificationChannelManager;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private final IBinder binder = new LocalBinder();
    public static final int ONGOING_NOTIFICATION_ID = 123;
    public static final int REPEAT_PERIOD = 4000;
    private TimerTask timerTask;
    private Timer serviceTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(ONGOING_NOTIFICATION_ID, buildNotification());
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.HOUR, -12);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                // We get the list of last photos only if
                performService();
            }
        };
        serviceTimer = new Timer();
        serviceTimer.schedule(timerTask, 1000, REPEAT_PERIOD);
        return START_STICKY;
    }

    private void performService() {
        Log.e(TAG, "Performing Foreground Service");
    }

    /**
     * Construct the initial notification that is displayed when live mission stream
     * has been enabled.
     *
     * @return a new Notification object that can be displayed
     */
    private Notification buildNotification() {
        String header = "WARNING:";
        String text = "There is an ongoing process underway";

        NotificationCompat.Builder nb = new NotificationCompat.Builder(getBaseContext(), NotificationChannelManager.CHANNEL_ONE_ID);
        nb.setContentTitle(header);
        nb.setContentText(text);

        // Set the right activity (probs main menu) to open on tap, if we've defined one
        //if (notificationResumeClass != null) {
        //    Intent intent = new Intent(this, notificationResumeClass);
        //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // ensure nothing old left on top of the activity stack
        //    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
        //            PendingIntent.FLAG_UPDATE_CURRENT);
//
        //    nb.setContentIntent(pendingIntent);
        //}

        int icon = android.R.drawable.ic_notification_overlay;
        if (icon != 0) {
            nb.setSmallIcon(icon);
        }

        nb.setWhen(System.currentTimeMillis());

        return nb.build();
    }
}
