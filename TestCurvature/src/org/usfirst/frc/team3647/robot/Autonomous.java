package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous 
{
	public static Timer stopWatch = new Timer();
	static double time;
	
	//Other variables for auto
	static double prevLeftEncoder, prevRightEncoder;
	static int currentState;
	static double lSSpeed, rSSpeed, speed, sum;
	static int b;
	
	static String[][] array = new String[7][6];
	
	
	public static void initialize()
	{
		stopWatch.stop();
		stopWatch.reset();
		Drivetrain.stop();
		Encoders.resetEncoders();
		Drivetrain.setToBrake();
		prevLeftEncoder = 0;
		prevRightEncoder = 0;
		currentState = 0;
		time = 0;
	}
	
	public static void testCurvature()
	{
		String character = "";
		switch(currentState)
		{
			case 0:
				if(stopWatch.get() == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
				}
				break;
			case 1:
				if(stopWatch.get() < .2)
				{
					Drivetrain.drive.curvatureDrive(0, .16, true);
				}
				else if(stopWatch.get() < .3)
				{
					Drivetrain.drive.curvatureDrive(0, .16, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[0][0] = character;
				}
				else
				{
					currentState = 2;
				}
				break;
			case 2:
				if(stopWatch.get() < .5)
				{
					Drivetrain.drive.curvatureDrive(0, .33, true);
				}
				else if(stopWatch.get() < .6)
				{
					Drivetrain.drive.curvatureDrive(0, .33, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[0][1] = character;
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				if(stopWatch.get() < .8)
				{
					Drivetrain.drive.curvatureDrive(0, .5, true);
				}
				else if(stopWatch.get() < .9)
				{
					Drivetrain.drive.curvatureDrive(0, .5, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[0][2] = character;
				}
				else
				{
					currentState = 4;
				}
				break;
			case 4:
				if(stopWatch.get() < 1.1)
				{
					Drivetrain.drive.curvatureDrive(0, .66, true);
				}
				else if(stopWatch.get() < 1.2)
				{
					Drivetrain.drive.curvatureDrive(0, .66, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[0][3] = character;
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				if(stopWatch.get() < 1.4)
				{
					Drivetrain.drive.curvatureDrive(0, .83, true);
				}
				else if(stopWatch.get() < 1.5)
				{
					Drivetrain.drive.curvatureDrive(0, .83, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[0][4] = character;
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				if(stopWatch.get() < 1.7)
				{
					Drivetrain.drive.curvatureDrive(0, 1, true);
				}
				else if(stopWatch.get() < 1.8)
				{
					Drivetrain.drive.curvatureDrive(0, 1, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[0][5] = character;
				}
				else
				{
					currentState = 7;
				}
				break;
			case 7:
				if(stopWatch.get() < 2)
				{
					Drivetrain.drive.curvatureDrive(.16, .16, true);
				}
				else if(stopWatch.get() < 2.1)
				{
					Drivetrain.drive.curvatureDrive(.16, .16, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[1][0] = character;
				}
				else
				{
					currentState = 8;
				}
				break;
			case 8:
				if(stopWatch.get() < 2.3)
				{
					Drivetrain.drive.curvatureDrive(.16, .33, true);
				}
				else if(stopWatch.get() < 2.4)
				{
					Drivetrain.drive.curvatureDrive(.16, .33, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[1][1] = character;
				}
				else
				{
					currentState = 9;
				}
				break;
			case 9:
				if(stopWatch.get() < 2.6)
				{
					Drivetrain.drive.curvatureDrive(.16, .5, true);
				}
				else if(stopWatch.get() < 2.7)
				{
					Drivetrain.drive.curvatureDrive(.16, .5, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[1][2] = character;
				}
				else
				{
					currentState = 10;
				}
				break;
			case 10:
				if(stopWatch.get() < 2.9)
				{
					Drivetrain.drive.curvatureDrive(.16, .66, true);
				}
				else if(stopWatch.get() < 3)
				{
					Drivetrain.drive.curvatureDrive(.16, .66, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[1][3] = character;
				}
				else
				{
					currentState = 11;
				}
				break;
			case 11:
				if(stopWatch.get() < 3.2)
				{
					Drivetrain.drive.curvatureDrive(.16, .83, true);
				}
				else if(stopWatch.get() < 3.3)
				{
					Drivetrain.drive.curvatureDrive(.16, .83, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[1][4] = character;
				}
				else
				{
					currentState = 12;
				}
				break;
			case 12:
				if(stopWatch.get() < 3.5)
				{
					Drivetrain.drive.curvatureDrive(.16, 1, true);
				}
				else if(stopWatch.get() < 3.6)
				{
					Drivetrain.drive.curvatureDrive(.16, 1, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[1][5] = character;
				}
				else
				{
					currentState = 13;
				}
				break;
			case 13:
				if(stopWatch.get() < 3.8)
				{
					Drivetrain.drive.curvatureDrive(.33, .16, true);
				}
				else if(stopWatch.get() < 3.9)
				{
					Drivetrain.drive.curvatureDrive(.33, .16, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[2][0] = character;
				}
				else
				{
					currentState = 14;
				}
				break;
			case 14:
				if(stopWatch.get() < 4.1)
				{
					Drivetrain.drive.curvatureDrive(.33, .33, true);
				}
				else if(stopWatch.get() < 4.2)
				{
					Drivetrain.drive.curvatureDrive(.33, .33, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[2][1] = character;
				}
				else
				{
					currentState = 15;
				}
				break;
			case 15:
				if(stopWatch.get() < 4.4)
				{
					Drivetrain.drive.curvatureDrive(.33, .5, true);
				}
				else if(stopWatch.get() < 4.5)
				{
					Drivetrain.drive.curvatureDrive(.33, .5, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[2][2] = character;
				}
				else
				{
					currentState = 16;
				}
				break;
			case 16:
				if(stopWatch.get() < 4.7)
				{
					Drivetrain.drive.curvatureDrive(.33, .66, true);
				}
				else if(stopWatch.get() < 4.8)
				{
					Drivetrain.drive.curvatureDrive(.33, .66, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[2][3] = character;
				}
				else
				{
					currentState = 17;
				}
				break;
			case 17:
				if(stopWatch.get() < 5)
				{
					Drivetrain.drive.curvatureDrive(.33, .83, true);
				}
				else if(stopWatch.get() < 5.1)
				{
					Drivetrain.drive.curvatureDrive(.33, .83, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[2][4] = character;
				}
				else
				{
					currentState = 18;
				}
				break;
			case 18:
				if(stopWatch.get() < 5.3)
				{
					Drivetrain.drive.curvatureDrive(.33, 1, true);
				}
				else if(stopWatch.get() < 5.4)
				{
					Drivetrain.drive.curvatureDrive(.33, 1, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[2][5] = character;
				}
				else
				{
					currentState = 19;
				}
				break;
			case 19:
				if(stopWatch.get() < 5.6)
				{
					Drivetrain.drive.curvatureDrive(.5, .16, true);
				}
				else if(stopWatch.get() < 5.7)
				{
					Drivetrain.drive.curvatureDrive(.5, .16, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[3][0] = character;
				}
				else
				{
					currentState = 20;
				}
				break;
			case 20:
				if(stopWatch.get() < 5.9)
				{
					Drivetrain.drive.curvatureDrive(.5, .33, true);
				}
				else if(stopWatch.get() < 6)
				{
					Drivetrain.drive.curvatureDrive(.5, .33, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[3][1] = character;
				}
				else
				{
					currentState = 21;
				}
				break;
			case 21:
				if(stopWatch.get() < 6.2)
				{
					Drivetrain.drive.curvatureDrive(.5, .5, true);
				}
				else if(stopWatch.get() < 6.3)
				{
					Drivetrain.drive.curvatureDrive(.5, .5, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[3][2] = character;
				}
				else
				{
					currentState = 22;
				}
				break;
			case 22:
				if(stopWatch.get() < 6.5)
				{
					Drivetrain.drive.curvatureDrive(.5, .66, true);
				}
				else if(stopWatch.get() < 6.6)
				{
					Drivetrain.drive.curvatureDrive(.5, .66, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[3][3] = character;
				}
				else
				{
					currentState = 23;
				}
				break;
			case 23:
				if(stopWatch.get() < 6.8)
				{
					Drivetrain.drive.curvatureDrive(.5, .83, true);
				}
				else if(stopWatch.get() < 6.9)
				{
					Drivetrain.drive.curvatureDrive(.5, .83, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[3][4] = character;
				}
				else
				{
					currentState = 24;
				}
				break;
			case 24:
				if(stopWatch.get() < 7.1)
				{
					Drivetrain.drive.curvatureDrive(.5, 1, true);
				}
				else if(stopWatch.get() < 7.2)
				{
					Drivetrain.drive.curvatureDrive(.5, 1, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[3][5] = character;
				}
				else
				{
					currentState = 25;
				}
				break;
			case 25:
				if(stopWatch.get() < 7.4)
				{
					Drivetrain.drive.curvatureDrive(.66, .16, true);
				}
				else if(stopWatch.get() < 7.5)
				{
					Drivetrain.drive.curvatureDrive(.66, .16, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[4][0] = character;
				}
				else
				{
					currentState = 26;
				}
				break;
			case 26:
				if(stopWatch.get() < 7.7)
				{
					Drivetrain.drive.curvatureDrive(.66, .33, true);
				}
				else if(stopWatch.get() < 7.8)
				{
					Drivetrain.drive.curvatureDrive(.66, .33, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[4][1] = character;
				}
				else
				{
					currentState = 27;
				}
				break;
			case 27:
				if(stopWatch.get() < 8)
				{
					Drivetrain.drive.curvatureDrive(.66, .5, true);
				}
				else if(stopWatch.get() < 8.1)
				{
					Drivetrain.drive.curvatureDrive(.66, .5, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[4][2] = character;
				}
				else
				{
					currentState = 28;
				}
				break;
			case 28:
				if(stopWatch.get() < 8.3)
				{
					Drivetrain.drive.curvatureDrive(.66, .66, true);
				}
				else if(stopWatch.get() < 8.4)
				{
					Drivetrain.drive.curvatureDrive(.66, .66, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[4][3] = character;
				}
				else
				{
					currentState = 29;
				}
				break;
			case 29:
				if(stopWatch.get() < 8.6)
				{
					Drivetrain.drive.curvatureDrive(.66, .83, true);
				}
				else if(stopWatch.get() < 8.7)
				{
					Drivetrain.drive.curvatureDrive(.66, .83, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[4][4] = character;
				}
				else
				{
					currentState = 30;
				}
				break;
			case 30:
				if(stopWatch.get() < 8.9)
				{
					Drivetrain.drive.curvatureDrive(.66, 1, true);
				}
				else if(stopWatch.get() < 9)
				{
					Drivetrain.drive.curvatureDrive(.66, 1, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[4][5] = character;
				}
				else
				{
					currentState = 31;
				}
				break;
			case 31:
				if(stopWatch.get() < 9.2)
				{
					Drivetrain.drive.curvatureDrive(.83, .16, true);
				}
				else if(stopWatch.get() < 9.3)
				{
					Drivetrain.drive.curvatureDrive(.83, .16, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[5][0] = character;
				}
				else
				{
					currentState = 32;
				}
				break;
			case 32:
				if(stopWatch.get() < 9.5)
				{
					Drivetrain.drive.curvatureDrive(.83, .33, true);
				}
				else if(stopWatch.get() < 9.6)
				{
					Drivetrain.drive.curvatureDrive(.83, .33, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[5][1] = character;
				}
				else
				{
					currentState = 33;
				}
				break;
			case 33:
				if(stopWatch.get() < 9.8)
				{
					Drivetrain.drive.curvatureDrive(.83, .5, true);
				}
				else if(stopWatch.get() < 9.9)
				{
					Drivetrain.drive.curvatureDrive(.83, .5, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[5][2] = character;
				}
				else
				{
					currentState = 34;
				}
				break;
			case 34:
				if(stopWatch.get() < 10.1)
				{
					Drivetrain.drive.curvatureDrive(.83, .66, true);
				}
				else if(stopWatch.get() < 10.2)
				{
					Drivetrain.drive.curvatureDrive(.83, .66, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[5][3] = character;
				}
				else
				{
					currentState = 35;
				}
				break;
			case 35:
				if(stopWatch.get() < 10.4)
				{
					Drivetrain.drive.curvatureDrive(.83, .83, true);
				}
				else if(stopWatch.get() < 10.5)
				{
					Drivetrain.drive.curvatureDrive(.83, .83, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[5][4] = character;
				}
				else
				{
					currentState = 36;
				}
				break;
			case 36:
				if(stopWatch.get() < 10.7)
				{
					Drivetrain.drive.curvatureDrive(.83, 1, true);
				}
				else if(stopWatch.get() < 10.8)
				{
					Drivetrain.drive.curvatureDrive(.83, 1, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[5][5] = character;
				}
				else
				{
					currentState = 37;
				}
				break;
			case 37:
				if(stopWatch.get() < 11)
				{
					Drivetrain.drive.curvatureDrive(1, .16, true);
				}
				else if(stopWatch.get() < 11.1)
				{
					Drivetrain.drive.curvatureDrive(1, .16, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[6][0] = character;
				}
				else
				{
					currentState = 38;
				}
				break;
			case 38:
				if(stopWatch.get() < 11.3)
				{
					Drivetrain.drive.curvatureDrive(1, .33, true);
				}
				else if(stopWatch.get() < 11.4)
				{
					Drivetrain.drive.curvatureDrive(1, .33, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[6][1] = character;
				}
				else
				{
					currentState = 39;
				}
				break;
			case 39:
				if(stopWatch.get() < 11.6)
				{
					Drivetrain.drive.curvatureDrive(1, .5, true);
				}
				else if(stopWatch.get() < 11.7)
				{
					Drivetrain.drive.curvatureDrive(1, .5, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[6][2] = character;
				}
				else
				{
					currentState = 40;
				}
				break;
			case 40:
				if(stopWatch.get() < 11.9)
				{
					Drivetrain.drive.curvatureDrive(1, .66, true);
				}
				else if(stopWatch.get() < 12)
				{
					Drivetrain.drive.curvatureDrive(1, .66, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[6][3] = character;
				}
				else
				{
					currentState = 41;
				}
				break;
			case 41:
				if(stopWatch.get() < 12.2)
				{
					Drivetrain.drive.curvatureDrive(1, .83, true);
				}
				else if(stopWatch.get() < 12.3)
				{
					Drivetrain.drive.curvatureDrive(1, .83, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[6][4] = character;
				}
				else
				{
					currentState = 42;
				}
				break;
			case 42:
				if(stopWatch.get() < 12.5)
				{
					Drivetrain.drive.curvatureDrive(1, 1, true);
				}
				else if(stopWatch.get() < 12.6)
				{
					Drivetrain.drive.curvatureDrive(1, 1, true);
					character += "Left Speed: ";
					character += Double.toString(Drivetrain.leftSRX.get());
					character += "; ";
					character += "Right Speed: ";
					character += Double.toString(Drivetrain.rightSRX.get());
					character += "; ";
					array[6][5] = character;
				}
				else
				{
					currentState = 43;
				}
				break;
			case 44:
				stopWatch.stop();
				for(int i = 0; i < array.length; i++)
				{
					for(int j = 0; j < array[i].length; j++)
					{
						System.out.print(array[i][j] + " ");
					}
					System.out.println();
				}
				currentState = 45;
				break;
			case 45:
				Drivetrain.stop();
				break;
		}
	}
}
