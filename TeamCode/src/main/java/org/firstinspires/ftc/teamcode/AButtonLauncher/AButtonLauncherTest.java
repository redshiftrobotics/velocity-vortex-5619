package org.firstinspires.ftc.teamcode.AButtonLauncher;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Eric Golde on 10/25/2016.
 */
@TeleOp(name="ALaunch", group="Launcher")
public class AButtonLauncherTest extends OpMode{

    DcMotor launcher;
    double power = 0.5;

    @Override
    public void init() {
        launcher = hardwareMap.dcMotor.get("launcher");
        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
int pos = 0;
    @Override
    public void loop() {

        while(launcher.getCurrentPosition() < AButtonLauncherConfig.ec.getValueInt()){
            telemetry.addData("State", "WHILE LOOP");
            pos = launcher.getCurrentPosition();
            //wind p the motor
            launcher.setPower(power);
            telemetry.addLine("Pos: " + pos + " | " + AButtonLauncherConfig.ec.getValueInt());
            updateTelemetry(telemetry);

        }
        if(gamepad1.a){
            launcher.setPower(power);
            telemetry.addData("State", "SET POWER");
            telemetry.addLine("Pos: " + pos + " | " + AButtonLauncherConfig.ec.getValueInt());
            updateTelemetry(telemetry);

            launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            telemetry.addData("State", "STOP AND RESET ENCODER");
            telemetry.addLine("Pos: " + pos + " | " + AButtonLauncherConfig.ec.getValueInt());
            updateTelemetry(telemetry);

            launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addData("State", "RUN USING ENCODER");
            updateTelemetry(telemetry);

            pos = 0;
            telemetry.addData("State", "POS = 0");
            telemetry.addLine("Pos: " + pos + " | " + AButtonLauncherConfig.ec.getValueInt());
            updateTelemetry(telemetry);

            telemetry.addData("State", "WAITING FOR 1 SEC");
            telemetry.addLine("Pos: " + pos + " | " + AButtonLauncherConfig.ec.getValueInt());
            updateTelemetry(telemetry);
            this.resetStartTime();
            if (this.getRuntime() > 1.0) { //this will wait 1 second; change the value to wait a different amount of tim
                return;
            }
            telemetry.addData("State", "IT HAS BEEN 1 SEC");
            telemetry.addLine("Pos: " + pos + " | " + AButtonLauncherConfig.ec.getValueInt());
            updateTelemetry(telemetry);
            pos = 0;
        }else{
            telemetry.addData("State", "HOLDING STILL");
            telemetry.addLine("Pos: " + pos + " | " + AButtonLauncherConfig.ec.getValueInt());
            updateTelemetry(telemetry);
            launcher.setPower(0);
        }
        updateTelemetry(telemetry);
    }

}

