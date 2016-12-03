package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "5619 RED Simple Shooter Autonomous")
public class FiftySixNineteenSimpleShooterAutonomousRed extends AutonomousOpMode {
	DcMotor shooter;
	DcMotor collector;

	@Override
	public void runOpMode() throws InterruptedException {
		// If we're shooting, we're facing backward.
		leftDrive = hardwareMap.dcMotor.get("right_drive");
		rightDrive = hardwareMap.dcMotor.get("left_drive");
		shooter = hardwareMap.dcMotor.get("shooter");
		collector = hardwareMap.dcMotor.get("collector");

		rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		collector.setDirection(DcMotorSimple.Direction.REVERSE);

		waitForStart();

		forward(1.1);

		Thread.sleep(3000);

		shooter.setPower(1.0);

		Thread.sleep(3000);

		shooter.setPower(0);

		collector.setPower(1);

		Thread.sleep(3000);

		shooter.setPower(1);

		Thread.sleep(3000);

		shooter.setPower(0);
		collector.setPower(0);

		while (opModeIsActive()) idle();
	}
}
