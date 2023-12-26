package vn.edu.tnut.mynotify;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.io.IOException;

public class MyBackgroundService extends Service {

    private MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Lấy đường dẫn từ Intent
        String audioFilePath = intent.getStringExtra("AUDIO_FILE_PATH");

        if (audioFilePath != null) {
            // Phát âm thanh từ đường dẫn
            playAudio(audioFilePath);
        }

        // Trả về giá trị START_STICKY để service tiếp tục chạy ngầm sau khi bị hủy
        return START_STICKY;
    }

    private void playAudio(String audioFilePath) {
        // Kiểm tra và giải phóng MediaPlayer nếu đang phát âm thanh
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }

        // Khởi tạo mới MediaPlayer và phát âm thanh
        //mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.reset();
            Uri uri = Uri.parse(audioFilePath);
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Thêm lắng nghe sự kiện để giải phóng MediaPlayer khi âm thanh kết thúc
        mediaPlayer.setOnCompletionListener(mp -> {
            stopSelf(); // Tự ngắt service khi âm thanh kết thúc
        });
    }

    @Override
    public void onDestroy() {
        // Giải phóng MediaPlayer khi service bị hủy
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        super.onDestroy();
    }
}
