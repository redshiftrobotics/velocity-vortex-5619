package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Tank drive teleop. Use Joystick #1
 */
@TeleOp(name="Tank Drive", group="TankChassis")
@Disabled
public class TankDriveTeleop extends TankChassisBase {
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        float leftValue = -gamepad1.left_stick_y;
        float rightValue = -gamepad1.right_stick_y;

        leftValue = Range.clip(leftValue, -TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());
        rightValue = Range.clip(rightValue, -TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());

        left.setPower(leftValue);
        right.setPower(rightValue);
    }
}
