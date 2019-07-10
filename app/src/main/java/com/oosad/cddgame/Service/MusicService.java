package com.oosad.cddgame.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.oosad.cddgame.R;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    public void onCreate() {

        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.doudizhu);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);

        if (!mediaPlayer.isPlaying()) {

            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        return null;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();

        mediaPlayer.release();
    }


    /**
     * ????
     */
    public class MyMusicBinder extends Binder {

        public MusicService getService() {
            return MusicService.this;
        }
    }
}
