package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "5619 BLUE Simple Shooter Autonomous")
public class FiftySixNineteenSimpleShooterAutonomousBlue extends AutonomousOpMode {
	DcMotor shooter;
	DcMotor collector;
	Servo servo;

	@Override
	public void runOpMode() throws InterruptedException {
		// If we're shooting, we're facing backward.
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
		shooter = hardwareMap.dcMotor.get("shooter");
		servo = hardwareMap.servo.get("servo");
		collector = hardwareMap.dcMotor.get("collector");

		rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		collector.setDirection(DcMotorSimple.Direction.REVERSE);

		waitForStart();

		forward(1700);

		Thread.sleep(3000);

		shooter.setPower(1.0);

		Thread.sleep(3000);

		shooter.setPower(0);

		servo.setPosition(DriveOPBase.servo_max.getValueDouble());
		Thread.sleep(500);
		servo.setPosition(DriveOPBase.servo_min.getValueDouble());

		shooter.setPower(1);


		Thread.sleep(3000);

		shooter.setPower(0);

		while (opModeIsActive()) idle();
	}
}
