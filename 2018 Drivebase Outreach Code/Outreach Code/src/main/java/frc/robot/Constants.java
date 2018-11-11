package frc.robot;

public class Constants 
{
	//Drive Motor Pins
	public final static int leftMaster = 57;
	public final static int rightMaster = 54;
	public final static int rightSlave1 = 58;
	public final static int rightSlave2 = 59;
	public final static int leftSlave1 = 52;
	public final static int leftSlave2 = 53;

	//Drive PID Constants
	public final static int kTimeoutMs = 10;  //Universal Constant
	public final static int drivePID = 0;
	public final static double lDrivekF = 0.65;
	public final static double lDrivekP = 1;//1.75
	public final static double lDrivekI = 0;
	public final static double lDrivekD = 0;
	public final static double rDrivekF = 0.64;
	public final static double rDrivekP = 1;//1.75
	public final static double rDrivekI = 0;
	public final static double rDrivekD = 0;
	public final static double velocityConstant = 1550;

	//PracBot
	public final static double lDrivekF1 = 0.66;
	public final static double lDrivekP1 = 1;
	public final static double lDrivekI1 = 0;
	public final static double lDrivekD1 = 0;
	public final static double rDrivekF1 = 0.67;
	public final static double rDrivekP1 = 1;
	public final static double rDrivekI1 = 0;
	public final static double rDrivekD1 = 0;
	public final static double velocityConstant1 = 1550;

	public final static double driveElevatorSpeedModifier = 0.6;
}
