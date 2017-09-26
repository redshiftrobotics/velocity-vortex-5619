package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

/**
 * This class sets up the hardware map and registers all the motors, servos, etc.
 *
 * Created by Eric Golde on 9/12/2017.
 */

public class Chassis {

	private OpMode opMode;
	private HardwareMap hardware;

	//Motors
	public DcMotor frontLeft;
	public DcMotor frontRight;
	public DcMotor backLeft;
	public DcMotor backRight;



	public Chassis(OpMode opMode){
		this.opMode = opMode;
		this.hardware = opMode.hardwareMap;
	}
	/**
	 * ! CALL AT THE BEGINNING OF INIT() !
	 * This will init the chassis class. It registers everything to the hardware map, sets up all the motors etc.
	 */
	public void init(){
		//Here is where everything gets initialised
		opMode.telemetry.addData("CHASSIS: ", "Starting to Initialize everything...");
		updateTelementry();

		frontLeft = hardware.dcMotor.get("fl");
		frontRight = hardware.dcMotor.get("fr");
		backLeft = hardware.dcMotor.get("bl");
		backRight = hardware.dcMotor.get("br");

		//Assuming that the right motors spin the oppisite way
		frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
		backRight.setDirection(DcMotorSimple.Direction.REVERSE);

		opMode.telemetry.addData("CHASSIS: ", "Initialized everything!");

		updateTelementry();
	}

	/**
	 * ! CALL AT THE END OF LOOP() !
	 * This will automatically update telemetry
	 */
	public void loop(){
		//We might need this, not clear as of now
		updateTelementry();
	}

	private void updateTelementry(){opMode.telemetry.update();}

}
