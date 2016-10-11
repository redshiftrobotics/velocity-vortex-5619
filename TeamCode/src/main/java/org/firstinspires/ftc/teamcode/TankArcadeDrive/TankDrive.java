package org.firstinspires.ftc.teamcode.TankArcadeDrive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TankArcadeDrive.DriveOPBase;

/**
 * Created by Eric Golde on 9/20/2016.
 */

//example tank drive with config
@TeleOp(name="TankDrive", group="Drive")
@Disabled
public class TankDrive extends DriveOPBase {

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

        float leftValue = -gamepad1.left_stick_y;
        float rightValue = -gamepad1.right_stick_y;

        leftValue = Range.clip(leftValue, -DriveOPBase.motorSpeed.getValueFloat(), DriveOPBase.motorSpeed.getValueFloat());
        rightValue = Range.clip(rightValue, -DriveOPBase.motorSpeed.getValueFloat(), DriveOPBase.motorSpeed.getValueFloat());

        left.setPower(leftValue);
        right.setPower(rightValue);
    }

    @Override
    public void stop(){

    }
}
