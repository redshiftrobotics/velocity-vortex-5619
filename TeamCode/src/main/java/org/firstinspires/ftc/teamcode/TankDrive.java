package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Tank Drive", group="TeleOP")
public class TankDrive extends DriveOPBase{

    @Override
    public void init() {
        super.initMe();
		telemetry.addLine("Controls:");
		telemetry.addLine(" ");
		telemetry.addLine("Drive: TankDrive");
		telemetry.addLine("X: Ball Collector toggle (Push)");
		telemetry.addLine("A: Launch (Hold Down)");
		telemetry.addLine("B: Servo open/close (Push)");
		updateTelemetry(telemetry);
    }

    @Override
    public void start(){}

    @Override
    public void loop() {

        float leftValue = -gamepad1.left_stick_y;
        float rightValue = -gamepad1.right_stick_y;

        leftValue = Range.clip(leftValue, -DriveOPBase.motorSpeed.getValueFloat(), DriveOPBase.motorSpeed.getValueFloat());
        rightValue = Range.clip(rightValue, -DriveOPBase.motorSpeed.getValueFloat(), DriveOPBase.motorSpeed.getValueFloat());

        left.setPower(rightValue);
        right.setPower(leftValue);

		if(toggleCollectorOn()){
			collector.setPower(DriveOPBase.collectorSpeed.getValueDouble());
			telemetry.addData("Collector", "on");
		}else{
			collector.setPower(0);
			telemetry.addData("Collector", "off");
		}

		if(gamepad1.b){
			servo.setPosition(DriveOPBase.servo_max.getValueDouble());
		}else{
			servo.setPosition(DriveOPBase.servo_min.getValueDouble());
		}

		if(gamepad1.a){
			shooter.setPower(DriveOPBase.shooterSpeed.getValueDouble());
		}else{
			shooter.setPower(0);
		}

		updateTelemetry(telemetry);
    }

	boolean lastBttnStateToggleCollector = false;
	boolean toggleStateToggleCollector = false;

	public boolean toggleCollectorOn() {
		if (gamepad1.x && !lastBttnStateToggleCollector) {
			toggleStateToggleCollector = !toggleStateToggleCollector;
		}
		lastBttnStateToggleCollector = gamepad1.x;
		return toggleStateToggleCollector;
	}

    @Override
    public void stop(){}
}
