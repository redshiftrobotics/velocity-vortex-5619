package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.beacons.BeaconDetector;
import org.redshiftrobotics.beacons.BeaconState;


/**
 * A simple OpMode to debug Beacon detection.
 */

@TeleOp(name = "Mass Beacon Detect", group = "Debug")
public class MassBeaconDetect extends LinearOpMode {

	private BeaconDetector detector;

	private boolean lastButtonState = false;

	@Override
	public void runOpMode() throws InterruptedException {
		detector = new BeaconDetector(this.hardwareMap);
		waitForStart();
		detector.start();
		telemetry.addLine("Press X to Mass Detect the Beacon");
		while (opModeIsActive()) {
			if (!lastButtonState && gamepad1.x) {
				telemetry.addLine("Detecting...");
				telemetry.update();

				int BR = 0;
				int RB = 0;
				int BB = 0;
				int RR = 0;

				for (int i = 0; i < 200; ++i) {
					switch (detector.detect()) {
						case BLUE_RED:
							BR++;
							break;
						case RED_BLUE:
							RB++;
							break;
						case BLUE:
							BB++;
							break;
						case RED:
							RR++;
					}
				}

				telemetry.addData("BR", String.valueOf(BR));
				telemetry.addData("RB", String.valueOf(RB));
				telemetry.addData("BB", String.valueOf(BB));
				telemetry.addData("RR", String.valueOf(RR));
				telemetry.addLine();
				telemetry.addLine("Press X to Detect a Beacon");
				telemetry.update();
			}
			lastButtonState = gamepad1.x;
		}
	}
}
