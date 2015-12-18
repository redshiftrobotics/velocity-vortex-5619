package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 10/5/2015.
 */





public class EDebug extends EOpModeBase {



    public void instructions()
    {
        dt("-=Instructions=-");
        dt("When you Push A Both servos (arm) should reset to there correct position");
        dt("When you push B hit servos should reset");
        dt("When you push Y both arms should retract to there default start up POS");
        dt("Push X to test");
        dt("Right Trigger toggles Sync (Not Working)");
        dt("-==============-");
    }

    public void init() {

        dt("Debug Time!");

        super.init();

        instructions();

        extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

    }

    public void start()
    {
        extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }


    @Override
    public void loop()
    {


        if(gamepad1.a)
        {
            moveLeftArmBlahInches(5);
        }
        if(gamepad1.b)
        {
            moveLeftArmBlahInches(-5);
        }

        ct("Left Arm", extendMotor1.getCurrentPosition());



    }




}
