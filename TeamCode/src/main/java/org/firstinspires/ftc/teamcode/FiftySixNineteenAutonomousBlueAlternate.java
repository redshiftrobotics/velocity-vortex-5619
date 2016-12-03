package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.redshiftrobotics.Alliance;

@Autonomous(name = "5619 Autonomous BLUE ALT")
public class FiftySixNineteenAutonomousBlueAlternate extends FullAutonomous {
	@Override
	protected Alliance getAlliance() {
		return Alliance.BLUE;
	}

	@Override
	protected boolean isAlternatePosition() {
		return true;
	}
}
