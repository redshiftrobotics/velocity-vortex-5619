package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Eric Golde on 12/3/2015.
 */
public class EServoTest  extends OpMode{

    Servo servo;
    double pos = 0;

    public void init()
    {
       servo = hardwareMap.servo.get("lift1");
        servo.setPosition(pos);
    }

    public void loop()
    {
        telemetry.addData("Servo Pos", pos);
        if(pos < 0.99)
        {
            servo.setPosition(pos);
            pos++;
        }
        else
        {
            servo.setPosition(0);
            pos = 0;
        }

    }

}
