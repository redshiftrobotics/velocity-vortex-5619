package com.qualcomm.ftcrobotcontroller.opmodes;

import android.speech.tts.TextToSpeech;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import java.text.DecimalFormat;


/**
 * Created by Eric Golde on 11/1/2015.
 */
public abstract class EOpModeBase extends OpMode {

    String teleConvert;
    int teleInt = 0;

    DecimalFormat newFormat = new DecimalFormat("#.##");

//
    boolean useTTS = false; //use TTS? (YOU CANT USE THIS DURRING A MATCH)
    public static boolean Debug = false; //outputs a lot of text for debugging purpuses

    final int ENCODER_CPR = 1120; //ANDY MARK MOTOR DONT CHANGE
    final double TAPE_MEASURE_INCH_PER_ROTATION = 5.25;

    protected DcMotor frontLeftMotor; //FRONT LEFT
    protected DcMotor frontRightMotor; //FRONT RIGHT
    protected DcMotor backLeftMotor; //BACK LEFT
    protected DcMotor backRightMotor; //BACK RIGHT
    protected DcMotor extendMotor1; // arm1
    protected DcMotor extendMotor2; // arm2
    protected Servo lift1; //
    protected Servo lift2; //
    protected Servo hit1; //hit1
    protected Servo hit2; //hit2
    //protected LightSensor lightSensor; //line follower light sensor

    protected LegacyModule legacyModule1; //legacy module
    //protected ServoController servoController1; //servo controller
    protected DcMotorController dcMotorController1; //motor controller 1
    protected DcMotorController dcMotorController2; //motor controller 2
    protected DcMotorController dcMotorController3; //motor controller 3

    protected EAutoCallMountainCodeHigh mountainCodeHigh;


    public void dl(String text) {
        System.out.println(text);
    }

    public void dl(int text) {
        System.out.println(text);
    }

    public void dl(double text) {
        System.out.println(text);
    }

    public void dl(float text) {
        System.out.println(text);
    }

    public void dt(String text) //Debug text multiline. Usefull for a lot of output debugging
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
        //toastShort(text); //this makes it so DT (Debug text Multiline) also uses toast. ONLY USE IF NESSASARRY

