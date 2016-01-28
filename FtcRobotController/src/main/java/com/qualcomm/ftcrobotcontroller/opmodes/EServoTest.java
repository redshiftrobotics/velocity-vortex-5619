package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Eric Golde on 12/3/2015.
 */
public class EServoTest  extends OpMode{

    Servo servo;
    Servo servo1;


    public void init()
    {
       servo = hardwareMap.servo.get("climberExtend");
        servo1 = hardwareMap.servo.get("servo1");
    }

    public void loop()
    {
        servo.setPosition(1);



    }

}
