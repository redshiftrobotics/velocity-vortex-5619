package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 1/9/2016.
 */
public class ETankTeleop extends EOpModeBaseTank{
////////////////////////////////////////////////////////////
    double amountToSlowDownTheDrivingSpeed = 0.5;
    double amountToSlowDownTheArms = 0.5;
////////////////////////////////////////////////////////////

    @Override
    public void init()
    {
        dt("Tank Drive Selected!");
        super.init(); //calls the init funtion in EOpModeBase.class

        left.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        right.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    @Override
    public void start()
    {
        left.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        right.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        left.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        right.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    boolean lastBttnStateHitServoLeft = false;
    boolean toggleStateHitServoLeft = false;

    public boolean toggleHitServoLeft() {
        if (gamepad1.left_bumper && !lastBttnStateHitServoLeft) {
            toggleStateHitServoLeft = !toggleStateHitServoLeft;
        }
        lastBttnStateHitServoLeft = gamepad1.left_bumper;
        return toggleStateHitServoLeft;
    }

    boolean lastBttnStateHitServoRight = false;
    boolean toggleStateHitServoRight = false;

    public boolean toggleHitServoRight() {
        if (gamepad1.right_bumper && !lastBttnStateHitServoRight) {
            toggleStateHitServoRight = !toggleStateHitServoRight;
        }
        lastBttnStateHitServoRight = gamepad1.right_bumper;
        return toggleStateHitServoRight;
    }


    @Override
    public void loop()
    {
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

        left.setPower(xValue * amountToSlowDownTheDrivingSpeed);
        right.setPower(yValue * amountToSlowDownTheDrivingSpeed);

        float armValue = -gamepad2.left_stick_y;
        armValue = Range.clip(armValue, -1, 1);
        arm.setPower(armValue * amountToSlowDownTheArms);

        if (toggleHitServoLeft() == false) {
            ct("Hit1", "Closed");
            hit1.setPosition(1);
        } else {
            ct("Hit1", "Open");
            hit1.setPosition(0.50);
        }

        if (toggleHitServoRight() == false) {
            ct("Hit2", "Closed");
            hit2.setPosition(0);
        } else {
            ct("Hit2", "Open");
            hit2.setPosition(0.50);

        }



        ct("Left", left.getCurrentPosition());
        ct("Right", right.getCurrentPosition());
        ct("Arm", arm.getCurrentPosition());

    }
}