        if (Debug) {
            dl(text);
        }
    }

    public void dt(int text) //Debug text multiline. Usefull for a lot of output debugging
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
        //toastShort(text); //this makes it so DT (Debug text Multiline) also uses toast. ONLY USE IF NESSASARRY
        if (Debug) {
            dl(text);
        }
    }

    public void dt(float text) //Debug text multiline. Usefull for a lot of output debugging
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
        //toastShort(text); //this makes it so DT (Debug text Multiline) also uses toast. ONLY USE IF NESSASARRY
        if (Debug) {
            dl(text);
        }
    }

    public void dt(double text) //Debug text multiline. Usefull for a lot of output debugging
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
        //toastShort(text); //this makes it so DT (Debug text Multiline) also uses toast. ONLY USE IF NESSASARRY
        if (Debug) {
            dl(text);
        }
    }


    public void ct(String what, String text) //Debug text single line. Usefull for printing Estate changes
    {
        telemetry.addData(what, text);
        if (Debug) {
            dl(what + " " + text);
        }
    }

    public void ct(String what, int text) //Debug text single line. Usefull for printing Estate changes
    {
        telemetry.addData(what, text);
        if (Debug) {
            dl(what + " " + text);
        }
    }

    public void ct(String what, double text) //Debug text single line. Usefull for printing Estate changes
    {
        telemetry.addData(what, text);
        if (Debug) {
            dl(what + " " + text);
        }
    }

    public void ct(String what, float text) //Debug text single line. Usefull for printing Estate changes
    {
        telemetry.addData(what, text);
        if (Debug) {
            dl(what + " " + text);
        }
    }

    public void tts(String text) {

        dt("TTS: " + text);
        if (useTTS) {
            FtcRobotControllerActivity.t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void tts(int text) {

        dt("TTS: " + text);
        if (useTTS) {
            FtcRobotControllerActivity.t1.speak(Integer.toString(text), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void tts(float text) {

        dt("TTS: " + text);
        if (useTTS) {
            FtcRobotControllerActivity.t1.speak(Float.toString(text), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void tts(double text) {

        dt("TTS: " + text);
        if (useTTS) {
            FtcRobotControllerActivity.t1.speak(Double.toString(text), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void moveLeftArmBlahInches(double inchesToMove) {
        double targetCount = extendMotor1.getCurrentPosition() + inchesToMove / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR;
        //double twoDecimal =  Double.valueOf(newFormat.format(count));
        //double count = extendMotor1.getCurrentPosition();

        ct("Target Count", targetCount);


        if(inchesToMove > 0)
        {
            //move positive
            extendMotor1.setPower(1);
            while(extendMotor1.getCurrentPosition() < targetCount)
            {
                dl("HERE");
            }
            extendMotor1.setPower(0);
        }
       else if(inchesToMove < 0)
        {
            //move negitive
            extendMotor1.setPower(-1);
            while(extendMotor1.getCurrentPosition() > targetCount)
            {
                dl("HERE 2");
            }
            extendMotor1.setPower(0);
        }
        else
        {
            // equels 0
            //dont do anything
            //coder made a mistake
            ct("Number", "??????");
            dt("Dear Coder, You have made a mistake in your code. You can't move 0 inches. It will do nothing. Trust me. I know my math :)");
        }
    }

    public void moveRightArmBlahInches(double inchesToMove) {
        double count = extendMotor2.getCurrentPosition() + (inchesToMove / (TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR));

        if (count > 0) {
            extendMotor2.setPower(1);
            while (extendMotor2.getCurrentPosition() < count) {
                if (Debug) {
                    dt("Right Arm Count: " + count);
                }
            }
            extendMotor2.setPower(0);
        } else {
            extendMotor2.setPower(-1);
            while (extendMotor2.getCurrentPosition() > count) {
                if (Debug) {
                    dt("Right Arm Count: " + count);
                }
            }
            extendMotor2.setPower(0);
        }
    }

    public void resetArmLeft() {
        double count = 0;
        extendMotor1.setPower(-1);
        while (extendMotor1.getCurrentPosition() > count) {
            if (Debug) {
                dt("RESET: Left Arm Count: " + count);
            }
        }
        extendMotor1.setPower(0);
    }

    public void resetArmRight() {
        double count = 0;
        extendMotor2.setPower(-1);
        while (extendMotor2.getCurrentPosition() > count) {
            if (Debug) {
                dt("RESET Right Arm Count: " + count);
            }
        }
        extendMotor2.setPower(0);
    }

    public void resetHitLeft() {
        if (Debug) {
            dt("Reset Hit1");
        }
        hit1.setPosition(1);
    }

    public void resetHitRight() {
        if (Debug) {
            dt("Reset Hit2");
        }
        hit2.setPosition(0);
    }

    public void resetArmHeightLeft() {
        if (Debug) {
            dt("Reset Lift1");
        }
        lift1.setPosition(0.6);
    }

    public void resetArmHeightRight() {
        if (Debug) {
            dt("Reset Lift2");
        }
        lift2.setPosition(0.5);
    }

    public double getLeftTapePos() {
        if (Debug) {
            dt("LeftPos: " + extendMotor1.getCurrentPosition() / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR);
        }
        return extendMotor1.getCurrentPosition() / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR;
    }

    public double getRightTapePos() {
        if (Debug) {
            dt("RightPos: " + extendMotor2.getCurrentPosition() / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR);
        }
        return extendMotor2.getCurrentPosition() / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR;
    }

    public void init() {
        dt("Init Loading...");//start

//if(Debug){dt("");}

        if (Debug) {
          dt("Starting To Register: legacyModule1 = hardwareMap.legacyModule.get(\"Legacy Module 1\");");
         }
        legacyModule1 = hardwareMap.legacyModule.get("Legacy Module 1");
        if(Debug){dt("Finished!");}
        if (Debug) {
            //dt("Starting To Register: servoController1 = hardwareMap.servoController.get(\"Servo Controller 1\");");
        }
       // servoController1 = hardwareMap.servoController.get("Servo Controller 1");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: dcMotorController1 = hardwareMap.dcMotorController.get(\"Motor Controller Arm\");");
        }
        dcMotorController1 = hardwareMap.dcMotorController.get("Motor Controller Arm");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: dcMotorController2 = hardwareMap.dcMotorController.get(\"Motor Controller Back\");");
        }
        dcMotorController2 = hardwareMap.dcMotorController.get("Motor Controller Back");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: dcMotorController3 = hardwareMap.dcMotorController.get(\"Motor Controller Front\");");
        }
        dcMotorController3 = hardwareMap.dcMotorController.get("Motor Controller Front");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: frontLeftMotor = hardwareMap.dcMotor.get(\"left1\");");
        }
        frontLeftMotor = hardwareMap.dcMotor.get("left1");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: frontRightMotor = hardwareMap.dcMotor.get(\"right1\");");
        }
        frontRightMotor = hardwareMap.dcMotor.get("right1");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: backLeftMotor = hardwareMap.dcMotor.get(\"left2\");");
        }
        backLeftMotor = hardwareMap.dcMotor.get("left2");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: backRightMotor = hardwareMap.dcMotor.get(\"right2\");");
        }
        backRightMotor = hardwareMap.dcMotor.get("right2");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: extendMotor1 = hardwareMap.dcMotor.get(\"extend1\");");
        }
        extendMotor1 = hardwareMap.dcMotor.get("extend1");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: extendMotor2 = hardwareMap.dcMotor.get(\"extend2\");");
        }
        extendMotor2 = hardwareMap.dcMotor.get("extend2");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: lift1 = hardwareMap.servo.get(\"lift1\");");
        }
        lift1 = hardwareMap.servo.get("lift1");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: lift2 = hardwareMap.servo.get(\"lift2\");");
        }
        lift2 = hardwareMap.servo.get("lift2");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: hit1 = hardwareMap.servo.get(\"hit1\");");
        }
        hit1 = hardwareMap.servo.get("hit1");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: hit2 = hardwareMap.servo.get(\"hit2\");");
        }
        hit2 = hardwareMap.servo.get("hit2");
        if (Debug) {
            dt("Finished!");
        }
        //if(Debug){dt("Starting To Register: lightSensor = hardwareMap.lightSensor.get(\"light\");");}
        //lightSensor = hardwareMap.lightSensor.get("light");
        //if (Debug) {
        //    dt("Finished!");
        // }

        if(Debug){dt("Starting To Proses: frontRightMotor.setDirection(DcMotor.Direction.REVERSE);");}
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        if (Debug) {
            dt("Finished!");
        }
        if(Debug){dt("Starting To Proses: frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);");}
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        if (Debug) {
            dt("Finished!");
        }if(Debug){dt("Starting To Proses: backRightMotor.setDirection(DcMotor.Direction.REVERSE);");}
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        if (Debug) {
            dt("Finished!");
        }
        if(Debug){dt("Starting To Proses: backLeftMotor.setDirection(DcMotor.Direction.FORWARD);");}
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        if (Debug) {
            dt("Finished!");
        }
        if(Debug){dt("Starting To Proses: extendMotor2.setDirection(DcMotor.Direction.REVERSE);");}
        extendMotor2.setDirection(DcMotor.Direction.FORWARD);
        if (Debug) {
            dt("Finished!");
        }
        if(Debug){dt("Starting To Proses: extendMotor1.setDirection(DcMotor.Direction.FORWARD);");}
        extendMotor1.setDirection(DcMotor.Direction.REVERSE);
        if (Debug) {
            dt("Finished!");
        }

        if(Debug){dt("Starting To Proses: mountainCode = new EAutoCallMountainCode(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, extendMotor1, extendMotor2, telemetry);");}
        mountainCodeHigh = new EAutoCallMountainCodeHigh(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, extendMotor1, extendMotor2, telemetry, lift1, lift2);
        if (Debug) {
            dt("Finished!");
        }
        dt("Init Loaded!"); //end


    }


}
