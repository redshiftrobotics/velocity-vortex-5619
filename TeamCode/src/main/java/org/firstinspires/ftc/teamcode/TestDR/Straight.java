package org.firstinspires.ftc.teamcode.TestDR;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.redshiftrobotics.DeadReckoning.DRMap;

/**
 * Created by Eric Golde on 10/22/2016.
 */

@Autonomous(name="Straight", group="DR")
public class Straight extends OpMode {

    public DRMap dRStraight = new DRMap(DRMap.TETRIX_ENCODER_CPR, 1, 7.7);

    DcMotor left;
    DcMotor right;

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        dRStraight.addPath(50);
    }

    @Override
    public void start(){
        dRStraight.run(left, right);
    }

    @Override
    public void loop() {
        telemetry.addData("left", left.getCurrentPosition() + "/" + left.getTargetPosition());
        telemetry.addData("right", right.getCurrentPosition() + " / " + right.getTargetPosition());
        telemetry.addData("Set encoder Count", dRStraight.ectemp);
    }
}
