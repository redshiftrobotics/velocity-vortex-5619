package org.firstinspires.ftc.teamcode.utilities;

/**
 * FTC StopWatch class that was in the advanced coding powerpoint
 *
 * Created by Eric Golde on 9/14/2017.
 */

public class StopWatch {

	long mLastTime = 0;
	int mWaitTime = 0;


	public StopWatch(int waitTime){
		mWaitTime = waitTime;
	}

	public void setTime(int waitTime){
		mWaitTime = waitTime;
	}

	public boolean isExpired(){
		if((System.currentTimeMillis() - mLastTime) > mWaitTime){
			return true;
		}
		return false;
	}

	public void reset(){
		mLastTime = System.currentTimeMillis();
	}

}
