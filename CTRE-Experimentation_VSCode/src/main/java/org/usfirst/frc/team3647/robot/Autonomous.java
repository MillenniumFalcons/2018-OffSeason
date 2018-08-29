package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Autonomous
{

    MotionProfileFunctions rightSRXMP = new MotionProfileFunctions(Drivetrain.rightSRX);
    MotionProfileFunctions leftSRXMP = new MotionProfileFunctions(Drivetrain.leftSRX);

    public static void runAuto()
	{
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		int auto = 2; //1 is switch, 2 is scale
		if(auto == 1)
		{
			if(gameData.charAt(0) == 'R')
			{
				//rightSwitch1Cube(lValue, rValue);
			}
			else if(gameData.charAt(0) == 'L')
			{
				//leftSwitch2Cube(lValue, rValue);
			}
			else
			{
				//cross(lValue, rValue);
			}
		}
		else if(auto == 2)
		{
			if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'L')
			{
				//lrScaleFirstSwitchSecond(lValue, rValue);
			}
			else if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'R')
			{
				//rrScaleFirstSwitchSecond(lValue, rValue);
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
			{
				//rightSwitch1Cube(lValue, rValue);
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'L')
			{
				//leftSwitch2Cube(lValue, rValue);
			}
			else
			{
				//cross(lValue, rValue);
			}
		}
		else
		{
			//cross(lValue, rValue);
		}
    }
    
    public void sampleAuto()
    {
        ReadMotionProfileData rightData = new ReadMotionProfileData();
        ReadMotionProfileData leftData = new ReadMotionProfileData();
        int state = 0;
        switch(state)
        {
            case 0:
            //zero elevator
            state = 1;
            break;
            case 1:
            rightSRXMP.runTalon("righttest.csv");
            leftSRXMP.runTalon("lefttest.csv");
            state = 2;
            break;
            case 2:
            while(!rightSRXMP.finishedPath && !leftSRXMP.finishedPath)
            {
                state = 2;
            }
            if(rightSRXMP.finishedPath && leftSRXMP.finishedPath)
            {
            //drop cube off and stuff
            state = 3;
            }
            break;
            case 3:
            //back up 
            rightSRXMP.runTalon("righttestpart2.csv");
            leftSRXMP.runTalon("lefttestpart2.csv");
            state = 4;
            break;
        }
    }
}