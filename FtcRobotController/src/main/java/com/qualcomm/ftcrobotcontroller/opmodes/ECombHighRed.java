package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 11/14/2015.
 */
public class ECombHighRed extends EOpModeBase {



    int Estate;
    final int ENCODER_CPR = 1120; //ANDY MARK MOTOR DONT CHANGE
    final double GEAR_RATIO_WHEEL = 1;
    final int DIAMETER_DRIVEWEEL = 60; //in mm
    final double CIRCUMFRANCE_DRIVEWEEL = Math.PI * DIAMETER_DRIVEWEEL;
    final double POWER_DRIVE7 = 0.5;



    final int STATE_DRIVE_7_FEET = 1;
    final int STATE_TURN_90_LEFT = 2;

    enum mountainStates {beginning, forwardDrive, extendArms, catchArmOnBar, pullUp, pullingUp, stop, extendingArms}
    mountainStates state;

    boolean armsOut = false;


    long StartTime;
    long TimeElapsed;


    public void init() {

        dt("Red Team Selected. I really hope this works ~Eric");

        super.init();




        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);


    }

    @Override
    public void start()
    {
        startDrive7();
    }
    @Override
    public void loop()
    {
        if(Estate == STATE_DRIVE_7_FEET)
        {
            loopDrive7();
        }
        else if(Estate == STATE_TURN_90_LEFT)
        {
            loopLeft90();
        }
    }

    /*
    ██████████████████████████████████████████████████████████████████████
     */
    final double DISTANCE_DRIVE7 = 914.4; //in mm
    final double ROTATIONS_DRIVE7 = DISTANCE_DRIVE7 / CIRCUMFRANCE_DRIVEWEEL;
    final int COUNTS_DRIVE7 = (int)(ENCODER_CPR * ROTATIONS_DRIVE7 * GEAR_RATIO_WHEEL);

    public void startDrive7()
    {
        Estate = STATE_DRIVE_7_FEET;
        ct("State", "STATE_DO_CURVE");


        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontLeftMotor.setTargetPosition(COUNTS_DRIVE7);
        frontRightMotor.setTargetPosition(COUNTS_DRIVE7);
        backLeftMotor.setTargetPosition(COUNTS_DRIVE7);
        backRightMotor.setTargetPosition(COUNTS_DRIVE7);

        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);


        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);




        frontLeftMotor.setPower(POWER_DRIVE7 * 0.4);
        frontRightMotor.setPower(POWER_DRIVE7);
        backLeftMotor.setPower(POWER_DRIVE7 * 0.4);
        backRightMotor.setPower(POWER_DRIVE7);
    }

    public void loopDrive7()
    {
        ct("Drive7",Integer.toString(COUNTS_DRIVE7));

        ct("Left Arm", extendMotor1.getCurrentPosition());
        ct("Right Arm", extendMotor2.getCurrentPosition());

        ct("Front Left", frontLeftMotor.getCurrentPosition());
        ct("Front Right", frontRightMotor.getCurrentPosition());
        ct("Back Left", backLeftMotor.getCurrentPosition());
        ct("Back Right", backRightMotor.getCurrentPosition());

        // ct("lift1Pos", lift1Pos);
        //ct("lift2Pos", lift2Pos);



        int ENCODER_POS_DRIVE7 = frontRightMotor.getCurrentPosition();
        if(ENCODER_POS_DRIVE7 >= COUNTS_DRIVE7)
        {
            startLeft90();
        }
    }

    ////////////////////////////

    public void startLeft90()
    {
        Estate = STATE_TURN_90_LEFT;



        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        dt("Calling Maddy's Code Now!");
       //maddys init gets called here
        dt("Done Calling Code!");
    }

    public void loopLeft90()
    {
       //maddys loop gets called here
    }


    //////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////



//

    public void minit()
    {
        dt("STARTING MADDYS OP MODE");
        //StartTime = System.currentTimeMillis();
        state = mountainStates.beginning;

    }

    public void mloop()
    {

//        TimeElapsed = System.currentTimeMillis() - StartTime;
//
//
//        if (!armsOut && TimeElapsed> /*SET THIS FUCKING TIME*/0)
//        {
//            Estate = mountainStates.extendArms;
//            armsOut = true;
//        }
//

        switch (state)
        {
            case beginning:
                DoBeginning();
                break;
//            case forwardDrive:
//                DoForwardDrive();
//                break;
            case extendArms:
                DoExtendArms();
                break;
            case extendingArms:
                DoExtendingArms();
                break;
            case catchArmOnBar:
                DoCatchArmOnBar();
                break;
            case pullUp:
                DoPullUp();
                break;
            case pullingUp:
                DoPullingUp();
                break;
            case stop:
                DoStop();
                break;
        }
    }

    void DoBeginning()
    {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        telemetry.addData("State: ", "Beginning");
        state = mountainStates.extendArms;
    }

    // void DoForwardDrive()
//    {
//        telemetry.addData("State: ", "Forward Drive");
//    }

    void DoExtendArms()
    {
        //SET ARMS TO GOT SLIGHTLY ABOVE CHURRO (TEST VALUES)
        //TEST THIS SHIT
        lift1.setPosition(.7);
        lift2.setPosition(.2);

        moveLeftArmBlahInches(48);
        moveRightArmBlahInches(48);

        telemetry.addData("State: ", "Extend Arms");


        state= mountainStates.extendingArms;
    }

    void DoExtendingArms()
    {
        if (getLeftTapePos()>= 48 && getRightTapePos()>= 48)
        {
            state = mountainStates.catchArmOnBar;
        }
        telemetry.addData("State: ", "Extending Arms Currently");
    }

    void DoCatchArmOnBar()
    {
        //SET ARMS SLOWIY DOWN TILL THE CATCH CHURROS (TEST VALUES)
        //TEST THESE FUCKING VALUES
        lift1.setPosition(.6);
        lift2.setPosition(.3);
        //maybe add bool to check the postition???
        telemetry.addData("State: ", "Catch on arm bar");
        state =  mountainStates.pullUp;

    }

    void DoPullUp()
    {
        //pull up to (midzone??)
        //POSSIBLY CHANGE WHEEL SPEED/POWER
        moveRightArmBlahInches(-48);
        moveLeftArmBlahInches(-48);

        telemetry.addData("State:", "Do Pull Up");

        state = mountainStates.pullingUp;
    }

    void DoPullingUp()
    {
        telemetry.addData("State:", "Currently Doing Pull Up");
        if(getLeftTapePos()<= 5 && getRightTapePos()<= 5)
        {
            state = mountainStates.stop;
        }
    }

    void DoStop()
    {

        telemetry.addData("State: ", "Stopped");
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        extendMotor1.setPower(0);
        extendMotor2.setPower(0);
    }



}
