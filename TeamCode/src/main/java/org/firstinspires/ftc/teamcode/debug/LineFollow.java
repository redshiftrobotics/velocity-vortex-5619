package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.redshiftrobotics.linefollower.LineFollower;

@Autonomous(name="Line Follower", group="Debug")
public class LineFollow extends OpMode
{
	private LightSensor lightSensor;
	private DcMotor leftMotor;
	private DcMotor rightMotor;

	private LineFollower lineFollower;

	@Override
	public void init() {
		telemetry.addData("Status", "Initialized");
		lightSensor = hardwareMap.lightSensor.get("light_sensor_1");
		leftMotor = hardwareMap.dcMotor.get("left_motor");
		rightMotor = hardwareMap.dcMotor.get("right_motor");
		rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
		leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
		lineFollower = new LineFollower(lightSensor, leftMotor, rightMotor);
	}

	@Override
	public void loop() {
		lineFollower.tick();
	}

	@Override
	public void stop() {
		// I don't know if we need this.
		leftMotor.setPower(0);
		rightMotor.setPower(0);
	}

}
