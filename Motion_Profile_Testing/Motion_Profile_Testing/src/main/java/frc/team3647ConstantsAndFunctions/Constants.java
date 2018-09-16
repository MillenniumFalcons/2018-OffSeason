package frc.team3647ConstantsAndFunctions;

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
	public final static double lDrivekF = 0.7;
	public final static double lDrivekP = .68;
	public final static double lDrivekI = 0;
	public final static double lDrivekD = 0;
	public final static double rDrivekF = 0.66;
	public final static double rDrivekP = .68;
	public final static double rDrivekI = 0;
	public final static double rDrivekD = 0;
	public final static double velocityConstant = 1550;

	//Pathfinder Constants
	public final static double wheelDiameter = .378;//.416666;
		//Used for waypoint generation method
	public final static double wheelBase = 2.1;
	public final static double maxVelocity = 15.498; // also used for csv file method
	public final static double maxAcceleration = 0;
	public final static double maxJerk = 0;
	public final static double MPTimeStep = 0.02;
		//PID Values
	public final static double lPFkP = 0.3048;//.05; //0.19; //P Gain
	public final static double lPFkI = 0; //I Gain (not used)
	public final static double lPFkD = 0; //D Gain
	public final static double lPFkV = 1 / maxVelocity ; //Feed Forward (1/max velocity)
	public final static double lPFkA = 0; //Acceleration Gain
	public final static double rPFkP = 0.3048;//.05; //0.19; //P Gain
	public final static double rPFkI = 0; //I Gain (not used)
	public final static double rPFkD = 0; //D Gain
	public final static double rPFkV = 1 / maxVelocity ; //Feed Forward(1/max velocity)
	public final static double rPFkA = 0; //Acceleration Gain

	//Auto Constants
	public final static double shootCubeTime = 0.5;
	public final static double elevatorTimeout = 1;
	public final static double autoIntakeSpeed = 1;
	public final static double autoShootSpeed = -1;
	public final static double scaleAutoElevatorUpDistance = 19800; //18ft * 12in * 1440 / (5in * 3.14)
}
