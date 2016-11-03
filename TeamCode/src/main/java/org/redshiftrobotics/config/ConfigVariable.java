package org.redshiftrobotics.config;

import org.redshiftrobotics.util.ColorPicker.Color;

public class ConfigVariable {
    public final String name;
    double value;
    double min;
    double max;
    double increment;
    protected ConfigType type = ConfigType.DOUBLE;

    public ConfigVariable(String name, double value, double min, double max, double increment){
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public boolean getValueBoolean(){
        if(value > 0) {
            return true;
        }
        return false;
    }

    public ConfigVariable setType(ConfigType type){
        this.type = type;
        return this;
    }

    public Color getValueColor(){return Color.getColor((int) Math.round(value));}

    public int getValueInt(){
        return (int) Math.round(value);
    }

    public double getValueDouble(){
        return value;
    }

    public float getValueFloat(){
        return (float) value;
    }

    public void setValue(double value){
        this.value = value;
    }
}
