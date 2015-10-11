package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//Rember to register opmode in FtcOpModeRegister.java !
public class EFrontRight extends OpMode {

    DcMotor frontRightMotor;

    public void init() {
        frontRightMotor = hardwareMap.dcMotor.get("right1");

    }

    @Override
    public void loop() {

        frontRightMotor.setPower(1);

    }






}

