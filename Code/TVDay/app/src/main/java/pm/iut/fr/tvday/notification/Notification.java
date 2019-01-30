package pm.iut.fr.tvday.notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.TextView;

import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.programmes.ProgrammeListActivity;

/**
 * Created by annam on 18/02/2018.
 * Permet de creer des notifications
 */

public class Notification {

    public static android.app.Notification createNotification(Activity activity){

        String CHANNEL_ID = "my_channel_01";
        TextView programmeChaine =  activity.findViewById(R.id.programme_titre_ch);
        CollapsingToolbarLayout programmeDesc = activity.findViewById(R.id.toolbar_layout_ch);

        final NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        //    favBDD.insertFav(programmeChaine.getTitle(),programmeTitre.getText(),1);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(activity, CHANNEL_ID)
                        .setSmallIcon(R.drawable.tele)
                        .setContentTitle(programmeDesc.getTitle())
                        .setContentText( programmeChaine.getText())
                        .setWhen(System.currentTimeMillis())
                        .setColor(Color.BLUE);

        Intent resultIntent = new Intent(activity, ProgrammeListActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
        stackBuilder.addParentStack(ProgrammeListActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        return mBuilder.build();
    }


    public static void scheduleNotification(android.app.Notification notification, int delay, Activity activity) {

        Intent notificationIntent = new Intent(activity, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
