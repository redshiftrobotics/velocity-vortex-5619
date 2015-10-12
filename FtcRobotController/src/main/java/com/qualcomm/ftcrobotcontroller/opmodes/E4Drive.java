package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


//Rember to register opmode in FtcOpModeRegister.java !
public class E4Drive extends OpMode {

    DcMotor frontLeftMotor; //FRONT LEFT
    DcMotor frontRightMotor; //FRONT RIGHT
    DcMotor backLeftMotor; //BACK LEFT
    DcMotor backRightMotor; //BACK RIGHT




    String teleConvert;
    int teleInt = 3;


    Servo leftServo;
    Servo rightServo;




    public void dt(String text)
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }




    @Override
    public void init() {

            dt ("init loaded");

        frontLeftMotor = hardwareMap.dcMotor.get("left1");
        frontRightMotor = hardwareMap.dcMotor.get("right1");
        backLeftMotor = hardwareMap.dcMotor.get("left2");
        backRightMotor = hardwareMap.dcMotor.get("right2");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
      // frontRightMotor.setDirection(DcMotor.Direction.REVERSE); //CHANGED

        leftServo = hardwareMap.servo.get("servo1");
        rightServo = hardwareMap.servo.get("servo2");

        leftServo.setDirection(Servo.Direction.REVERSE); //fix direction




    }





    @Override
    public void loop() {
        //get the values from the gamepads
        //note: pushing the stick all the way up returns -1,
        //so we need to reverse the y values
        float xValue = gamepad1.left_stick_x;
        float yValue = -gamepad1.left_stick_y;


        //calculate the power needed for each motor
        float leftPower = yValue + xValue;
        float rightPower = yValue - xValue;

        //clip the power values so that it only goes from -1 to 1
        leftPower = Range.clip(leftPower, -1, 1);
        rightPower = Range.clip(rightPower, -1, 1);

        //set the power of the motors with the gamepad values
        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);

        backLeftMotor.setPower(leftPower);
        backRightMotor.setPower(rightPower);



        //get the values from the gamepads
        //note: pushing the stick all the way up returns -1,
        //so we need to reverse the y values
        float ServoxValue = gamepad2.left_stick_x;

        //clip the power values so that it only goes from 0 to 1
        ServoxValue = Range.clip(ServoxValue, 1, 0);

        //set the power of the motors with the gamepad values
        leftServo.setPosition(ServoxValue);
        rightServo.setPosition(ServoxValue);


    }

}

