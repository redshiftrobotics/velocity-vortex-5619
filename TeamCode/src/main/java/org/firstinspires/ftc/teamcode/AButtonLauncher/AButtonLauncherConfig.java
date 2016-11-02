package org.firstinspires.ftc.teamcode.AButtonLauncher;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.config.Config;
import org.redshiftrobotics.config.ConfigBase;
import org.redshiftrobotics.config.ConfigVariable;

/**
 * Created by Eric Golde on 10/25/2016.
 */
@TeleOp(name="AButtonConfig", group ="Launcher")
public class AButtonLauncherConfig extends ConfigBase {
    Config config = new Config();

    static final ConfigVariable ec = new ConfigVariable("Encoder Counts", 350, 1, 1000000, 10);

    @Override
    public void init() {

        config.addVariable(ec);

        super.config = config;
        super.init();
    }

    @Override
    public void loop() {
        super.loop();
    }
}
