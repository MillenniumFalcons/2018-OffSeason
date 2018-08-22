package org.usfirst.frc.team3647.robot;

public class Constants 
{
	//Drivemotor ids
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
	//Drive PID Constants
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

	//Wrist Levels
	public final static int flat = 0;
	public final static int aim = 350;
	public final static int idle = 720;
	public final static int wristPin = 0;
	public final static int wristLimitSwitch = 1;

	//Wrist PID
		//noCube
	public final static int noCubePID = 0;
	public final static int noCubeF = 0;
	public final static int noCubeP = 0;
	public final static int noCubeI = 0;
	public final static int noCubeD = 0;
		//cube
	public final static int cubePID = 1;
	public final static int cubeP = 0;
	public final static int cubeF = 0;
	public final static int cubeI = 0;
	public final static int cubeD = 0;

	//Wrist level Speeds

		//flat
	public final static double moveToFlat = -0.1;
	public final static double aimMoveToFlat = -0.3;
	public final static double idleMoveToFlat = -0.25;
		//aim
	public final static double idleMoveToAim = -0.2;
	public final static double moveToAim = 0.3;
	public final static double maintainAimWithCube = 0.17;
	public final static double maintainAim = 0.1;
	public final static double adjustAimUpWithCube = 0.2;
	public final static double adjustAimUp = 0.15;
	public final static double adjustAimDownWithCube = 0;
	public final static double adjustAimDown = 0;
		//idle
	public final static double moveToIdle = 0.3;
	public final static double maintainIdleWithcube = 0.15;
	public final static double maintainIdle = 0;
	public final static double adjustIdleUpWithCube = 0.2;
	public final static double adjustidleUp = 0.15;
	public final static double adjustIdleDownWithCube = -0.15;
	public final static double adjustIdleDown = -0.1;

	//intake
	public final static int intakeBannerSensor = 8;
	
}
