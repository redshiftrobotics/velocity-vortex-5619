package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class ETLow extends EOpModeBase {



    public void init()
	{
        dt("LOW Tank Drive Selected!");
		super.init(); //calls the init funtion in EOpModeBase.class

        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        extendMotor2.setDirection(DcMotor.Direction.FORWARD);
        extendMotor1.setDirection(DcMotor.Direction.REVERSE);
    }

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
        xValue = Range.clip(xValue, -1, 1); //-1 - 1
       yValue = Range.clip(yValue, -1, 1); //-1 - 1

        //set the power of the motors with the gamepad values
        frontLeftMotor.setPower(xValue / 2); //
        frontRightMotor.setPower(yValue / 2); //

        backLeftMotor.setPower(xValue / 2);
        backRightMotor.setPower(yValue / 2);

//=================================================================================================

//====================[Gamepad2]===================================================================

        //this is for making the controller #2 beable to do the sliders
        float extendValueLeft = -gamepad2.left_stick_y;




        extendValueLeft = Range.clip(extendValueLeft, -1, 1);



        extendMotor1.setPower(extendValueLeft);
        extendMotor2.setPower(extendValueLeft);


////////////////////////////////////////////////////////////////////

        if(toggleHitServoLeft() == true) {
            ct("Hit1", "Open");
            hit1.setPosition(1);
        }
        else
        {
            ct("Hit1", "Closed");
            hit1.setPosition(0.50);
        }

        if(toggleHitServoRight() == true)
        {
            ct("Hit2", "Open");
            hit2.setPosition(0);
        }
        else
        {
            ct("Hit2", "Closed");
            hit2.setPosition(0.50);

        }





    }

}
