package org.redshiftrobotics.config;

/**
 * Created by Eric Golde on 9/16/2016.
 */
public class ConfigVariable {
    public final String name;
    double value;
    double min;
    double max;
    double increment;
    protected int type = ConfigType.TYPE_DOUBLE;

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

    public ConfigVariable setType(int type){
        this.type = type;
        return this;
    }

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
