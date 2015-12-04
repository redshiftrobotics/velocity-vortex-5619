package com.qualcomm.ftcrobotcontroller.opmodes;

import android.media.MediaPlayer;
import android.net.Uri;
import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import java.io.File;
import java.util.Random;

/*This is nothing crazy. I got bord when i finished my homework and desided to take duncans idea to make the robot dance.
* I know he was joking around with me about doing this but i thought it was a funny idea.
* THIS BREAKS FTC RULES SO DONT USE THIS DURRING THE COMPITION BUT YOU CAN USE IT DIRRING PRATICE AND MAINTANCE
* this is also good just as a test everything op mode
* I present to you, the robot dancing to the space jam sount track :)*/

/**
 * Created by Eric Golde on 12/3/2015.
 */
public class EParty extends EOpModeBase {

    MediaPlayer mediaPlayer;
    Uri song = Uri.fromFile(new File("assets/Eric/party.mp3"));
    Random random = new Random();
    int maxStates = 7;

    double lastTime;
    boolean hasBeen12Sec = false;


    public void init()
    {
        super.init();
        mediaPlayer = MediaPlayer.create(FtcRobotControllerActivity.mainActivity, song);


    }
    public void start()
    {
        mediaPlayer.start();

        lastTime = getRuntime();
    }

    public void loop()
    {
        telemetry.addData("hasBeen12Sec", hasBeen12Sec);
        int s = random.nextInt(maxStates);
        if(hasBeen12Sec) { //after 12 sec do this so it doesnt start untill something


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

        double time  = this.getRuntime();
        if (time - lastTime > 1) {
            everySec();
            lastTime = time;
        }
        double after8sec  = this.getRuntime();
        if (after8sec - lastTime > 12) {
            if(hasBeen12Sec = false)
            {
                hasBeen12Sec = true;
            }
            lastTime = after8sec;
        }



    }

    public void everySec()
    {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void stop()
    {
        everySec();
        lift1.setPosition(0.6);
        lift2.setPosition(0.5);
        hit1.setPosition(0);
        hit2.setPosition(1);
    }



}
