package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.redshiftrobotics.util.ColorPicker;

/**
 * Created by Eric Golde on 10/4/2016.
 */
@TeleOp(name="NoNull", group="UGG")
public class PlzNoNull extends OpMode {

    ColorPicker picker;

    @Override
    public void init() {

    }

    @Override
    public void start(){
        telemetry.addLine("Plz No null :)");
        updateTelemetry(telemetry);
    }

    @Override
    public void loop() {

    }
}
