package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TankArcadeDrive.DriveOPBase;

/**
 * Created by Eric Golde on 10/8/2016.
 */

@TeleOp(name="Tank Drive", group="TankChassis")
public class TankDriveTeleop extends TankChassisBase {

    @Override
    public void init() {
        super.init(); //call this so that everything in the base class inits before so that we register the motors and everything
    }

    @Override
    public void start(){/*Not used yet.*/}

    @Override
    public void loop() {
        float leftValue = -gamepad1.left_stick_y;
        float rightValue = -gamepad1.right_stick_y;

        //clip the variables to be what the config says
        leftValue = Range.clip(leftValue, TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());
        rightValue = Range.clip(rightValue, TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());

        left.setPower(leftValue);
        right.setPower(rightValue);
    }
}
