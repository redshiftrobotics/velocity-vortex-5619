package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class EFDrive extends OpMode {


    String teleConvert;
    int teleInt = 3;


    public void dt(String text)
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }

    public void init() {


        dt("init loaded");
        dt ("Final Drive Loaded:");


    }



    @Override
    public void loop() {



    }



}
