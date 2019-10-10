package com.samuelle.todolist.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.samuelle.todolist.R;
import com.samuelle.todolist.view.TodoMainActivity;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        String msgText = intent.getStringExtra("msgTxt").isEmpty() ? "Your task is done!" : intent.getStringExtra("msgTxt");
        String msgAlert = intent.getStringExtra("msgAlert");
        long id = intent.getLongExtra("id", 1);

        createNotification(context, id, msg, msgText, msgAlert);
    }

    public void createNotification(Context context, long id, String msg, String msgText, String msgAlert) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, TodoMainActivity.class), 0);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context, "tutorialspoint_01")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(msg)
                .setContentText(msgText)
                .setTicker(msgAlert);

        mbuilder.setContentIntent(notificationIntent);
        mbuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mbuilder.setAutoCancel(true);

        NotificationManager mNotificationManger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManger.notify((int) id, mbuilder.build());
    }
}
