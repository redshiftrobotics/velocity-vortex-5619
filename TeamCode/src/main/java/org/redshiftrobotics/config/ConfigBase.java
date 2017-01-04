package org.redshiftrobotics.config;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.text.DecimalFormat;

public class ConfigBase extends OpMode {
	protected Config config;
	Gamepad gp;

	boolean lastStateNext = false;
	boolean lastStateBack = false;
	boolean lastStateUp = false;
	boolean lastStateDown = false;

	int currentVar = 0;

	@Override
	public void init() {
		gp = gamepad1;
		currentVar = 0;
		telemetry.addLine("Ready to start configuring? Press the A button!");
		telemetry.addLine("");
		telemetry.addLine("Controls:");
		telemetry.addLine("  A: Next Page");
		telemetry.addLine("  B: Back Page");
		telemetry.addLine("  Y: Increment Up");
		telemetry.addLine("  X: Increment Down");
		updateTelemetry(telemetry);
	}

	@Override
	public void start(){showCurrentVariable();}

	@Override
	public void stop(){telemetry.addLine("Done configuring. Please exit this OPMode.");}

	@Override
	public void loop() {

		if(gp.a && !lastStateNext){
			nextPressed();
		}

		if(gp.b && !lastStateBack){
			backPressed();;
		}

		if(gp.y && !lastStateUp){
			incrementUpPressed();
		}

		if(gp.x && !lastStateDown){
			incrementDownPressed();
		}

		lastStateNext = gp.a;
		lastStateBack = gp.b;
		lastStateUp = gp.y;
		lastStateDown = gp.x;
	}

	void nextPressed(){
		int max = config.variables.size();

		if(currentVar >= max - 1){
			telemetry.clearAll();
			updateTelemetry(telemetry);
			currentVar=max;
			return;
		}

		currentVar++;
		showCurrentVariable();
	}

	void backPressed(){
		if(currentVar != 0) {
			currentVar--;
		}
		showCurrentVariable();
	}

	void incrementUpPressed(){
		if(currentVar >= config.variables.size()){
			return; //Return so it doesn't crash
		}
		double cur = config.variables.get(currentVar).getValueDouble();
		double inc = config.variables.get(currentVar).increment;
		double max = config.variables.get(currentVar).max;
		double min = config.variables.get(currentVar).min;

		cur = cur + inc;

		if(cur > max){
			cur = max;
		}

		if(cur < min){
			cur = min;
		}

		config.variables.get(currentVar).setValue(cur);
		showCurrentVariable();
	}

	void incrementDownPressed(){
		if(currentVar >= config.variables.size()){
			return; //Return so it doesn't crash
		}

		double cur = config.variables.get(currentVar).getValueDouble();
		double inc = config.variables.get(currentVar).increment;
		double max = config.variables.get(currentVar).max;
		double min = config.variables.get(currentVar).min;

		cur = cur - inc;

		if(cur > max){
			cur = max;
		}

		if(cur < min){
			cur = min;
		}

		config.variables.get(currentVar).setValue(cur);
		showCurrentVariable();
	}

	void showCurrentVariable(){
		if(currentVar >= config.variables.size()){
			return; //Return so it doesn't crash
		}

		telemetry.addData("Variable", config.variables.get(currentVar).name);

		//This is messy but makes a nice result for the user.
		if(config.variables.get(currentVar).type == ConfigType.DOUBLE){
			DecimalFormat df = new DecimalFormat("#.##");
			telemetry.addData("Value", df.format(config.variables.get(currentVar).getValueDouble()));

		}else if(config.variables.get(currentVar).type == ConfigType.BOOLEAN){

			telemetry.addData("Value", config.variables.get(currentVar).getValueBoolean());

		}else if(config.variables.get(currentVar).type == ConfigType.FLOAT){
			DecimalFormat df = new DecimalFormat("#.##########");
			telemetry.addData("Value", df.format(config.variables.get(currentVar).getValueFloat()));

		}else if(config.variables.get(currentVar).type == ConfigType.INT){

			telemetry.addData("Value", config.variables.get(currentVar).getValueInt());

		}else{
			telemetry.addLine("This should have never happened... (Line 157 in ConfigBase). Please exit the OPMode and ask Eric to fix this bug.");
		}
		updateTelemetry(telemetry);
	}
}
