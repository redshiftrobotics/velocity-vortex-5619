package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Madeline Byrne on 10/21/2015.
 */
public class OpModeTest extends OpMode
{
    DcMotor motorRight;
    DcMotor motorLeft;



    @Override
    public void init()
    {
        telemetry.addData("init ", "telemetry working");
        //set references for motors from hardware map
        motorRight=hardwareMap.dcMotor.get("Motor_1");
        motorLeft=hardwareMap.dcMotor.get("Motor_2");
        //reverse left motor
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop()
    {
        int rangemin=-1;
        int rangemax=1;
        float throttle = gamepad1.right_stick_y;
        float direction = -gamepad1.left_stick_y;
        float rightPower = throttle - direction;
        float leftPower = throttle + direction;
        rightPower = Range.clip(throttle, rangemin, rangemax);
        leftPower = Range.clip(direction, rangemin, rangemax);
        rightPower = (float)scaleInput(throttle);
        leftPower = (float)scaleInput(direction);
        motorLeft.setPower(leftPower);
        motorRight.setPower(rightPower);

        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", leftPower));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightPower));

        //float throttle = -gamepad1.left_stick_y;
        //float direction = gamepad1.left_stick_x;
       // float right = throttle - direction;
        //float left = throttle + direction;

    }

    @Override
    public void stop()
    {

    }

    double scaleInput (double dVal)
    {
        telemetry.addData("dVal is ", String.format("%.2f", dVal));
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.0,};
        int scaleArrayLength = 16;
        int index = (int) (dVal * scaleArrayLength);
        if (index < 0) {
            index = -index;
        }
        if (index > scaleArrayLength) {
            index = scaleArrayLength;
        }
        double dscale = 0.0;
        if (dVal < 0) {
            dscale = -scaleArray[index];
        } else {
            dscale = scaleArray[index];
        }
        return dscale;
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


