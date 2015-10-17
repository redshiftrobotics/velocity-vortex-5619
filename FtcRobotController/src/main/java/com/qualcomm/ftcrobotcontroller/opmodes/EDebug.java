package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import android.widget.TextView;
import android.widget.Toast;



//Rember to register opmode in FtcOpModeRegister.java !
public class EDebug extends OpMode {

    Servo servo1;

    String teleConvert;
    int teleInt = 3;
    boolean locked = false;
    double speed = 1;
    int timer = 0;

    public void init() {


dt("Op mode loaded");


    }

    public void dt(String text)
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }

    @Override
    public void loop() {

        if (timer <= 2000)
        {
            timer++;
        }
        if (gamepad2.a == true && locked == false)
        {
            locked = true;
           // dt("A Pressed!");
            dt("Locked!");
            dt("Restart OP Mode to unlock");
        }
        if (gamepad2.a == true && locked == true)
        {
           //dt("A Pressed!");
           // dt("A is locked!");
        }

        if(gamepad2.right_bumper == true && speed == 1 && timer == 2000)
        {
           //speed = 0.9;
            timer = 0;
            dt("Every 2 seconds you should be able to pushthe button");
            dt(" ");

        }
        else if(gamepad2.right_bumper == true && speed == 0.9)
        {

        }
        //max 1
        //min 0.5



    }

}

