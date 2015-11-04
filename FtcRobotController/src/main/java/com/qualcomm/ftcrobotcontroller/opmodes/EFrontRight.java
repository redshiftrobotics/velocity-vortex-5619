package com.qualcomm.ftcrobotcontroller.opmodes;


//Rember to register opmode in FtcOpModeRegister.java !
public class EFrontRight extends EOpModeBase {



    public void init() {
		dt("[Debug] FrontRightTest Selected!");
		super.init();
    }

    @Override
    public void loop() {

        frontRightMotor.setPower(1);

    }






}

