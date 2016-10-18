package org.redshiftrobotics.config;

import java.util.ArrayList;

public class Config {
    public ArrayList<ConfigVariable> variables = new ArrayList<ConfigVariable>();

    public void addVariable(ConfigVariable variable){
        variables.add(variable);
    }

    public void clearArray(){
        variables.clear();
    }
}
