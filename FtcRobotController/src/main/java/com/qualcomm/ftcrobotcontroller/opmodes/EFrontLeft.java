package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//Rember to register opmode in FtcOpModeRegister.java !
public class EFrontLeft extends OpMode {

    DcMotor frontLeftMotor;

    public void init() {
        frontLeftMotor = hardwareMap.dcMotor.get("left1");
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {

        frontLeftMotor.setPower(1);

    }






}

