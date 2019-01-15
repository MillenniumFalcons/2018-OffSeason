package frc.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3647Subsystems.*;
import frc.team3647Autonomous.*;
import frc.team3647Autonomous.AutoSelector;
import frc.team3647Autonomous.AutoSelector.AutoMode;
import frc.team3647Autonomous.AutoSelector.StartPosition;
import frc.team3647Utility.*;


public class Robot extends IterativeRobot 
{
  //Objects
	Command mAutonomous;								//Create a command for starting Autonomous behavior
	SendableChooser<AutoMode> mAutoModeChooser;			//Create object to send AutoMode data to SmartDashboard
	SendableChooser<StartPosition> mStartPosChooser;	//Create object to send Start Position data to SmartDashboard
	Joysticks joy;										//Create Joystick Object: In order to use xBox controller for robot movement
	MotorSafety safety;									//Create MotorSafety Object: This shuts off motors when their outputs aren't updated often enough.
	MotorSafetyHelper safetyChecker;					//Create MotorSafetyHelped Object: This has the code to actually do time and call the motors to stop.
	CameraServer server;								//Create CameraServer Object: To stream video from USB Video Camera on robot to driverstation
	Odometry odo;										//Create Odometry Object: This is the code for running Auto Paths
	
	//Subsystems
	public static final Drivetrain mDrivetrain = new Drivetrain(); //Creates a Drivetrain Object (that cannot change): For robot drivetrain movement

	//Test Variables
	boolean driveEncoders, driveCurrent, driveVelocity, navXAngle;

	boolean lastAuto; //Boolean for checking if robot Auto is complete
	
	@Override
	public void robotInit() 
	{
		try
		{
			joy = new Joysticks(); 						//Initialize Joysticks Object
			odo = new Odometry(); 						//Initialize Odometry Object (see Odometry.java)
			mDrivetrain.drivetrainInitialization(); 	//Initialize Drivetrain Object
			setTests(); 								//Method used to run tests on sensors on the robot
			lastAuto = false; 							//lastauto determines the coasting of motors

			mAutoModeChooser = new SendableChooser<>(); 								//Create a SendableChooser to put in dashboard for choosing auto
			mAutoModeChooser.addDefault("TEST_PATH", AutoMode.TEST_PATH);				//Default object set to, from AutoSelector.java, AutoMode Enum: "TEST_PATH" 
			mAutoModeChooser.addObject("OPEN_LOOP_DRIVE", AutoMode.OPEN_LOOP_DRIVE);	//Adding another object from AutoSelector.java, AutoMode Enum: "OPEN_LOOP_DRIVE"
			SmartDashboard.putData("Auto Mode", mAutoModeChooser);						//Put the data from mA.M.C. in the SmartDashboard under "Auto Mode"
			
			mStartPosChooser = new SendableChooser<>();						//Create a SendableChooser to put in dashboard for choosing starting position of robot
			mStartPosChooser.addDefault("Right", StartPosition.RIGHT);		//Default object set to, from AutoSelector.java, StartPosition Enum: "RIGHT" 
			mStartPosChooser.addObject("Middle", StartPosition.MIDDLE);		//Adding another object from AutoSelector.java, StartPosition Enum: "MIDDLE"
			mStartPosChooser.addObject("Left", StartPosition.LEFT);			//Adding another object from AutoSelector.java, StartPosition Enum: "LEFT"
			SmartDashboard.putData("Start Position", mStartPosChooser);		//Put the data from mS.P.C. in the SmartDashboard under "Start Position"
		}
		catch(Throwable t)
		{
			throw t;	//Catch any errors or exceptions
		}
	}
	
	public void setTests()
	{
		driveEncoders = false; 	//test for encoder values
		driveCurrent = false; 	//test for current
    	driveVelocity = false; 	//test for velocity
		navXAngle = false;		//test for gyro angle
	}
	
	@Override
	public void autonomousInit() 
	{
		mAutonomous = new AutoSelector		//Create new AutoSelector Object to select auto behavior for the robot to run
		(
			mStartPosChooser.getSelected(), //Gets robot start position from SendableChooser on Dashboard
			mAutoModeChooser.getSelected()	//Gets robot Auto Mode from SendableChooser on Dashboard
		);
		mAutonomous.start();
	}

	@Override
	public void autonomousPeriodic() 
	{
		Scheduler.getInstance().run();	//Runs the method once
		lastAuto = true;				//Sets lastAuto to true, thus setting brakes to coast after 
	}
	
	@Override
	public void disabledPeriodic()
	{
		if(lastAuto)
		{
			mDrivetrain.setToBrake();	//Set robot motors to break if lastAuto is True
		}
		else
		{
			mDrivetrain.setToCoast();	//Set robot motors to coast if lastAuto is not True
		}
	}
	
	@Override
	public void teleopInit()
	{
		mDrivetrain.resetAngle();		//Resets Gyro Yaw Angle to zero
		mDrivetrain.setToCoast();		//Sets drivetrain motors to coast
		odo.odometryInit(false);		//Odometry Object Initialization Method, see Odometry.java
	}
	
	@Override
	public void teleopPeriodic() 
	{
		try 
		{
			updateJoysticks();			//Updates Joysticks mappings and actions periodically
			runDrivetrain();			//Runs Drivetrain Method
			runTests();					//Runs robot sensor tests, if any to run
			lastAuto = false;			//Sets lastAuto to false, thus setting motors to coast mode
			odo.printPosition();		//Print position in odometry terms
			if(joy.buttonA)
			{
				odo.setOdometry(0, 0, 0);		//Resets Odometry Values
				mDrivetrain.resetEncoders();	//Reset Encoder Values to zero
				mDrivetrain.resetAngle();		//Resets Gyro Yaw Angle to zero
			}
		}
		catch(Throwable t)
		{
			throw t;	//Catch any errors or exceptions
		}
	}

	@Override
	public void testInit()
	{

	}
	
	@Override
	public void testPeriodic() 
	{

	}
	
	public void updateJoysticks() //This method, along with setting controls, also updates controller actions
	{
		joy.setMainContollerValues();		//Set controller mapping for Joystick Input 0 in Dashboard - Main Driver
		joy.setCoDriverContollerValues();	//Set controller mapping for Joystick Input 1 in Dashboard - Co-Driver
	}

	public void runDrivetrain() 			//Method to set drivetrain speeds based on leftBumper toggle
	{
		if(joy.leftBumper)
			mDrivetrain.newArcadeDrive(joy.leftJoySticky * 0.5, joy.rightJoyStickx * 0.5, mDrivetrain.getYaw());	//move 0.5 speed if leftBumper is true

		else
			mDrivetrain.newArcadeDrive(joy.leftJoySticky, joy.rightJoyStickx, mDrivetrain.getYaw());				//move full speed if leftBumper is not true
			
	}
	
	public void runMotorSafety()
	{
		safetyChecker.setSafetyEnabled(false);	//Motor Safety Disabled
	}
	
	public void runTests()	//method for sensor tests
	{
		if(driveEncoders)
			mDrivetrain.testEncoderPosition();		//Test Encoder Values
		
		if(driveCurrent)
		  	mDrivetrain.testDrivetrainCurrent();	//Test Electrical Current Values
		
    	if(driveVelocity)
			mDrivetrain.testEncoderVelocity();		//Test Velocity
		
		if(navXAngle)
			mDrivetrain.testAngle();				//Test Gyro Angle
	}
}
