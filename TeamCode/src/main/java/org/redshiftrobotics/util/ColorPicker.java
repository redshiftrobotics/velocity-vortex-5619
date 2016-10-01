package org.redshiftrobotics.util;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Eric Golde on 10/1/2016.
 */
public class ColorPicker {

    public ColorPicker(){

    }

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
        if(color == Color.RED){
            return "RED";
        }
        else if(color == Color.YELLOW){
            return "YELLOW";
        }
        else if(color == Color.GREEN){
            return "GREEN";
        }
        else if(color == Color.BLUE){
            return "BLUE";
        }
        else if(color == Color.PURPLE){
            return "PURPLE";
        }
        else if(color == Color.BLACK){
            return "BLACK";
        }
        else if(color == Color.WHITE) {
            return "WHITE";
        }
        else{
            return "UNKNOWN_COLOR";
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
