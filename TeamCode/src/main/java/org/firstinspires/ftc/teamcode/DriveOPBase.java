package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.redshiftrobotics.config.ConfigType;
import org.redshiftrobotics.config.ConfigVariable;

public abstract class DriveOPBase extends OpMode {

    static final ConfigVariable motorSpeed = new ConfigVariable("Motor Speed", 1, 0, 1, 0.1).setType(ConfigType.DOUBLE);
	static final ConfigVariable shooterSpeed = new ConfigVariable("Shooter Speed", 1, -1, 1, 0.1).setType(ConfigType.DOUBLE);
	static final ConfigVariable collectorSpeed = new ConfigVariable("Collector Speed", 1, -1, 1, 0.1).setType(ConfigType.DOUBLE);
	static final ConfigVariable servo_min = new ConfigVariable("Servo min", 0.8, 0, 1, 0.1).setType(ConfigType.DOUBLE);
	static final ConfigVariable servo_max = new ConfigVariable("Servo max", 1, 0, 1, 0.1).setType(ConfigType.DOUBLE);

    static void config() {
        DriveConfigFile.config.clearArray();
        DriveConfigFile.config.addVariable(motorSpeed);
		DriveConfigFile.config.addVariable(shooterSpeed);
		DriveConfigFile.config.addVariable(collectorSpeed);
		DriveConfigFile.config.addVariable(servo_min);
		DriveConfigFile.config.addVariable(servo_max);
    }

	DcMotor left;
	DcMotor right;
	DcMotor shooter;
	DcMotor collector;
	Servo servo;

	public void initMe(){
		left = hardwareMap.dcMotor.get("left_drive");
		right = hardwareMap.dcMotor.get("right_drive");
		right.setDirection(DcMotorSimple.Direction.REVERSE);
		shooter = hardwareMap.dcMotor.get("shooter");
		collector = hardwareMap.dcMotor.get("collector");
		servo = hardwareMap.servo.get("servo");
	}

}
