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
	public final static double lDrivekF = 0.577966;
	public final static double lDrivekP = 0;
	public final static double lDrivekI = 0;
	public final static double lDrivekD = 0;
	public final static double rDrivekF = 0.571189;
	public final static double rDrivekP = 0;
	public final static double rDrivekI = 0;
	public final static double rDrivekD = 0;
	public final static double velocityConstant = 1770; //ticks * free RPM / (1 minute divided by 100ms) //1830
	
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

	//intake
	public final static int intakeBannerSensor = 8;

	//Elevator Pins
	public final static int leftGearboxSRX = 52;
	public final static int rightGearboxSRX = 62;
	public final static int leftGearboxSPX = 54;
	public final static int rightGearboxSPX = 57;
	public final static int elevatorBannerSensor = 9;

	//Elevator Position
	public static final double bottom = 0;
	public static final double sWitch = 13000;
	public static final double lowerScale = 36300;
	public static final double scale = 41500;
	public static final double climb = 11000;

	// Elevator PID Values
		//carriage only
	public final static int carriagePID = 0;
	public final static int carriageF = 0;
	public final static int carriageP = 0;
	public final static int carriageI = 0;
	public final static int carriageD = 0;
		//interstage
	public final static int interstagePID = 1;
	public final static int interstageP = 0;
	public final static int interstageF = 0;
	public final static int interstageI = 0;
	public final static int interstageD = 0;
	
}
