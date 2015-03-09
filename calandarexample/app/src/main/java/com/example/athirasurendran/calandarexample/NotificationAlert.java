package com.example.athirasurendran.calandarexample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.athirasurendran.calandarexample.MainActivity;
import com.example.athirasurendran.calandarexample.R;

/**
 * Created by Athira Surendran on 3/1/2015.
 */
public class NotificationAlert extends Activity {
    private static final int NOTIFY_ME_ID = 1337;

    private NotificationManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationCompat.Builder m = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.calendar_icon).setContentTitle("Event").setContentText("Hello world");
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /*********** Create notification ***********/
        int mID = 123;
        mManager.notify(mID, m.build());
    }
}
