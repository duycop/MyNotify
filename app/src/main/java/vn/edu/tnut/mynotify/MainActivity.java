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
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    NotificationHelper notificationHelper;
    DynamicNotificationHelper dynamicNotificationHelper;

    private WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationHelper=new NotificationHelper(this);
        //dynamicNotificationHelper=new DynamicNotificationHelper(this);
        //DangKyService();

        //ánh xạ đối tượng từ layout vào biến
        //webView1 = findViewById(R.id.webView1);

        //tạo đối tượng từ lệnh new: ko gắn lên giao diện
        webView1=new WebView(this);

        //ko có trên layout: nên ko cần ẩn đi
        //webView1.setVisibility(View.GONE);

        // Thiết lập các cài đặt cho WebView
        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true); // Cho phép thực thi JavaScript (nếu cần)

        // Thêm Interface vào WebView

        webView1.addJavascriptInterface(this, "DuyCop");

        // Nạp URL vào WebView
        webView1.loadUrl("https://maifood.duckdns.org/56kmt/");
    }

    // Hàm này sẽ được gọi từ Interface khi có dữ liệu được gửi từ JavaScript
    @JavascriptInterface
    public void jsThongBao(String data) {
        // Xử lý dữ liệu nhận được từ WebView tại đây
        // data chứa thông tin được gửi từ JavaScript

        //nhận đc chuỗi, thì đem nó thành thông báo
        //thông báo: Nội dung từ API (động)
        showNotification("API Alarm", data);
    }

    @JavascriptInterface
    public void jsThongBao2(String tieude,String data) {
        // Xử lý dữ liệu nhận được từ WebView tại đây
        // data chứa thông tin được gửi từ JavaScript

        //nhận đc chuỗi, thì đem nó thành thông báo
        //thông báo: Nội dung từ API (động)
        showNotification(tieude, data);
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
        }else if (id == R.id.nut4) {
            showToast("Button 4 clicked");
            //this.dynamicNotificationHelper.fetchAndShowNotification(100);
            MakeSound(R.raw.shotgun_firing);
        }

    }

    // Hàm hiển thị thông báo Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    void showNotification(String title, String message){
        notificationHelper.showNotification(title,message);
        //dynamicNotificationHelper.showNotification(title,message);
    }

    void MakeSound(int res_raw_mp3){
        String soundUri = "android.resource://" + getPackageName() + "/" + res_raw_mp3;
        //NotificationSoundService.playSound(soundUri);
        Intent serviceIntent = new Intent(this, MyBackgroundService.class);
        serviceIntent.putExtra("AUDIO_FILE_PATH", soundUri);
        startService(serviceIntent);
    }



}