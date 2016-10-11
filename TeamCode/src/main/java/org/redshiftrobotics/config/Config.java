package org.redshiftrobotics.config;

import java.util.ArrayList;

/**
 * Created by Eric Golde on 9/16/2016.
 */
public class Config {
    public ArrayList<ConfigVariable> variables = new ArrayList<ConfigVariable>();

    public void addVariable(ConfigVariable variable){
        variables.add(variable);
    }

    public void clearArray(){
        variables.clear();
    }
}
