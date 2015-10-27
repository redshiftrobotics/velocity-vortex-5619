package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.qualcomm.robotcore.util.ElapsedTime;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;



//Rember to register opmode in FtcOpModeRegister.java !
public class EDebug extends OpMode {



    String teleConvert;
    int teleInt = 1; //start from




    public void init() {
        dt("Op mode loaded");

        /*
        if(buttn = false)
        {
        motor xyz -1.0
        }





         */

    }

    boolean lastBttnState = false;
    boolean toggleState = false;
    public boolean toggle ()
    {
        if(gamepad1.b && !lastBttnState)
        {
            toggleState = !toggleState;
        }
        lastBttnState = gamepad1.b;
        return toggleState;
    }

    public void dt(String text) {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }

    @Override
    public void loop() {


        if(toggle() == true)
        {
            telemetry.addData("Code ", "True");
        }
        else
        {
            telemetry.addData("Code ", "False");
        }
        if(gamepad1.b)
        {
            telemetry.addData("What ", "True");
        }
        else
        {
            telemetry.addData("What ", "False");
        }





    }



}

