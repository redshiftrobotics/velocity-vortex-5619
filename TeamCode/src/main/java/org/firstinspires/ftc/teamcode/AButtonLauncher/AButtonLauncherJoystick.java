package org.firstinspires.ftc.teamcode.AButtonLauncher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 11/1/2016.
 */
@TeleOp(name="ALaunchJoystick", group="Launcher")
public class AButtonLauncherJoystick extends OpMode {
    DcMotor launcher;

    @Override
    public void init() {
        launcher = hardwareMap.dcMotor.get("launcher");
        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        launcher.setPower(Range.clip((double)-gamepad1.left_stick_y, -1, 1));
        telemetry.addData("Pos", launcher.getCurrentPosition());
        updateTelemetry(telemetry);
    }
}
