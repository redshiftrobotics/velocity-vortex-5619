package org.firstinspires.ftc.teamcode.TankArcadeDrive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.redshiftrobotics.config.ConfigType;
import org.redshiftrobotics.config.ConfigVariable;

/**
 * Created by Eric Golde on 9/24/2016.
 */
public abstract class DriveOPBase extends OpMode {

    static final ConfigVariable motorSpeed = new ConfigVariable("Motor Speed", 1, 0, 1, 0.1).setType(ConfigType.TYPE_DOUBLE);

    static void initMe() {
        DriveConfigFile.config.clearArray();
        DriveConfigFile.config.addVariable(motorSpeed);
    }

}
