package vn.edu.tnut.mynotify;

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
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class DynamicNotificationHelper {

    private static final String CHANNEL_ID = "DynamicAlert";
    private static final String CHANNEL_NAME = "DynamicAlert";
    private static final String CHANNEL_DESCRIPTION = "Dynamic Thông tin quan trọng";
    private static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;

    private final Context context;
    private final NotificationManager notificationManager;

    public DynamicNotificationHelper(Context context) {
        this.context = context;
        requestNotificationPermission(context);
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
        //Notification notification = builder.build();
        Notification notification = createNotification(title, message);
        if (notificationManager != null) {
            notificationManager.notify(2, notification);
        }
    }

    private Notification createNotification(String title, String message) {
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

    public static boolean isNotificationPermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Nếu phiên bản Android là Oreo (API level 26) trở lên, kiểm tra trạng thái quyền thông báo
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        } else {
            // Nếu phiên bản Android dưới Oreo, kiểm tra trạng thái quyền thông báo thông qua NotificationManager
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            return notificationManager.areNotificationsEnabled();
        }
    }

    public static void requestNotificationPermission(Context context) {
        if (!isNotificationPermissionGranted(context)) {
            // Nếu quyền thông báo chưa được cấp, chuyển người dùng đến màn hình cài đặt
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            } else {
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.fromParts("package", context.getPackageName(), null));
            }
            context.startActivity(intent);
        }
    }

    public static String get_json(URL url) {

//            String json = IOUtils.toString(url, Charset.forName("UTF-8"));
//            return new JSONObject(json);
        return "{testttt}";

    }

    public void fetchAndShowNotification(int id) {
        try {
            String str = "http://tms.tnut.edu.vn/api/?id=" + id;
            URI uri = new URI(str);
            URL url = uri.toURL();
            String json = get_json(url);
            Log.d("duycop", "test=" + url+ " json="+json);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
