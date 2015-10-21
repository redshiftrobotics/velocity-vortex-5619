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
    double wait = 0.2; //WAIT TIME

    double xPressed = 0;
    ElapsedTime runtime = new ElapsedTime();
    String startDate;
    double startDatePlusTwoSec = runtime.time();
    public void init() {


        dt("Op mode loaded");
        runtime.reset();
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());


    }
     //create empty time

    public boolean toggle ()
    {
        if (runtime.time() >= startDatePlusTwoSec) {
            boolean toggleSwitch = false;
            boolean btnup = true;
            startDatePlusTwoSec = runtime.time() + wait; //WAIT TIME
//===================================================================================
            if (gamepad1.right_bumper == true && btnup == true) {
                toggleSwitch = !toggleSwitch;
                btnup = false;


            } else if (gamepad1.right_bumper == true && btnup == false) {
                btnup = true;
            }



            return toggleSwitch;
        }
        return false;
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
            xPressed = xPressed + 0.1;
            dt("Speed: " + xPressed);
        }

        if(xPressed >= 1)
        {
            xPressed = 0;
        }



    }



}

