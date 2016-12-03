package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "5619 Simple Autonomous (Cap Ball)")
public class FiftySixNineteenSimpleAutonomous extends AutonomousOpMode {
	@Override
	public void runOpMode() throws InterruptedException {
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");

		waitForStart();

		forward(0);
	}
}
