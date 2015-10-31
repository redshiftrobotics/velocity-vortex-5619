package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//Rember to register opmode in FtcOpModeRegister.java !
public class EBackLeft extends OpMode {

    DcMotor backLeftMotor;

    public void init() {
        backLeftMotor = hardwareMap.dcMotor.get("left2");
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {

        backLeftMotor.setPower(1);

    }






}

