package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.redshiftrobotics.util.TelementryUtil;

/**
 * Controls:
 *   JoyStick 1:
 *     Left Stick: Left side forward and backwards
 *     Right Stick: Right side forward and backwards
 *   JoyStick 2:
 *     A: Hold to fire launcher
 */

@TeleOp(name="Tank Drive", group="TankChassis")

public class TankDriveTeleop extends TankChassisBase {

    TelementryUtil util = new TelementryUtil(this);

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

        if(gamepad2.a){
            launcher.setPower(TankChassisConfig.fireSpeed.getValueDouble());
        }else{
            launcher.setPower(0);
        }
    }
}
