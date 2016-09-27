package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 9/20/2016.
 */

@TeleOp(name="ArcadeDrive", group="WTD")
public class ArcadeDrive extends DriveOPBase{

    DcMotor left;
    DcMotor right;

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void start(){

    }

    @Override
    public void loop() {

        float xValue = gamepad1.left_stick_x;
        float yValue = -gamepad1.left_stick_y;

        float leftPower = yValue + xValue;
        float rightPower = yValue - xValue;

        leftPower = Range.clip(leftPower, -DriveOPBase.motorSpeed.getValueFloat(), DriveOPBase.motorSpeed.getValueFloat());
        rightPower = Range.clip(rightPower, -DriveOPBase.motorSpeed.getValueFloat(), DriveOPBase.motorSpeed.getValueFloat());

        left.setPower(leftPower);
        right.setPower(rightPower);
    }

    @Override
    public void stop(){

    }
}
