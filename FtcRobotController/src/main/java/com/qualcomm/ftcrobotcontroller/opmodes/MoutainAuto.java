package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


public class MoutainAuto extends EOpModeBase
{
    enum powerState {spowerHigh, spowerMedium, spowerLow}
    powerState state;
    long tStart = System.currentTimeMillis();
    long tEnd = System.currentTimeMillis();
    long tDelta = tEnd - tStart;
    double elapsedSeconds = tDelta / 1000.0;

    @Override
    public void init()
    {
        //frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        //frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        super.init();
        state=state.spowerLow;
    }

    @Override
    public void stop()
    {
        super.stop();
    }

    @Override
    public void loop()
    {
        //extendMotor1.setPower(.8);
        //extendMotor2.setPower(.8);

        switch (state)
        {
            case spowerLow:
                frontRightMotor.setPower(.2);
                frontLeftMotor.setPower(.2);
                backRightMotor.setPower(.2);
                backLeftMotor.setPower(.2);

                if (elapsedSeconds> 5)
                {
                    state= state.spowerMedium;
                }
            case spowerMedium:
                frontLeftMotor.setPower(.6);
                frontRightMotor.setPower(.6);
                backLeftMotor.setPower(.6);
                backRightMotor.setPower(.6);
                if (elapsedSeconds> 10)
                {
                    state=state.spowerHigh;
                }
            case spowerHigh:
                frontLeftMotor.setPower(1);
                frontRightMotor.setPower(1);
                backLeftMotor.setPower(1);
                backRightMotor.setPower(1);
        }

    }
}
