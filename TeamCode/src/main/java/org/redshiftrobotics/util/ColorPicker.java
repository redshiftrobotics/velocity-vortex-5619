package org.redshiftrobotics.util;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Eric Golde on 10/1/2016.
 */
public class ColorPicker {

    /*
    this is a class I made to return solid colors from the color sensor
     */
    public ColorPicker(){
        //this needs to be here, cause withiut it bad stuff happends
    }

    private int sensitivity = 80;

    public void setSensitivity(int sensitivity){
        this.sensitivity = sensitivity;
    }

    //returns a color based off of the color sensor
    public Color whatColor(ColorSensor colorSensor){
            if(colorSensor.red() > sensitivity && colorSensor.green() < sensitivity && colorSensor.blue() < sensitivity){
                return Color.RED;
            }
            else if(colorSensor.red() > sensitivity && colorSensor.green() > sensitivity && colorSensor.blue() < sensitivity){
                return Color.YELLOW;
            }
            else if(colorSensor.red() < sensitivity && colorSensor.green() > sensitivity && colorSensor.blue() < sensitivity){
                return Color.GREEN;
            }
            else if(colorSensor.red() < sensitivity && colorSensor.green() < sensitivity && colorSensor.blue() > sensitivity){
                return Color.BLUE;
            }
            else if(colorSensor.red() > sensitivity && colorSensor.green() < sensitivity && colorSensor.blue() > sensitivity){
                return Color.PURPLE;
            }
            else if(colorSensor.red() < sensitivity && colorSensor.green() < sensitivity && colorSensor.blue() < sensitivity){
                return Color.BLACK;
            }
            else if(colorSensor.red() > sensitivity && colorSensor.green() > sensitivity && colorSensor.blue() > sensitivity){
                return Color.WHITE;
            }
            else {
                return Color.UNKNOWN_COLOR;
            }
    }

    public String toString(Color color){
        switch(color) {
            case RED:  return "RED";
            case YELLOW:  return "YELLOW";
            case GREEN:  return "GREEN";
            case BLUE:  return "BLUE";
            case PURPLE:  return "PURPLE";
            case WHITE:  return "WHITE";
            case BLACK:  return "BLACK";
            default: return "UNKNOWN_COLOR";
        }
    }

    public enum Color{
        RED,
        YELLOW,
        GREEN,
        BLUE,
        PURPLE,
        BLACK,
        WHITE,
        UNKNOWN_COLOR
    }

}
