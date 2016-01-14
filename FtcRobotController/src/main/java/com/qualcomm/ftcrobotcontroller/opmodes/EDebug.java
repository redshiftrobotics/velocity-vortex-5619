package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 10/5/2015.
 */





public class EDebug extends EOpModeBaseTank {



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



    }

    public void start()
    {

    }


    @Override
    public void loop()
    {





    }




}
