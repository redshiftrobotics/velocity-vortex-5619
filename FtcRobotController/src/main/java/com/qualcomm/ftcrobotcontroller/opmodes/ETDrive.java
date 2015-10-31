package com.qualcomm.ftcrobotcontroller.opmodes;

import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;



/**
 * Created by Eric Golde on 10/17/2015.
 */
public class ETDrive extends OpMode {

    String teleConvert;
    int teleInt = 3;





    DcMotor frontLeftMotor; //FRONT LEFT
    DcMotor frontRightMotor; //FRONT RIGHT
    DcMotor backLeftMotor; //BACK LEFT
    DcMotor backRightMotor; //BACK RIGHT
    DcMotor extendMotor1; // arm
    DcMotor extendMotor2; // arm
    Servo clamp1;
    Servo clamp2;
    Servo hit1;
    Servo hit2;



    public void dt(String text)
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }
    public void ct(String what, String text)
    {
        telemetry.addData(what, text);
    }

    public void printControls()
    {
        dt("Controller #1");
        dt("    left joy: left side");
        dt("    right joy: right side");
        dt("");
        dt("Controller #2");
        dt("    left joy: Extend left claw assmbly");
        dt("    right joy: Extend left claw assmbly");
        dt("    left joy bttn: open / close left claw");
        dt("    right joy bttn: open / close right claw");
        dt("    lb: left servo open /close");
        dt("    rb: right servo open / close");
        dt("");

    }

    public void init() {

        dt("Tank Drive Selected!");
        dt("Init Loading...");


        frontLeftMotor = hardwareMap.dcMotor.get("left1");
        frontRightMotor = hardwareMap.dcMotor.get("right1");
        backLeftMotor = hardwareMap.dcMotor.get("left2");
        backRightMotor = hardwareMap.dcMotor.get("right2");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
       // backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        // frontRightMotor.setDirection(DcMotor.Direction.REVERSE); //CHANGED

        extendMotor1 = hardwareMap.dcMotor.get("extend1");
        extendMotor2 = hardwareMap.dcMotor.get("extend2");

        clamp1 = hardwareMap.servo.get("clamp1");
        clamp2 = hardwareMap.servo.get("clamp2");

        hit1 = hardwareMap.servo.get("hit1");
        hit2 = hardwareMap.servo.get("hit2");




        dt ("Init Loaded!");
        //dt ("Controls:");
       // printControls();




    }

///////////////////////////////////////////////////
    boolean lastBttnStateOpenLeft = false;
    boolean toggleStateOpenLeft = false;
    public boolean toggleGrippersOpenLeft()
    {
        if(gamepad2.left_stick_button && !lastBttnStateOpenLeft)
        {
            toggleStateOpenLeft = !toggleStateOpenLeft;
        }
        lastBttnStateOpenLeft = gamepad2.left_stick_button;
        return toggleStateOpenLeft;
    }
///////////////////////////////////////////////////////////////
    boolean lastBttnStateOpenRight = false;
    boolean toggleStateOpenRight = false;
    public boolean toggleGrippersOpenRight()
    {
        if(gamepad2.right_stick_button && !lastBttnStateOpenRight)
        {
            toggleStateOpenRight = !toggleStateOpenRight;
        }
        lastBttnStateOpenRight = gamepad2.right_stick_button;
        return toggleStateOpenRight;
    }
/////////////////////////////////////////////////////////////////
    boolean lastBttnStateHitServoLeft = false;
    boolean toggleStateHitServoLeft = false;
    public boolean toggleHitServoLeft()
    {
        if(gamepad2.left_bumper && !lastBttnStateHitServoLeft)
        {
            toggleStateHitServoLeft = !toggleStateHitServoLeft;
        }
        lastBttnStateHitServoLeft = gamepad2.left_bumper;
        return toggleStateHitServoLeft;
    }
/////////////////////////////////////////////////////////////////////
    boolean lastBttnStateHitServoRight = false;
    boolean toggleStateHitServoRight = false;
    public boolean toggleHitServoRight()
    {
        if(gamepad2.right_bumper && !lastBttnStateHitServoRight)
        {
            toggleStateHitServoRight = !toggleStateHitServoRight;
        }
        lastBttnStateHitServoRight = gamepad2.right_bumper;
        return toggleStateHitServoRight;
    }
    @Override
    public void loop() {

//================[Driver]=========================================================================
//get the values from the gamepads
        //note: pushing the stick all the way up returns -1,
        //so we need to reverse the y values
        float xValue = -gamepad1.left_stick_y;
        float yValue = -gamepad1.right_stick_y;


        //calculate the power needed for each motor


        //clip the power values so that it only goes from -1 to 1
        xValue = Range.clip(xValue, -1, 1);
       yValue = Range.clip(yValue, -1, 1);

        //set the power of the motors with the gamepad values
        frontLeftMotor.setPower(-xValue); //
        frontRightMotor.setPower(-yValue); //

        backLeftMotor.setPower(xValue);
        backRightMotor.setPower(yValue);

//=================================================================================================

//====================[Gamepad2]===================================================================

        //this is for making the controller #2 beable to do the sliders
        float extendValueLeft = -gamepad2.left_stick_y;
        float extendValueRight = -gamepad2.right_stick_y;



        extendValueLeft = Range.clip(extendValueLeft, -1, 1);
        extendValueRight = Range.clip(extendValueRight, -1, 1);


        extendMotor1.setPower(extendValueLeft);
        extendMotor2.setPower(extendValueRight);

////////////////////////////////////////////////////////////////////
        if(toggleGrippersOpenLeft() == true) {
            ct("LeftClaw: ", "Closed");
            clamp1.setPosition(0.75);
        }
        else
        {
            ct("LeftClaw: ", "Open");
            clamp1.setPosition(0);
        }

        if(toggleGrippersOpenRight() == true)
        {
            ct("RightClaw: ", "Closed");
            clamp2.setPosition(0.75);
        }
        else
        {
            ct("RightClaw: ", "Open");
            clamp2.setPosition(0);
        }
////////////////////////////////////////////////////
        if(toggleHitServoLeft() == true) {
            ct("Hit1", "Open");
            hit1.setPosition(0.50);
        }
        else
        {
            ct("Hit1", "Closed");
            hit1.setPosition(0);
        }

        if(toggleHitServoRight() == true)
        {
            ct("Hit2", "Open");
            hit2.setPosition(0.50);
        }
        else
        {
            ct("Hit2", "Closed");
            hit2.setPosition(0);
           // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        }



/////////////////////////////////////////////




    }




}
