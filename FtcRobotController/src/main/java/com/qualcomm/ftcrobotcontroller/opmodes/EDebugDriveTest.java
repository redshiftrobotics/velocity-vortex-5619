package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class EDebugDriveTest extends EOpModeBase {



    public void init()
    {
        dt("DRIVE TEST (DEBUG)");
        super.init(); //calls the init funtion in EOpModeBase.class

        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


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
        frontLeftMotor.setPower(xValue); //
        frontRightMotor.setPower(yValue); //

        ct("-x", Float.toString(xValue));
        ct("-y", Float.toString(yValue));
        ct("GPx", Float.toString(gamepad1.left_stick_y));
        ct("GPy", Float.toString(gamepad1.right_stick_y));
        //back postive


//=================================================================================================

//====================[Gamepad2]===================================================================

        //this is for making the controller #2 beable to do the sliders
        float extendValueLeft = -gamepad2.left_stick_y;




        extendValueLeft = Range.clip(extendValueLeft, -1, 1);



        //extendMotor1.setPower(extendValueLeft);
        //extendMotor2.setPower(extendValueLeft);


////////////////////////////////////////////////////////////////////


    }

}
