package com.qualcomm.ftcrobotcontroller.opmodes;





import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

//Rember to register opmode in FtcOpModeRegister.java !
public class EricsTestOpMode extends OpMode {

    DcMotor leftMotor1;
    DcMotor rightMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor2;

    @Override
    public void init() {
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        rightMotor1 = hardwareMap.dcMotor.get("right1");

        rightMotor1.setDirection(DcMotor.Direction.REVERSE);

        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor2 = hardwareMap.dcMotor.get("right2");

        rightMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        //get the values from the gamepads
        //note: pushing the stick all the way up returns -1,
        //so we need to reverse the y values
        float xValue = -gamepad1.left_stick_y;
        float yValue = gamepad1.left_stick_x;
        //Also reverse the x and y
        //why? Because it makes it work, i dont get it but i found it makes my code work fine so i did it
        //if you dont then all your controlls will be backwards :/

        //calculate the power needed for each motor
        float leftPower = yValue + xValue;
        float rightPower = yValue - xValue;

        //clip the power values so that it only goes from -1 to 1
        leftPower = Range.clip(leftPower, -1, 1);
        rightPower = Range.clip(rightPower, -1, 1);

        //set the power of the motors with the gamepad values
        leftMotor1.setPower(leftPower);
        rightMotor1.setPower(rightPower);

        leftMotor2.setPower(leftPower);
        rightMotor2.setPower(rightPower);

        //============================[Testing]=================================================
        /*

        if(gamepad1.a == true){
            leftMotor.setPower(1);
        }
        else{
            leftMotor.setPower(0);
        }

       */







    }






}

