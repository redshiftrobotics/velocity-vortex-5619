package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

/**
 * This class sets up the hardware map and registers all the motors, servos, etc.
 *
 * Created by Eric Golde on 9/12/2017.
 */

public class Chassis {

	private OpMode opMode;

	public Chassis(){/*Nothing goes here*/}

	/**
	 * ! CALL AT THE BEGINNING OF INIT() !
	 * This will init the chassis class. It registers everything to the hardware map, sets up all the motors etc.
	 */
	public void init(OpMode opMode){
		this.opMode = opMode;

		//Here is where everything gets initialised
	}

	/**
	 * This is called from the main loop of your OPMode.
	 */
	public void loop(){
		//We might need this, not clear as of now
	}

}
