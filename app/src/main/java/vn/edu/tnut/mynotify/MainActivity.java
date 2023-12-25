package vn.edu.tnut.mynotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Alert";
    private static final String CHANNEL_NAME = "Alert";
    private static final String CHANNEL_DESCRIPTION = "Thông báo quan trọng";
    private static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;

    private NotificationHelper notificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationHelper = new NotificationHelper(this);
    }



    public void showNotify(View view) {
        int id = view.getId();
        if (id == R.id.nut1) {
            showToast("Button 1 clicked");
            notificationHelper.showNotification("Thông báo mới", "Nội dung thông báo");
        } else if (id == R.id.nut2) {
            showToast("Button 2 clicked");
            notificationHelper.showNotification("New Message", "hello all");
        }
    }

    // Hàm hiển thị thông báo Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}