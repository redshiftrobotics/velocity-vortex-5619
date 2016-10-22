package org.redshiftrobotics.DeadReckoning;

/**
 * Created by Eric Golde on 10/20/2016.
 */
public class DRPath {

    private double distance;
    private DRDirection direction;
    private double power = 1;

    public DRPath(double distance){
        this.distance = distance;
        this.direction = DRDirection.FORWARD;
    }

    public DRPath(double distance, DRDirection direction){
        this.distance = distance;
        this.direction = direction;
    }

    public void setDistance(double distance){this.distance = distance;}

    public double getDistance(){return distance;}

    public void setDirection(DRDirection direction){
        this.direction = direction;
    }

    public DRDirection getDirection(){
        return direction;
    }

    public void setPower(double power){this.power = power;}

    public double getPower(){return power;}

}
