/*
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS DOESNT DO ANYTHING
THIS IS SOMETHING STUPID I MADE THEY I MAY PICK UP LATER FOR THE SECOND COMITITION. IT DOES US NO GOOD TO WORK ON IT NOW.
 */

package com.qualcomm.ftcrobotcontroller.opmodes;


import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;

import com.qualcomm.ftcrobotcontroller.R;



/**
 * Created by Eric Golde on 11/18/2015.
 */
public class EWireCheck extends EOpModeBase {

    private EOpModeBase base;
    private String status = "Encoder Status";
    private String direction = "Encoder Direction";
    private String working = "Working!";
    private String notworking = "NOT WORKING";
    private String positive = "Positive Direction ()";
    private String negitive = "Negitive Direction";
    private String isNull = "Null";
    private int secondsToWait = 7;

    public void sleep()
    {
        // Execute some code after 2 seconds have passed
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //return?
            }
        }, secondsToWait);
    }

    public void init()
    {
       super.init();
        dt("Testing Every connection.");
    }

    public void loop()
    {

        dt("Testing FRONT-LEFT-MOTOR");
        frontLeftMotor.setPower(1);
        int frontLeftMotorCurrentPosition = frontLeftMotor.getCurrentPosition();
        if(frontLeftMotorCurrentPosition == 0)
        {
            //encoders not working
            ct(status, notworking);
            ct(direction, isNull);
        }
        else
        {
            //encoder working
            if(frontLeftMotorCurrentPosition > 0)
            {
                //positive
                ct(status, working);
                ct(direction, positive);
            }
            else
            {
                ct(status, working);
                ct(direction, negitive);
            }
        }

        SystemClock.sleep(secondsToWait * 1000);

    }
}
