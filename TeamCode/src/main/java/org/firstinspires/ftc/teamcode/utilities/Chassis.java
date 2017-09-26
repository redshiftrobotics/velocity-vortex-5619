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
		frontLeft = hardware.dcMotor.get("fl");
		frontRight = hardware.dcMotor.get("fr");
		backLeft = hardware.dcMotor.get("bl");
		backRight = hardware.dcMotor.get("br");
	}

	/**
	 * This is called from the main loop of your OPMode.
	 */
	public void loop(){
		//We might need this, not clear as of now
	}

}
