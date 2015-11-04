package com.qualcomm.ftcrobotcontroller.opmodes;

public class EBackRight extends EOpModeBase {


    public void init() {
		dt("[Debug] BackRightTest Selected!");
        super.init();
    }

    @Override
    public void loop() {

        backRightMotor.setPower(1);

    }






}

