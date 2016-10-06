package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.redshiftrobotics.util.ColorPicker;

/**
 * Created by Eric Golde on 10/4/2016.
 */

//idea for very stupidly simple line follower. ***NEVER TESTED***
@TeleOp(name="ColorLineFollowTest", group="Sensor")
public class ColorLineFollowTest extends OpMode {

    DcMotor left;
    DcMotor right;
    ColorSensor s0;
    ColorSensor s1;
    ColorPicker colorPicker = new ColorPicker();
    boolean dirRight = true;
    double speed = 0.3;



    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        s0 = hardwareMap.colorSensor.get("s0");
        s1 = hardwareMap.colorSensor.get("s1");

        s0.enableLed(true);
        s1.enableLed(true);
    }

    @Override
    public void loop() {

        int s0c = colorPicker.whatColor(s0);
        int s1c = colorPicker.whatColor(s1);

        telemetry.addData("s0c", colorPicker.toString(s0c));
        telemetry.addData("s1c", colorPicker.toString(s1c));

        if(s0c == ColorPicker.Color.BLACK){
            //right
            dirRight = true;
        }

        if(s1c == ColorPicker.Color.BLACK){
            //left
            dirRight = false;
        }

        telemetry.addData("dir", dirRight);

        if(dirRight){
            left.setPower(speed);
            right.setPower(-speed);
        }else{
            left.setPower(-speed);
            right.setPower(speed);
        }

        updateTelemetry(telemetry);

    }
}
