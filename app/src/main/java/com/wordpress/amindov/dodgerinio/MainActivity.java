package com.wordpress.amindov.dodgerinio;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final String TAG = getClass().getName();
    public final static boolean DEBUG = false;

    private GameView gameView;
    private MediaPlayer soundTrackPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        soundTrackPlayer = MediaPlayer.create(this, R.raw.dodge_sndtrack);
        soundTrackPlayer.setVolume(1.0f,1.0f);
        soundTrackPlayer.setLooping(true);
        soundTrackPlayer.start();

        Block.loadBlocks(getResources());
        gameView = new GameView(this, getPreferences(0));
        gameView.changeState(new MainMenuState(gameView));
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        soundTrackPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        soundTrackPlayer.stop();
    }

    public void showToast(final String toast) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
