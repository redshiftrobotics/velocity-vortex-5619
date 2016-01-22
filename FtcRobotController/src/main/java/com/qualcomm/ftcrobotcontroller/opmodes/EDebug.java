package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 1/9/2016.
 */
public class EDebug extends OpMode {
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    DcMotor left;
    DcMotor right;

    boolean lastBttnStateHitServoLeft = false;
    boolean toggleStateHitServoLeft = false;




    @Override
    public void init() {

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        left.setDirection(DcMotor.Direction.REVERSE);
        right.setDirection(DcMotor.Direction.FORWARD);

    }

    @Override
    public void start() {


    }

    @Override
    public void loop() {

        if(gamepad1.a)
        {
            telemetry.addData("Key", "A");
        }
        else if(gamepad1.b)
        {
            telemetry.addData("Key", "B");
            right();
        }
        else if(gamepad1.y)
        {
            telemetry.addData("Key", "Y");
        }
        else if(gamepad1.x)
        {
            telemetry.addData("Key", "X");
            left();
        }
        else
        {
            telemetry.addData("Key", "-");
            s();
        }

    }

    public void left()
    {
        right.setPower(1);
        left.setPower(0.5);
    }
    public void right()
    {
        left.setPower(1);
        right.setPower(0.5);
    }

    public void s()
    {
        left.setPower(0);
        right.setPower(0);
    }
}


