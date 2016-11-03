package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.redshiftrobotics.util.Util;

/**
 * Tank drive teleop. Use Joystick #1
 */
@TeleOp(name="Tank Drive", group="TankChassis")
public class TankDriveTeleop extends TankChassisBase {
    Util util = new Util(this);
    @Override
    public void init() {
        super.init();
        util.writeLine("TankDrive ready!");
    }

    @Override
    public void loop() {
        float leftValue = -gamepad1.left_stick_y;
        float rightValue = -gamepad1.right_stick_y;

        leftValue = Range.clip(leftValue, -TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());
        rightValue = Range.clip(rightValue, -TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());

        left.setPower(leftValue);
        right.setPower(rightValue);

        if(gamepad1.a){
            launcher.setPower(1);
        }else{
            launcher.setPower(0);
        }
    }
}
