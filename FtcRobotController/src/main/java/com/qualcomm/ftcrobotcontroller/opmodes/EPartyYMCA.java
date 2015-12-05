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


    Random random = new Random();
    int maxStates = 7;

    double lastTime;
    double lastTime8;
    double lastTime100;

    boolean hasBeen12Sec = false;

    int s = 0;


    public void init() {

        super.init();


    }

    public void start() {
        dt("Playing Musixc");


        lastTime = getRuntime();

        mediaPlayer = MediaPlayer.create(FtcRobotControllerActivity.mainActivity, R.raw.ymca);
        mediaPlayer.start();


    }

    public void loop() {

        telemetry.addData("hasBeen12Sec", hasBeen12Sec);

        if (hasBeen12Sec) { //after 12 sec do this so it doesnt start untill something


            if (s == 0) {
                frontLeftMotor.setPower(1);
                frontRightMotor.setPower(1);
                backLeftMotor.setPower(1);
                backRightMotor.setPower(1);
            } else if (s == 1) {
                double d = random.nextDouble() * 0.5;
                hit1.setPosition(d);
            } else if (s == 2) {
                double e = random.nextDouble() * 0.5 + 0.5;
                hit2.setPosition(e);
            } else if (s == 3) {
                double f = random.nextDouble() * 0.5;
                lift1.setPosition(f);
            } else if (s == 4) {
                double g = random.nextDouble() * 0.5 + 0.5;
                lift2.setPosition(g);
            } else if (s == 5) {
                frontLeftMotor.setPower(1);
                backLeftMotor.setPower(1);
                frontRightMotor.setPower(-1);
                backRightMotor.setPower(-1);
            } else if (s == 6) {
                frontLeftMotor.setPower(-1);
                backLeftMotor.setPower(-1);
                frontRightMotor.setPower(1);
                backRightMotor.setPower(1);
            }

        }

        double time100 = this.getRuntime();
        if (time100 - lastTime100 > 100) {

            s = random.nextInt(maxStates);
            lastTime100 = time;
        }

        double time = this.getRuntime();
        if (time - lastTime > 1000) {
            everySec();

            lastTime = time;
        }
        double after8sec = this.getRuntime();

        if (after8sec - lastTime8 > 1) {
            if (hasBeen12Sec == false) {
                hasBeen12Sec = true;
            }
            lastTime8 = after8sec;
        }


    }

    public void everySec() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void stop() {
        mediaPlayer.stop();
        everySec();
        resetHitLeft();
        resetHitRight();
        resetArmHeightLeft();
        resetArmHeightRight();
        resetArmLeft();
        resetArmRight();
    }


}




