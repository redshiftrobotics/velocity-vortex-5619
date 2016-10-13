package org.redshiftrobotics.config;

/**
 * Created by Eric Golde on 9/16/2016.
 */
import org.redshiftrobotics.config.ConfigType;
import org.redshiftrobotics.util.ColorPicker;

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

    public ColorPicker.Color getValueColor(){return ColorPicker.Color.getColor((int) Math.round(value));}

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
