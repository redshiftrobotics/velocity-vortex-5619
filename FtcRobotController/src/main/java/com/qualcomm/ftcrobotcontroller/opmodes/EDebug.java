package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 10/5/2015.
 */


public class EDebug extends EOpModeBaseTank {


    public void instructions() {
        dt("-=Instructions=-");

        dt("-==============-");
    }

    @Override
    public void init() {
        dt("Debug Time!");
        super.init();
        instructions();
    }

    @Override
    public void start() {

    }


    @Override
    public void loop() {


    }


}
