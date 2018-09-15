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
	public final static double velocityConstant = 1872;

	//Pathfinder Constants
	public final static double wheelDiameter = 0.4166;
		//Used for waypoint generation method
	public final static double wheelBase = 2.4;
	public final static double maxVelocity = 14; // also used for csv file method
	public final static double maxAcceleration = 0;
	public final static double maxJerk = 0;
	public final static double MPTimeStep = 0.01;
		//PID Values
	public final static double lPFkP = 0.013; //P Gain
	public final static double lPFkI = 0; //I Gain
	public final static double lPFkD = 0; //D Gain
	public final static double lPFkV = 1 / maxVelocity ; //Velocity Ratio (1/max velocity) -- changes m/s to -1 to 1 scale
	public final static double lPFkA = 0; //Acceleration Gain
	public final static double rPFkP = 0.013; //P Gain
	public final static double rPFkI = 0; //I Gain
	public final static double rPFkD = 0; //D Gain
	public final static double rPFkV = 1 / maxVelocity ; //Velocity Ratio (1/max velocity) -- changes m/s to -1 to 1 scale
	public final static double rPFkA = 0; //Acceleration Gain
}
