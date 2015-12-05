package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Eric Golde on 12/5/2015.
 */
public class EResetAll extends EOpModeBase {

    public void init()
    {
        super.init();
        ct("Status", "Starting");
        resetArmHeightLeft();
        resetArmHeightRight();
        resetArmLeft();
        resetArmRight();
        resetHitLeft();
        resetHitRight();
        ct("Status", "Done!");
    }

    public void loop()
    {

    }

}
