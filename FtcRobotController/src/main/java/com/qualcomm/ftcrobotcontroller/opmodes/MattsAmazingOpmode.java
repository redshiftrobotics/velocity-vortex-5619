package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.Range;

public class MattsAmazingOpmode extends OpMode

{
    //declare motor controller
    private DcMotorController v_dc_motor_controller_drive;
    private ServoController servocontroller;

    //declare variables for left and right motors
    private DcMotor v_motor_left_drive;
    final int v_channel_left_drive = 1;

    private DcMotor v_motor_right_drive;
    final int v_channel_right_drive = 2; //these aren't used right now but they would be for encoders

    private Servo hand1;


    @Override public void init ()

    {
        //this is where hardware interfaces are initialized and bound
        v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("drive_controller");

        v_motor_left_drive = hardwareMap.dcMotor.get ("left_drive");

        v_motor_right_drive = hardwareMap.dcMotor.get ("right_drive");

        //servocontroller = hardwareMap.servoController.get("servo_controller");

        //hand1 = hardwareMap.servo.get("hand1");





    }

    @Override public void start ()

    {
        //Actions in this method will execute before the 'Start' button is pressed on the controller, its the equivalent of the setup loop in Arduino programming.

    }

    public MattsAmazingOpmode()

    {

    }

    @Override public void loop ()

    {
        //Again the equivalent in Arduino programming is the loop()... This will continue to execute (in this case in a linear/synchronous fashion) until stop() is called (at least I think thats how you terminate it).


        //This sets the drive power of both motors to the first gamepads joysticks.
        v_motor_left_drive.setPower (scale_motor_power(gamepad1.left_stick_y));
        v_motor_right_drive.setPower (scale_motor_power(-gamepad1.right_stick_y));
        //hand1hand1.setPosition(Range.clip (gamepad2.left_stick_y, Servo.MIN_POSITION, Servo.MAX_POSITION));




    }

    double scale_motor_power (double p_power)
    {
        //
        // Assume no scaling.
        //
        double l_scale = 0.0f;

        //
        // Ensure the values are legal.//
        double l_power = Range.clip (p_power, -1, 1);

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

    } // PushBotManual::scale_motor_power

}