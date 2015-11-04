package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Noah Rose-Ledesma on 9/26/2015.
 */


public class NoahTestOpMode extends OpMode{
    // First declare your controller hardware
    private DcMotorController dc_controller;
    // Then declare your individual hardware
    private DcMotor motor;
    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();
    @Override
     public void init(){
        // Init is called when you load the opmode and before the start button is pressed
        // This is when you make your hardware mappings
        dc_controller = hardwareMap.dcMotorController.get("Motor Controller 1");
        motor = hardwareMap.dcMotor.get ("left_drive");
    }
    @Override
    public void init_loop() {
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        runtime.reset();
        telemetry.addData("Null Op Init Loop", runtime.toString());
    }
    @Override
    public void loop(){
        telemetry.addData("1 Start", "NoahTestOpMode started at " + startDate);
        telemetry.addData("2 Status", "joy y " + gamepad1.left_stick_y);
        //motor.setPower (scale_motor_power(gamepad1.left_stick_y));
    }
    @Override
    public void start ()
    {
        motor.setPower(1);
    }
    // Matt told me you need a constructor, even if it doesnt do anything. Something about extend OpMode
    public NoahTestOpMode(){

    }

    double scale_motor_power (double p_power)
    {
        //
        // Assume no scaling.
        //
        double l_scale = 0.0f;

        //
        // Ensure the values are legal.
        //
        double l_power = Range.clip(p_power, -1, 1);

        double[] l_array =
                { 0.00, 0.05, 0.09, 0.10, 0.12
                        , 0.15, 0.18, 0.24, 0.30, 0.36
                        , 0.43, 0.50, 0.60, 0.72, 0.85
                        , 1.00, 1.00
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int) (l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    } //I stole this from matt, ty matt <3
}