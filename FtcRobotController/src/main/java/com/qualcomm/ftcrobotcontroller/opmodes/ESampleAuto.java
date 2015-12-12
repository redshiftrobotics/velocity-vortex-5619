package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Eric Golde on 10/5/2015.
 */
public class ESampleAuto extends EOpModeBase {

    int state;
    final int STATE_DRIVE_7_FEET = 1;
    final int STATE_TURN_90_DEG_LEFT = 2;

    public void init() {

        dt("Simple Autonomous Selected!");

        super.init();

    }

    @Override
    public void start() {
        startDrive7();
        //FIRST STATE TO START IN
    }

    @Override
    public void loop() {
        //THIS IS THE KEY PART TO EVERYTING! ***DON'T SCREW THIS UP OR YOU WILL BE VERY FRUSTRATED***

        if (state == STATE_DRIVE_7_FEET) {
            loopDrive7();
        } else if (state == STATE_TURN_90_DEG_LEFT) {
            //call the loop funtion!
        }
    }


    //███████████████████████████████████████████████████████

    public void startDrive7() {
        state = STATE_DRIVE_7_FEET;
        /*
         reset incoders here
         set encoder limit here
         set power to motors
         */
    }

    public void loopDrive7() {
        /*
        if encoders say your finished
        then call start next Estate
         */
    }
}
