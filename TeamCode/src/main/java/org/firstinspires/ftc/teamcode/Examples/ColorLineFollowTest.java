package org.firstinspires.ftc.teamcode.Examples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.redshiftrobotics.util.ColorPicker;
import org.redshiftrobotics.util.ColorPicker.Color;

/*
idea for very stupidly simple line follower.
this is not really good but it works for the most part.
 */
@TeleOp(name="ColorLineFollowTest", group="Sensor")
public class ColorLineFollowTest extends OpMode {

    DcMotor left;
    DcMotor right;
    ColorSensor sensor;
    ColorPicker colorPicker = new ColorPicker();
    double speed = 1;
    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        sensor = hardwareMap.colorSensor.get("color sensor");

        sensor.enableLed(true);
    }

    @Override
    public void loop() {

        Color color = colorPicker.whatColor(sensor);

        telemetry.addData("color", color.getName());

        if(color == ColorPicker.Color.RED){
            left.setPower(speed);
            right.setPower(-speed);
            telemetry.addData("left", "+");
            telemetry.addData("right", "-");
        }else{
            left.setPower(-speed);
            right.setPower(speed);
            telemetry.addData("left", "-");
            telemetry.addData("right", "+");
        }

        updateTelemetry(telemetry);

    }
}
