package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class EOpenHouseSoccerBot extends OpMode {

    String teleConvert;
    int teleInt = 0;

     DcMotor left;
     DcMotor right;

    //LegacyModule legacyModule1; //legacy module

    public void dt(String text) //Debug text multiline. Usefull for a lot of output debugging
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
        //toastShort(text); //this makes it so DT (Debug text Multiline) also uses toast. ONLY USE IF NESSASARRY
    }

    public void ct(String what, String text) //Debug text single line. Usefull for printing Estate changes
    {
        telemetry.addData(what, text);
    }

    public void init()
    {
        dt("Gabe's Open House Code Selected!");

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        //legacyModule1 = hardwareMap.legacyModule.get("Legacy Module 1");

        right.setDirection(DcMotor.Direction.REVERSE);
        left.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {


        float xValue = gamepad1.left_stick_y;
        float yValue = gamepad1.right_stick_y;



        xValue = Range.clip(xValue, -1, 1);
        yValue = Range.clip(yValue, -1, 1);

        //set the power of the motors with the gamepad values
        left.setPower(xValue); //
        right.setPower(yValue); //



    }
}
