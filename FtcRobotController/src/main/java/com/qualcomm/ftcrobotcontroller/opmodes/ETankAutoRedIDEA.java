package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Eric Golde on 1/22/2016.
 */
public class ETankAutoRedIDEA extends EOpModeBaseTank { //USE E-TANK-AUTO-RED-OLD

    final int epTurnOffOfStartingPlatform = 700;
    final int epAmountToDriveForwardBeforeTurning = epTurnOffOfStartingPlatform + 1500;
    final int epAmountToTurnUntilYouHitTheLine = epAmountToDriveForwardBeforeTurning + 700;
    final int epAmountToDriveForwardBeforeYouHitTheClimberBucket = epAmountToTurnUntilYouHitTheLine + 800;

    final int epAmountToExtendClimberArmServoToDropClimbersInTheBucket = 1; //servo int 0-1

//////////////////////////
    int servoPos;
    boolean finishedExtendingArmForBucket = false; //*****DO NOT OVERRIDE****
    boolean finishRetractingTheArmForBucket = false; //*****DO NOT OVERRIDE****
    boolean doneUsingTheArmForBucket = false; //*****DO NOT OVERRIDE****
    boolean doneDroppingClimbers = false; //*****DO NOT OVERRIDE****
    boolean doneDoingEverythingWithServosInAutonomous = false; //*****DO NOT OVERRIDE****


    @Override
    public void init()
    {
        dt("Red Team Autonomous Selected!");
        super.init();
    }

    @Override
    public void start()
    {
        turnOffOfStartingPos();
    }

    @Override
    public void loop()
    {
        if(right.getCurrentPosition() >= epTurnOffOfStartingPlatform)
        {
            //when you finish turning off of the starting plat form,
            //you should start driving forward now.
            driveForwardToAroundWhiteLine();
        }
        else if(right.getCurrentPosition() >= epAmountToDriveForwardBeforeTurning)
        {
            //when your done driving forward
            //you should start to turn on to the line now
            turnToAlineWithWhiteLine();;
        }
        else if(right.getCurrentPosition() >= epAmountToTurnUntilYouHitTheLine)
        {
            //you now have finished and you have hit the line.
            //you should now start driving forward slowly so you can dump the people into bucket
            driveSlowlyToPositionRobotToDumpClimbers();
        }
        else if(right.getCurrentPosition() >= epAmountToDriveForwardBeforeYouHitTheClimberBucket)
        {
            //you now have stoped to you can extend the climber thingy and dump the climbers
            //we need some kind of wait or for loop to delay.
            //this cant be thread.sleep i dont think.
            //well it can but that can cause other problems
            if(extendArmForBucket())
            {
                finishedExtendingArmForBucket = true;
            }
        }
        else if(finishedExtendingArmForBucket && doneUsingTheArmForBucket == false && doneDoingEverythingWithServosInAutonomous == false)
        {
            //servo arm has finished extending
            //dump
            if(dropClimbersInBucket())
            {
                doneDroppingClimbers = true;
            }
        }
        else if(finishedExtendingArmForBucket && doneDroppingClimbers && doneDoingEverythingWithServosInAutonomous == false)
        {
            //retract the arm
            if(retractArmForBucket())
            {
                finishedExtendingArmForBucket = true;
                doneDoingEverythingWithServosInAutonomous = true;
            }
        }
        else if(finishedExtendingArmForBucket && finishRetractingTheArmForBucket && doneDroppingClimbers && doneDoingEverythingWithServosInAutonomous)
        {
            //you have now finished retracting the arm
            //NOW WHAT??
            //DRIVE AWAY??
            //--Need more instructions--
            //drive up ramp
        }


    }

    public void turnOffOfStartingPos()
    {
        left.setPower(.3);
        right.setPower(.4);
    }
    public void driveForwardToAroundWhiteLine()
    {
        left.setPower(.5);
        right.setPower(.5);
    }
    public void turnToAlineWithWhiteLine()
    {
        left.setPower(.2);
        right.setPower(.3);
    }
    public void driveSlowlyToPositionRobotToDumpClimbers()
    {
        left.setPower(.2);
        right.setPower(.2);
    }
    public boolean extendArmForBucket()
    {
        return true; //CHANGE THIS
    }
    public boolean retractArmForBucket()
    {
        return true; //CHANGE THIS
    }
    public boolean dropClimbersInBucket()
    {
        return true; //CHANGE THIS
    }


}
