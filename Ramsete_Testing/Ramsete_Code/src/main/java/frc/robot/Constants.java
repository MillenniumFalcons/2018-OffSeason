package frc.robot;

public interface Constants
{
	//Drive Motor Pins
	public final static int kLeftMaster = 57;
	public final static int kRightMaster = 54;
	public final static int kRightSlave1 = 58;
	public final static int kRightSlave2 = 59;
	public final static int kLeftSlave1 = 52;
	public final static int kLeftSlave2 = 53;

	//Drive PID Constants
	public final static int kTimeoutMs = 10;  //Universal Constant
	public final static int kDrivePID = 0;
	public final static double kLDrivekF = 0.65; //0.7
	public final static double kLDrivekP = .68; 	 //.68
	public final static double kLDrivekI = 0;
	public final static double kLDrivekD = 0;
	public final static double kRDrivekF = 0.64; //.66
	public final static double kRDrivekP = .68; 	 //.68
	public final static double kRDrivekI = 0;
	public final static double kRDrivekD = 0;
	public final static double kVelocityConstant = 1550;

	//Robot Constants
	public final static double kWheelDiameter = 4.8;
	public final static double kWheelBase = 27; //25.2
    public final static double kMaxVelocity = 14.498; 
	public final static int kEncoderTicks = 1440;
	
	//Ramsete Constants
	public final static double kBeta = 1; // b>0 (corrrection)
	public final static double kZeta = .38; // 1>z>0 (dampening)
	
	
}
