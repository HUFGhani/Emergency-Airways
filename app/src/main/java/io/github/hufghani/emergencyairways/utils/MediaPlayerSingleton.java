package io.github.hufghani.emergencyairways.utils;

import android.app.Application;
import android.media.MediaPlayer;

public class MediaPlayerSingleton extends Application {

    static MediaPlayer instance;

    public static MediaPlayer getInstance() {

        if (instance == null)
        {
            instance = new MediaPlayer();

        }

        return instance;
    }


}