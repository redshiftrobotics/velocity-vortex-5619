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

    public void dt(String text) {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }


    public void init() {
        dt("Debug.class Selected!");
        dt("Init Loading...");


        dt ("Init Loaded!");
    }





    @Override
    public void loop() {




    }



}

