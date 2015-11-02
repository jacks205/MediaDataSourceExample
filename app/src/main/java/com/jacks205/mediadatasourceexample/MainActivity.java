package com.jacks205.mediadatasourceexample;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    MediaPlayer mp;
    boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surface);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        mp = new MediaPlayer();
        isPaused = false;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mp.setDisplay(holder);
            if(!isPaused) {
                VideoDataSource dataSource = new VideoDataSource();
                dataSource.downloadVideo(new VideoDownloadListener() {
                    @Override
                    public void onVideoDownloaded() {
                        mp.prepareAsync();
                    }

                    @Override
                    public void onVideoDownloadError(Exception e) {
                        Log.d("MainActivity", e.toString());
                    }
                });
                mp.setDataSource(dataSource);
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
            }else {
                mp.start();
            }
            isPaused = false;
        } catch (IllegalStateException is){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mp.isPlaying() && !isPaused) {
            mp.pause();
            isPaused = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp != null) {
            mp.stop();
            mp.release();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
