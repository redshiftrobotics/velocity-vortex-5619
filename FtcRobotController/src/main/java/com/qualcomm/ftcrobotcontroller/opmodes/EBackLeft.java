package com.qualcomm.ftcrobotcontroller.opmodes;

public class EBackLeft extends EOpModeBase {

    public void init() {
		dt("[Debug] BackLeftTest Selected!");
        super.init();
    }

    @Override
    public void loop() {

        backLeftMotor.setPower(1);

    }






}

