package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.redshiftrobotics.beacons.BeaconDetector;


/**
 * A simple OpMode to debug Beacon detection.
 */

@TeleOp(name = "Beacon Detect", group = "Debug")
public class BeaconDetect extends OpMode {

	private BeaconDetector detector;

	private boolean lastButtonState = false;

	@Override
	public void init() {
		detector = new BeaconDetector(this.hardwareMap);
	}

	@Override
	public void start() {
		detector.start();
		telemetry.addLine("Press X to Detect a Beacon");
	}

	@Override
	public void loop() {
		if (!lastButtonState && gamepad1.x) {
			telemetry.addLine("Detecting...");
			telemetry.update();

			telemetry.addData("State", detector.detect().toString());
			telemetry.addLine();
			telemetry.addLine("Press X to Detect a Beacon");
			telemetry.update();
		}
		lastButtonState = gamepad1.x;
	}

	@Override
	public void stop() {
		detector.stop();
	}
}
