package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Eric Golde on 1/16/2016.
 */
public class ERight extends EOpModeBaseTank {

    @Override
    public void init()
    {
        super.init();
        dt("Right");
    }
    @Override
    public void loop()
    {
        left.setPower(0.4);
        right.setPower(0.2);
    }
}
