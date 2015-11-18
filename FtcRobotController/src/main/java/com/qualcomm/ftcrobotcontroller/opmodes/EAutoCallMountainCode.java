package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Eric Golde on 11/18/2015.
 */
public class EAutoCallMountainCode {


    /*
   THIS IS A FILE TO HAVE MY 8 AUTONOMIS CODE CALL MountainAutoStateTest11_15
   THIS IS A PARTICAL OP MODE
    */

    protected DcMotor frontLeftMotor; //FRONT LEFT
    protected DcMotor frontRightMotor; //FRONT RIGHT
    protected DcMotor backLeftMotor; //BACK LEFT
    protected DcMotor backRightMotor; //BACK RIGHT
    protected DcMotor extendMotor1; // arm1
    protected DcMotor extendMotor2; // arm2
    protected Telemetry telemetry;

    String teleConvert;
    int teleInt = 0;

    public EAutoCallMountainCode(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, DcMotor extendMotor1, DcMotor extendMotor2, Telemetry telemetry)
    {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;
        this.extendMotor1 = extendMotor1;
        this.extendMotor2 = extendMotor2;
        this.telemetry = telemetry;
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
    }

    public void ct(String what, String text) //Debug text single line. Usefull for printing state changes
    {
        telemetry.addData(what, text);
    }


    public void init()
    {
        dt("Driving up mountain!");
        extendMotor1.setPower(0);
        extendMotor2.setPower(0);
    }

    public void loop()
    {
        dt("If you see this then you have sunceesfully called an partical opmode from a op mode!");
        //INPUT MADDYS LOOP AND OTHER FUNTIONS HERE!(DONT FORGET VARIABLES AS WELL)
    }
}
