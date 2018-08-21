package org.usfirst.frc.team3647.robot;

public class Constants 
{
	public final static int leftMaster = 57;
	public final static int rightMaster = 54;
	public final static int rightSlave1 = 58;
	public final static int rightSlave2 = 59;
	public final static int leftSlave1 = 52;
	public final static int leftSlave2 = 53;
	
	public final static int kTimeoutMs = 10;
	public final static int drivePID = 0;
	public final static boolean kSensorPhase = true;
	public final static boolean kMotorInvert = false;
	public final static double lDrivekF = 0.0274;
	public final static double lDrivekP = 0.113333;
	public final static double lDrivekI = 0;
	public final static double lDrivekD = 0;
	public final static double rDrivekF = 0.0274;
	public final static double rDrivekP = 0.113333;
	public final static double rDrivekI = 0;
	public final static double rDrivekD = 0;
	public final static double velocityConstant = 1024 * 759 / 600; //ticks * free RPM / (1 minute divided by 100ms)
	
	public final static double deadZone = 0.05;
	
}
