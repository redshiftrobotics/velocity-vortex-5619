package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 10/5/2015.
 */



/*
██████████████████████████████████████████████████████████████████████
 __   __       ___   .___________.___________. _______ .__   __. .___________. __    ______   .__   __.  __   __
|  | |  |     /   \  |           |           ||   ____||  \ |  | |           ||  |  /  __  \  |  \ |  | |  | |  |
|  | |  |    /  ^  \ `---|  |----`---|  |----`|  |__   |   \|  | `---|  |----`|  | |  |  |  | |   \|  | |  | |  |
|  | |  |   /  /_\  \    |  |        |  |     |   __|  |  . `  |     |  |     |  | |  |  |  | |  . `  | |  | |  |
|__| |__|  /  _____  \   |  |        |  |     |  |____ |  |\   |     |  |     |  | |  `--'  | |  |\   | |__| |__|
(__) (__) /__/     \__\  |__|        |__|     |_______||__| \__|     |__|     |__|  \______/  |__| \__| (__) (__)



Paste in code here to test as part of testing a opmode. After it works paste it in the EAuto.java

Look at ESimpleAuto.class for help!
█████████████████████████████████████████████████████████████████████████
 */
public class EAutoHigh extends EOpModeBase {


    int state;
    final int ENCODER_CPR = 1120; //ANDY MARK MOTOR DONT CHANGE
    final double GEAR_RATIO_WHEEL = 1;
    final int DIAMETER_DRIVEWEEL = 60; //in mm
    final double CIRCUMFRANCE_DRIVEWEEL = Math.PI * DIAMETER_DRIVEWEEL;
    final double POWER_DRIVE7 = 0.5;



    final int STATE_DRIVE_7_FEET = 1;
    final int STATE_TURN_90_LEFT = 2;


    public void init() {

        dt("EAutoHigh Selected!");
        dt("███ BROKEN ███");

        super.init();




        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    @Override
    public void start()
    {
        startDrive7();
    }
    @Override
    public void loop()
    {
        if(state == STATE_DRIVE_7_FEET)
        {
            loopDrive7();
        }
        else if(state == STATE_TURN_90_LEFT)
        {
            loopLeft90();
        }
    }

    /*
    ██████████████████████████████████████████████████████████████████████
     */
    final double DISTANCE_DRIVE7 = 2133.6; //in mm
    final double ROTATIONS_DRIVE7 = DISTANCE_DRIVE7 / CIRCUMFRANCE_DRIVEWEEL;
    final int COUNTS_DRIVE7 = (int)(ENCODER_CPR * ROTATIONS_DRIVE7 * GEAR_RATIO_WHEEL);

    public void startDrive7()
    {
        state = STATE_DRIVE_7_FEET;
        ct("State", "STATE_DO_CURVE");


        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontLeftMotor.setTargetPosition(COUNTS_DRIVE7);
        frontRightMotor.setTargetPosition(COUNTS_DRIVE7);
        backLeftMotor.setTargetPosition(COUNTS_DRIVE7);
        backRightMotor.setTargetPosition(COUNTS_DRIVE7);

        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);


        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);



        frontLeftMotor.setPower(POWER_DRIVE7);
        frontRightMotor.setPower(POWER_DRIVE7);
        backLeftMotor.setPower(POWER_DRIVE7);
        backRightMotor.setPower(POWER_DRIVE7);
    }

    public void loopDrive7()
    {
        ct("Drive7",Integer.toString(COUNTS_DRIVE7));

        ct("CountsLeftFront", Integer.toString(frontLeftMotor.getCurrentPosition()));
        ct("CountsRightFront", Integer.toString(frontRightMotor.getCurrentPosition()));
        ct("CountsLeftBack", Integer.toString(backLeftMotor.getCurrentPosition()));
        ct("CountsRightBack", Integer.toString(backRightMotor.getCurrentPosition()));



        int ENCODER_POS_DRIVE7 = frontLeftMotor.getCurrentPosition();
        if(ENCODER_POS_DRIVE7 >= COUNTS_DRIVE7)
        {
            startLeft90();
        }
    }

    ////////////////////////////

    public void startLeft90()
    {
        state = STATE_TURN_90_LEFT;

        dt("DONE!");
        ct("State", "STATE_TRIGGER_MADDY_OP_MODE");

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void loopLeft90()
    {
        dt("STATE_TRIGGER_MADDY_OP_MODE");
    }

}
