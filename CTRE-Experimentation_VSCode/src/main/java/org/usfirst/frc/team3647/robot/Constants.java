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
	public final static double lDrivekF = 0.682;
	public final static double lDrivekP = 0;
	public final static double lDrivekI = 0;
	public final static double lDrivekD = 0;
	public final static double rDrivekF = 0.62;//608928
	public final static double rDrivekP = 0;
	public final static double rDrivekI = 0;
	public final static double rDrivekD = 0;
	public final static double velocityConstant = 1700; //ticks * free RPM / (1 minute divided by 100ms) //1830
	
	public final static double deadZone = 0.05;

	//Wrist Levels
	public final static int flat = 0;
	public final static int aim = 500;
	public final static int idle = 700;
	public final static int wristPin = 0;
	public final static int wristLimitSwitch = 1;

	//Wrist PID
		//Cube
	public final static int cubePID = 1;
	public final static double cubeF = 0.1; // should be (0.75*1023)/700 = 1.096
	public final static double cubeP = 1;
	public final static double cubeI = 0;
	public final static double cubeD = 50;
		//no cube
	public final static int noCubePID = 0;
	public final static double noCubeF = .1;
	public final static double noCubeP = .45;
	public final static double noCubeI = 0;
	public final static double noCubeD = 50;

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
	public static final double lowerScale = 38000;
	public static final double scale = 45000;
	public static final double climb = 11000;

	// Elevator PID Values
		//carriage only
	public final static int carriagePID = 0;
	public final static double carriageF = 0;
	public final static double carriageP = 0.2;
	public final static double carriageI = 0;
	public final static double carriageD = 20;
		//interstage
	public final static int interstagePID = 1;
	public final static double interstageF = 0;
	public final static double interstageP = 0.4;
	public final static double interstageI = 0;
	public final static double interstageD = 50;
	
}
