package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.redshiftrobotics.Alliance;
import org.redshiftrobotics.beacons.BeaconDetector;
import org.redshiftrobotics.beacons.BeaconState;

@Autonomous(name="Test")
abstract public class TestAuto extends AutonomousOpMode {
	public void runOpMode() throws InterruptedException {
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
		rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);

		waitForStart();

		forward(4000);
		backward(4000);
	}
}
