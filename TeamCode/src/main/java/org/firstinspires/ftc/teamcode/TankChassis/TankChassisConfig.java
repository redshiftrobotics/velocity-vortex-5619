package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.config.Config;
import org.redshiftrobotics.config.ConfigBase;
import org.redshiftrobotics.config.ConfigVariable;

@TeleOp(name="Tank Chassis Config", group="TankChassis")
public class TankChassisConfig extends ConfigBase{
    Config config = new Config();

    static final ConfigVariable motorSpeed = new ConfigVariable("Drive Speed", 1, 0.1, 1, 0.1);
	static final ConfigVariable fireSpeed = new ConfigVariable("Launcher Speed", 0.5, 0.5, 1, 0.1);

    @Override
    public void init() {

        config.addVariable(motorSpeed);
		config.addVariable(fireSpeed);

        super.config = config;
        super.init();
    }

    @Override
    public void loop() {
        super.loop();
    }
}
