package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//Rember to register opmode in FtcOpModeRegister.java !
public class EBackRight extends OpMode {

    DcMotor backRightMotor;

    public void init() {
        backRightMotor = hardwareMap.dcMotor.get("right2");
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {

        backRightMotor.setPower(1);

    }






}

