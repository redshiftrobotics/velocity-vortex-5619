package org.redshiftrobotics.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

public class Util {
    OpMode op;

    public Util(OpMode opMode){
        this.op = opMode;
    }

    public void log(String thing, Object obj){
        op.telemetry.addData(thing, String.valueOf(obj));}

    public void log(Object obj){
        op.telemetry.addLine(String.valueOf(obj));
    }

    public void log(ArrayList<Object> obj){
        for(Object msg:obj){
            log(msg);
        }
        updateTelemetry();
    }

    public void writeLine (Object obj){
        op.telemetry.addLine(String.valueOf(obj));
        updateTelemetry();
    }

    public void clearTelemetry(){
        log("");
        updateTelemetry();
    }

    public void updateTelemetry(){
        op.updateTelemetry(op.telemetry);
    }

    public void console(Object obj){
        System.out.println(obj);
    }

    public void stopOPMode(){
        op.requestOpModeStop();
    }
}
