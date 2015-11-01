package com.qualcomm.ftcrobotcontroller.opmodes;


//Rember to register opmode in FtcOpModeRegister.java !
public class EFrontLeft extends EOpModeBase {



    public void init() {
		dt("[Debug] FrontLeftTest Selected!");
		super.init();

    }

    @Override
    public void loop() {

        frontLeftMotor.setPower(1);

    }






}

