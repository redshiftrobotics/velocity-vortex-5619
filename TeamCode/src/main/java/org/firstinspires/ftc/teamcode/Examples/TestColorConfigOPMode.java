package org.firstinspires.ftc.teamcode.Examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.config.ConfigVariable;
import org.redshiftrobotics.util.ColorPicker;
import org.redshiftrobotics.util.Util;

import org.redshiftrobotics.config.ConfigType;

/**
 * Created by Eric Golde on 9/16/2016.
 */
@TeleOp(name="ColorOutput", group="ColorConfig")
@Disabled
public class TestColorConfigOPMode extends OpMode {

    // new ConfigVariable(name, value, min, max, increment);

    static final ConfigVariable test1 = new ConfigVariable("Color", 1, ColorPicker.Color.MIN, ColorPicker.Color.MAX, 1).setType(ConfigType.COLORPICKER);
    Util util = new Util(this);

    static void initMe() //you need this because static { } does not get called in FTC //CALL CLEAR BEFORE ANYTHING
    {
        TestColorCOnfigConfig.config.clearArray();
        TestColorCOnfigConfig.config.addVariable(test1);
    }

    @Override
    public void init() {
        //not needed
    }

    @Override
    public void start(){
        //for this simple op mode just spit out the data
        util.log("int: " + test1.getValueInt());
        util.log("Color: " + test1.getValueColor().getName());
        util.updateTelemetry();
    }

    @Override
    public void loop() {
        //not needed
    }
}
