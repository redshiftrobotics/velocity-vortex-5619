package org.redshiftrobotics.DeadReckoning;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public class DRMap {

    public static final int TETRIX_ENCODER_CPR = 1440;

    private int ENCODER_CPR;
    private double GEAR_RATIO;
    private double WHEEL_DIAMETER;
    private double CIRCUMFERENCE;

    private ArrayList<DRPath> paths = new ArrayList<DRPath>();

    public DRMap(int encoder_cpr, double gear_ratio, double wheel_diameter_in_cem){
        this.ENCODER_CPR = encoder_cpr;
        this.GEAR_RATIO = gear_ratio;
        this.WHEEL_DIAMETER = wheel_diameter_in_cem;
        CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    }

    public double getEncoderCount(double distance_in_cem){
        final double DISTANCE = distance_in_cem;
        final double ROTATIONS = DISTANCE / CIRCUMFERENCE;
        final double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
        return COUNTS;
    }

    public void addPath(double distance, DRDirection direction){
        paths.add(new DRPath(distance, direction));
    }

    public void addPath(double distance){
        paths.add(new DRPath(distance));
    }

    public void run(DcMotor left, DcMotor right){
        for(DRPath p:paths){
            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            double ec = getEncoderCount(p.getDistance());

            switch(p.getDirection()){
                case FORWARD:
                    left.setTargetPosition((int)ec);
                    right.setTargetPosition((int)ec);
                    left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    left.setPower(p.getPower());
                    right.setPower(p.getPower());
                    break;

                case BACKWARDS:
                    left.setTargetPosition((int)ec);
                    right.setTargetPosition((int)ec);
                    left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    left.setPower(p.getPower());
                    right.setPower(p.getPower());
                    break;
            }


        }
    }

}
