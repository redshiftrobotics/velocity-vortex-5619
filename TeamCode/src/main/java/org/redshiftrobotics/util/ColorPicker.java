package org.redshiftrobotics.util;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Eric Golde on 10/1/2016.
 */
public class ColorPicker {

    private int sensitivity = 80;

    public void setSensitivity(int sensitivity){
        this.sensitivity = sensitivity;
    }

    public int whatColor(ColorSensor colorSensor){
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

    public String toString(int color){
        switch(color) {
            case Color.RED:  return "RED";
            case Color.YELLOW:  return "YELLOW";
            case Color.GREEN:  return "GREEN";
            case Color.BLUE:  return "BLUE";
            case Color.PURPLE:  return "PURPLE";
            case Color.WHITE:  return "WHITE";
            case Color.BLACK:  return "BLACK";
            default: return "UNKNOWN_COLOR";
        }
    }


    public class Color {
        public static final int RED = 1;
        public static final int YELLOW = 2;
        public static final int GREEN = 3;
        public static final int BLUE = 4;
        public static final int PURPLE = 5;
        public static final int BLACK = 6;
        public static final int WHITE = 7;
        public static final int UNKNOWN_COLOR = 8;
    }

}
