package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/*
 * Tank arcade drive teleop. Use Joystick #1
 */

@TeleOp(name="ArcadeDrive", group="TankChassis")
public class ArcadeDriveTeleop extends TankChassisBase {
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {

        float xValue = gamepad1.left_stick_x;
        float yValue = -gamepad1.left_stick_y;

        float leftPower = yValue + xValue;
        float rightPower = yValue - xValue;

        leftPower = Range.clip(leftPower, -TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());
        rightPower = Range.clip(rightPower, -TankChassisConfig.motorSpeed.getValueFloat(), TankChassisConfig.motorSpeed.getValueFloat());

        left.setPower(leftPower);
        right.setPower(rightPower);
    }
}
