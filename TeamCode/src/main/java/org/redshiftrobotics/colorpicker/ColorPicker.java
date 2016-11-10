package org.redshiftrobotics.colorpicker;

import com.qualcomm.robotcore.hardware.ColorSensor;

/*
this is a class I made to return solid colors from the color sensor
*/
public class ColorPicker {
    public ColorPicker(){
        //this needs to be here, cause without it bad stuff happends.
    }

    private int sensitivity = 80;

    public void setSensitivity(int sensitivity){
        this.sensitivity = sensitivity;
    }

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

    public enum Color{
        RED ("RED", 1),
        YELLOW ("YELLOW", 2),
        GREEN ("GREEN", 3),
        BLUE ("BLUE", 4),
        PURPLE ("PURPLE", 5),
        BLACK ("WHITE", 6),
        WHITE ("BLACK", 7),
        UNKNOWN_COLOR ("UNKNOWN_COLOR", 8);

        final String name;
        final int number;

        Color(String name, int number){
            this.name = name;
            this.number = number;
        }

        public static Color getColor(int number){
            switch(number) {
                case 1:  return Color.RED;
                case 2:  return Color.YELLOW;
                case 3:  return Color.GREEN;
                case 4:  return Color.BLUE;
                case 5:  return Color.PURPLE;
                case 6:  return Color.WHITE;
                case 7:  return Color.BLACK;
                default: return Color.UNKNOWN_COLOR;
            }
        }

        public static Color getColor(String name){
            switch(name) {
                case "RED":  return Color.RED;
                case "YELLOW":  return Color.YELLOW;
                case "GREEN":  return Color.GREEN;
                case "BLUE":  return Color.BLUE;
                case "PURPLE":  return Color.PURPLE;
                case "WHITE":  return Color.WHITE;
                case "BLACK":  return Color.BLACK;
                default: return Color.UNKNOWN_COLOR;
            }
        }

        public int getNumber(){
            return number;
        }
        public static int getNumber(String name){return getColor(name).getNumber();}

        public String getName(){
            return name;
        }
        public static String getName(int number){return getColor(number).getName();}

        public static final int MIN = 1;
        public static final int MAX = 8;
    }
}
