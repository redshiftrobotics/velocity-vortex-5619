package com.qualcomm.ftcrobotcontroller.opmodes;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.ftcrobotcontroller.R;

import java.io.File;
import java.io.InputStream;
import java.util.Random;

/*
Yes. This does the YMCA.
 */

/**
 * Created by Eric Golde on 12/3/2015.
 */
public class EPartyYMCA extends EOpModeBase {

    MediaPlayer mediaPlayer;





    public void init() {

    }

    public void start() {
        mediaPlayer = MediaPlayer.create(FtcRobotControllerActivity.mainActivity, R.raw.ymca);
        mediaPlayer.start();
    }

    public void loop() {
    }

    public void stop() {
        mediaPlayer.stop();


    }
}




