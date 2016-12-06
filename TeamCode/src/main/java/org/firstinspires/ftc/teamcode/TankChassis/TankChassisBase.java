package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class TankChassisBase extends OpMode{
    DcMotor left;
    DcMotor right;
    DcMotor launcher;

    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        launcher = hardwareMap.dcMotor.get("launcher");

        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);
    }
}
