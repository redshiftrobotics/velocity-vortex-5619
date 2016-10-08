package org.firstinspires.ftc.teamcode.TankChassis;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Examples.ExampleOPModeUsingConfig;
import org.redshiftrobotics.config.Config;
import org.redshiftrobotics.config.ConfigBase;
import org.redshiftrobotics.config.ConfigVariable;

/**
 * Created by Eric Golde on 10/8/2016.
 */

@TeleOp(name="Tank Chassis Config", group="TankChasis")
public class TankChassisConfig extends ConfigBase{

    public static Config config = new Config();

    // new ConfigVariable(name, value, min, max, increment);

    //new config variable to hold the motor speed
    static final ConfigVariable motorSpeed = new ConfigVariable("Drive Speed", 1, 0.1, 1, 0.1);

    @Override
    public void init() {

        //add the config variable to the config
        config.addVariable(motorSpeed);

        super.config = config;
        super.init(); //call the init in super
    }

    @Override
    public void loop() {
        super.loop(); //pass in loop
    }

}
