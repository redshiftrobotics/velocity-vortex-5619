package com.qualcomm.ftcrobotcontroller.opmodes;





import android.os.SystemClock;

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


   //telemetry.addData("1", "init"); "
   //(dt("Text");

   // boolean debugActive = true;
    boolean sledDown = false;
    //Boolean aButton = gamepad1.a;
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

    public void sleep(long milsec)
    {
        SystemClock.sleep(milsec);
        dt("Trying to sleep...");
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


        ///////////////////////////////////////////////////////////////////////////

        float ServoxValue = gamepad2.left_stick_x;

        ServoxValue = Range.clip(ServoxValue, 1, 0);

        leftServo.setPosition(ServoxValue);
        rightServo.setPosition(ServoxValue);









        ////////////////////////////////////////////////////////////////////////////

        /*
        if(gamepad1.a == true && sledDown == false)
        {
            leftServo.setPosition(1);
            rightServo.setPosition(1);
            dt("DOWN");
            sledDown = true;
            sleep(1000);
        }

        if(gamepad1.a == true && sledDown == true)
        {
            leftServo.setPosition(0);
            rightServo.setPosition(0);
            dt("UP");
            sledDown = false;
            sleep(1000);

        }


float lt1Convert;
        float lt1 = gamepad2.left_trigger;

        lt1Convert = Range.clip(lt1, 1, 0);

        telemetry.addData("1 Start", "Raw: " + lt1);
        telemetry.addData("2 Start", "Converted: " + lt1Convert);

*/

    }

}

