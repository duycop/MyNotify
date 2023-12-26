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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    NotificationHelper notificationHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationHelper=new NotificationHelper(this);
        //DangKyService();
    }

    public void showNotify(View view) {
        int id = view.getId();
        if (id == R.id.nut1) {
            showToast("Button 1 clicked");
            //notificationHelper.showNotification("Báo cháy", "Có cháy tại Zone 3");
            //notificationHelper.showVoice();
            MakeSound(R.raw.sniper_rifle);
        } else if (id == R.id.nut2) {
            showToast("Button 2 clicked");
            showNotification("SOS Fire Alarm", "Fire at Zone 3");
        } else if (id == R.id.nut3) {
            showToast("Button 3 clicked");
            showNotification("SOS Fire Alarm", "Fire at Zone 3");
            MakeSound(R.raw.shotgun_firing);
        }

    }

    // Hàm hiển thị thông báo Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    void showNotification(String title, String message){
        notificationHelper.showNotification(title,message);
    }

    void MakeSound(int res_raw_mp3){
        String soundUri = "android.resource://" + getPackageName() + "/" + res_raw_mp3;
        //NotificationSoundService.playSound(soundUri);
        Intent serviceIntent = new Intent(this, MyBackgroundService.class);
        serviceIntent.putExtra("AUDIO_FILE_PATH", soundUri);
        startService(serviceIntent);
    }



}