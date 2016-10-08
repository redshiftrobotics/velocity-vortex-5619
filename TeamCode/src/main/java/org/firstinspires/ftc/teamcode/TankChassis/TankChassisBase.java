package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Eric Golde on 10/8/2016.
 */
public abstract class TankChassisBase extends OpMode{

    DcMotor left;
    DcMotor right;

    public void init() {
        //register all motors here
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        //set direction of motors here
        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);

    }

}
