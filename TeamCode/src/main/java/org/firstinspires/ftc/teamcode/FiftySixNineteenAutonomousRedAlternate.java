package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.redshiftrobotics.Alliance;

@Autonomous(name = "5619 Autonomous RED ALT")
public class FiftySixNineteenAutonomousRedAlternate extends FullAutonomous {
	@Override
	protected Alliance getAlliance() {
		return Alliance.RED;
	}

	@Override
	protected boolean isAlternatePosition() {
		return true;
	}
}
