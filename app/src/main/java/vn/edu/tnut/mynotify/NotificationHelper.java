package vn.edu.tnut.mynotify;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotificationHelper {

    private static final String CHANNEL_ID = "Alert";
    private static final String CHANNEL_NAME = "Alert";
    private static final String CHANNEL_DESCRIPTION = "Thông tin quan trọng";
    private static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;

    private final Context context;
    private final NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Tạo Notification Channel khi tạo instance của NotificationHelper
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
            channel.setDescription(CHANNEL_DESCRIPTION);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            Uri soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.voice321);

            channel.setSound(soundUri, audioAttributes);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void showNotification(String title, String message) {
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        Bitmap largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire);
//        long[] vibrate = {0, 100, 200, 300};
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_stat_local_fire_department)
//                .setColor(ContextCompat.getColor(context, R.color.notification_color)) // đặt màu sắc cho thông báo
//                .setLargeIcon(largeIconBitmap)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setAutoCancel(true)
//                .setVibrate(vibrate)
//                .setLights(Color.RED, 1000, 1000)
//                .setContentIntent(pendingIntent);

        //Notification notification = builder.build();
        Notification notification = createNotification(title,message);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }
    public Notification createNotification(String title, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Bitmap largeIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire);
        long[] vibrate = {0, 100, 200, 300};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_local_fire_department)
                .setColor(ContextCompat.getColor(context, R.color.notification_color)) // đặt màu sắc cho thông báo
                .setLargeIcon(largeIconBitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setVibrate(vibrate)
                .setLights(Color.RED, 1000, 1000)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        return notification;
    }

}
