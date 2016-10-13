package org.firstinspires.ftc.teamcode.Examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.config.ConfigVariable;
import org.redshiftrobotics.util.ColorPicker;
import org.redshiftrobotics.util.ColorPicker.Color;
import org.redshiftrobotics.util.Util;

import org.redshiftrobotics.config.ConfigType;

/**
 * Created by Eric Golde on 9/16/2016.
 */
@TeleOp(name="Run Me!", group="Config")
@Disabled
public class ExampleOPModeUsingConfig extends OpMode {

                                       // new ConfigVariable(name, value, min, max, increment);

    static final ConfigVariable test1 = new ConfigVariable("int", 1, 0, 10, 1).setType(ConfigType.INT);
    static final ConfigVariable test2 = new ConfigVariable("float", 3.3, 1.111, 400.48, 3.6).setType(ConfigType.FLOAT);
    static final ConfigVariable test3 = new ConfigVariable("double", 3.3, 1.111, 400.48, 3.6).setType(ConfigType.DOUBLE);
    static final ConfigVariable test4 = new ConfigVariable("boolean", 1, 0, 1, 1).setType(ConfigType.BOOLEAN);
    static final ConfigVariable test5 = new ConfigVariable("color", Color.BLUE.getNumber(), (double)Color.MIN, (double)Color.MAX, 1).setType(ConfigType.COLORPICKER);
    Util util = new Util(this);

    static void initMe() //you need this because static { } does not get called in FTC //CALL CLEAR BEFORE ANYTHING
    {
        ExampleConfig.config.clearArray();
        ExampleConfig.config.addVariable(test1);
        ExampleConfig.config.addVariable(test2);
        ExampleConfig.config.addVariable(test3);
        ExampleConfig.config.addVariable(test4);
        ExampleConfig.config.addVariable(test5);
    }

    @Override
    public void init() {
        //not needed
    }

    @Override
    public void start(){
        //for this simple op mode just spit out the data
        util.log("int" + test1.getValueInt());
        util.log("float" + test2.getValueFloat());
        util.log("double" + test3.getValueDouble());
        util.log("boolean" + test4.getValueBoolean());
        util.log("color" + test5.getValueColor().getName());
        util.log("colorRaw" + test5.getValueColor().getNumber());
        util.updateTelemetry();
    }

    @Override
    public void loop() {
        //not needed
    }
}
