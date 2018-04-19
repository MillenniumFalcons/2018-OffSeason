package team3647ConstantsAndFunctions;

import team3647elevator.Elevator;
import team3647elevator.ElevatorLevel;

public class Functions 
{
	static double avg;
	static boolean reached;
	
	public static double stopToPickUp(double eValue)
	{
		eValue*=(-0.00005);
		eValue+=.3;
		return eValue;
	}
	
	public static double stopToSwitch(double eValue)
	{
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.8 to .2	
	}
	
	public static double stopToScale(double eValue)
	{
		
		if(eValue < 25000)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			return eValue;
			//.85 to .25
		}
	}
	
	public static double pickUpToStop(double eValue)
	{
		return -.2;
	}
	
	public static double pickUpToSwitch(double eValue)
	{
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.7 to .2
	}
	
	public static double pickUpToScale(double eValue)
	{
		
		if(eValue < 25000)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			return eValue;
			//.85 to .25
		}
	}
	
	public static double switchToStop(double eValue)
	{
		if(eValue > 5000)
		{
			return -.4;
		}
		else
		{
			return - .18;
		}
	}
	
	public static double switchToPickUp(double eValue)
	{
		if(eValue > 5000)
		{
			return -.35;
		}
		else
		{
			return - .16;
		}
	}
	
	public static double switchToScale(double eValue)//
	{
		if(eValue < 20000)
		{
			
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			
			return eValue;
			//.85 to .25
		}
		
		//.8 to .2
	}
	
	public static double stopToLowerScale(double eValue)
	{
		if(eValue<Constants.sWitch)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000230769230769);
			eValue+=1.031;
			return eValue;
		}
	}
	
	public static double pickUpToLowerScale(double eValue)
	{
		if(eValue<Constants.sWitch)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000230769230769);
			eValue+=1.031;
			return eValue;
		}
	}
	
	public static double switchToLowerScale(double eValue)
	{
		eValue*=(-0.0000230769230769);
		eValue+=1.031;
		return eValue;
	}
	
	public static double scaleToLowerScale(double eValue)
	{
		return -.2;
	}
	
	public static double lowerScaleToScale(double eValue)
	{
		
		if(eValue < 40000)
		{
			return .4;
		}
		else
		{
			return .2;
		}
	}
	
	public static double lowerScaleToSwitch(double eValue)
	{
		return -.28;
	}
	
	public static double lowerScaleToPickUp(double eValue)
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.15;
		}
	}
	
	public static double lowerScaleToStop(double eValue)
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.17;
		}
	}
	
	public static double scaleToStop(double eValue)//
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.17;
		}
	}
	
	public static double scaleToPickUp(double eValue)//
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.15;
		}
	}
	
	public static double scaleToSwitch(double eValue)
	{
		return -.32;
	}
	
	public static double test1and3Straight(double lValue, double rValue, double dist)
	{
		avg = (lValue + rValue)/2.0;
		if(avg < 1800)
		{
			return .6;
		}
		else if(avg < 4000)
		{
			return .74;
		}
		else if(avg < 7000)
		{
			return .87;
		}
		else if(avg < 10000)
		{
			return 1;
		}
		else if(avg < (dist -2000))
		{
			return .74;
		}
		else if(avg < dist)
		{
			return .34;
		}
		else
		{
			return 0;
		}
		//assuming distance 17000
	}
	
	
	public static double test3Turn(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .7;
		}
		else if(eValue < dist)
		{
			return .5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double test4Turn(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.7;
		}
		else if(eValue < dist)
		{
			return -.5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double test5Turn(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.7;
		}
		else if(eValue < dist)
		{
			return -.5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double testCurve(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 1000))
		{
			return .6;
		}
		else if(eValue < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	//Competition Stuff
	public static double oneCubeSwitchRightSideStraight(double sum, double dist)
	{
		if(sum < 2000)
		{
			return .6;
		}
		else if(sum < (dist-1500))
		{
			return .8;
		}
		else if(sum < dist)
		{
			return .4;
		}
		else
		{
			return 0;
		}
	}
	
	public static double oneCubeSwitchRightSideCurve(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 1000))
		{
			return .6;
		}
		else if(eValue < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchRightSideStraight(double sum, double dist)
	{
		if(sum < 2000)
		{
			return .4;
		}
		else if(sum < (dist-2500))
		{
			return .6;
		}
		else if(sum < (dist-1500))
		{
			return .45;
		}
		else if(sum < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchRightSideCurve(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < 1500)
		{
			return .5;
		}
		else if(eValue < (dist - 2000))
		{
			return .6;
		}
		else if(eValue < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchLeftSideFirstCurve(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .4;
		}
		else if(eValue < (dist))
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchLeftSideStraightCrossField(double eValue, double dist)
	{
		if(eValue < 3000)
		{
			return .5;
		}
		else if(eValue < (dist-1500))
		{
			return .7;
		}
		else if(eValue < (dist))
		{
			return .4;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchLeftSideSecondCurve(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .4;
		}
		else if(eValue < (dist))
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static void lrandrrElevatorForFirstScale(double lValue, double rValue, double eValue, double step)
	{
		if(step == 1)
		{
			reached = false;
			avg = (lValue + rValue)/2.0;
			if(avg < 10000)
			{
				if(eValue < 8000)
				{
					Elevator.moveEleVader(.6);
				}
				else if(!ElevatorLevel.reachedSwitch())
				{
					Elevator.moveEleVader(.3);
				}
				else
				{
					ElevatorLevel.maintainSwitchPosition();
				}
			}
			else
			{
				Elevator.moveEleVader(Functions.switchToScale(ElevatorLevel.elevatorEncoderValue));
			}
		}
	}
	
	public static double lrandrrSpeedForFirstScale(double lValue, double rValue, double dist)
	{
		avg = (lValue + rValue)/2.0;
		if(avg < 2000)
		{
			return .6;
		}
		else if(avg < 5000)
		{
			return .74;
		}
		else if(avg < 14000)
		{
			return .9;
		}
		else if(avg < (dist -1200))
		{
			return .7;
		}
		else if(avg < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double lrandrrFirstTurnToScale(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .7;
		}
		else if(eValue < dist)
		{
			return .5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double lrandrrBackUpTurnAfterScale(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.7;
		}
		else if(eValue < dist)
		{
			return -.5;
		}
		else
		{
			return 0;
		}
	}
	
	public static void moveElevatorToStop(double eValue)
	{
		if(!ElevatorLevel.reachedStop())
		{
			Elevator.moveEleVader(scaleToStop(eValue));
		}
		else
		{
			Elevator.stopEleVader();
		}
	}
	
	public static double lrandrrBackUpToWallTurn(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.6;
		}
		else if(eValue < dist)
		{
			return -.4;
		}
		else
		{
			return 0;
		}
	}

}
