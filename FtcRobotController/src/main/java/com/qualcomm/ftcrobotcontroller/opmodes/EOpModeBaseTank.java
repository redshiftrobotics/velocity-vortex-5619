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
public abstract class EOpModeBaseTank extends OpMode {

    String teleConvert;
    int teleInt = 0;


    public static boolean Debug = false; //outputs a lot of text for debugging purpuses


    protected DcMotor left;
    protected DcMotor right;
    protected DcMotor arm;
    protected Servo hit1;
    protected Servo hit2;
    protected Servo armServo;
    protected Servo climberExtend;
    protected Servo climberDrop;



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



    public void init() {
        dt("Init Loading...");//start


        if (Debug) {
            dt("Starting To Register: left motor");
        }
        left = hardwareMap.dcMotor.get("left");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: right motor");
        }
        right = hardwareMap.dcMotor.get("right");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: arm");
        }
        arm = hardwareMap.dcMotor.get("arm");
        if (Debug) {
            dt("Finished!");
        }

        if (Debug) {
            dt("Starting To Register: hit1");
        }
        hit1 = hardwareMap.servo.get("hit1");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: hit2");
        }
        hit2 = hardwareMap.servo.get("hit2");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: armServo");
        }
        armServo = hardwareMap.servo.get("armServo");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: climberExtend");
        }
        climberExtend = hardwareMap.servo.get("climberExtend");
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Register: climberDrop");
        }
        climberDrop = hardwareMap.servo.get("climberDrop");
        if (Debug) {
            dt("Finished!");
        }
        ////




        if (Debug) {
            dt("Starting To Proses: left");
        }
        left.setDirection(DcMotor.Direction.FORWARD);
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Proses: right");
        }
        right.setDirection(DcMotor.Direction.REVERSE);
        if (Debug) {
            dt("Finished!");
        }
        if (Debug) {
            dt("Starting To Proses: arm");
        }
        arm.setDirection(DcMotor.Direction.FORWARD);
        if (Debug) {
            dt("Finished!");
        }


        dt("Init Loaded!"); //end


    }


}
