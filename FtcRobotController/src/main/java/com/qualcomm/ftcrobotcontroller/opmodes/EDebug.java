package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 10/5/2015.
 */





public class EDebug extends EOpModeBase {




    public void init() {

        dt("Debug Time!");

        super.init();

    }

    @Override
    public void start()
    {
        dt("-=Instructions=-");
        dt("When you Push A Both servos (arm) should reset to there correct position");
        dt("When you push B hit servos should reset");
        dt("When you push Y both arms should retract to there default start up POS");
        dt("Push X to test");
        dt("Right Trigger toggles Sync (Not Working)");
        dt("-==============-");
    }
    @Override
    public void loop()
    {
        if(gamepad1.a)
        {
            dt("Resetting Arm Servos");
            resetArmHeightLeft();
            resetArmHeightRight();
        }
        if(gamepad1.b)
        {
            dt("Resetting Hit Servos");
            resetHitLeft();
            resetHitRight();
        }
        if(gamepad1.y)
        {
            dt("Reset Arm Lengh");
            resetArmLeft();
            resetArmRight();
        }
        if(gamepad1.x)
        {
            dt("Move Too Blah");
            dt("Left & Right arm should move 10 inches");
            moveLeftArmBlahInches(10);
            moveRightArmBlahInches(10);

            dt("Open Hit Servos");
            hit1.setPosition(0.50);
            hit2.setPosition(0.50);

            dt("Raise Arms");
            lift1.setPosition(0.6);
            lift2.setPosition(0.35);
        }

        ct("Left Arm", extendMotor1.getCurrentPosition());
        ct("Right Arm", extendMotor2.getCurrentPosition());

        ct("Front Left", frontLeftMotor.getCurrentPosition());
        ct("Front Right", frontRightMotor.getCurrentPosition());
        ct("Back Left", backLeftMotor.getCurrentPosition());
        ct("Back Right", backRightMotor.getCurrentPosition());



    }




}
