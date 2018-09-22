package frc.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.Timer;
import frc.team3647Subsystems.*;
import frc.team3647Autonomous.*;
import frc.team3647Inputs.*;



public class Robot extends IterativeRobot 
{
	//Objects
	Encoders enc;
	Joysticks joy;
	NavX navX;  
	MotorSafety safety;
	MotorSafetyHelper safetyChecker;
	CameraServer server;
	
	Timer stopWatch = new Timer();
	Timer newStopWatch = new Timer();
	int run = 0;
	double prevLeftEncoder = 0, prevRightEncoder = 0;

	//Test Variables
	boolean driveEncoders, driveCurrent, driveVelocity, driveClError, navXAngle;

	@Override
	public void robotInit() 
	{
		try
		{
			enc = new Encoders();
			navX = new NavX();
			joy = new Joysticks();
			Drivetrain.drivetrainInitialization();
			setTests();
			
		}
		catch(Throwable t)
		{
			throw t;
		}
	}
	
	public void setTests()
	{
		driveEncoders = false;
		driveCurrent = false;
    	driveVelocity = false;
		driveClError = false;
		navXAngle = true;
	}
	
	@Override
	public void autonomousInit() 
	{
		Autonomous.initialization(enc, navX);
		Autonomous.switchSide = "Right";
		
		navX.resetAngle();
	}

	@Override
	public void autonomousPeriodic() 
	{
		navX.setAngle();
		//Autonomous.soloPathAuto(enc, navX);
		//Autonomous.twoPathAuto(enc, navX);
		Autonomous.middleSwitch(enc, navX);
		//Drivetrain.turnDegrees(navX, 180, 0.5, 2);
		//Autonomous.runAuto(enc, navX);
	}
	
	@Override
	public void disabledPeriodic()
	{
		Drivetrain.setToBrake();
	}
	
	@Override
	public void teleopInit()
	{
		navX.resetAngle();
		Drivetrain.setToCoast();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		try 
		{
			navX.setAngle();
			updateJoysticks();
			runDrivetrain();
			runTests();
			if(joy.buttonA)
			{
				enc.resetEncoders();
			}
		}
		catch(Throwable t)
		{
			throw t;
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
	
	public void updateJoysticks()
	{
		joy.setMainContollerValues();
		joy.setCoDriverContollerValues();
	}

	public void runDrivetrain()
	{
		enc.setEncoderValues();
		if(joy.leftBumper)
		{
			//Drivetrain.arcadeDrive(enc.leftEncoderValue, enc.rightEncoderValue, joy.leftJoySticky * .6, joy.rightJoyStickx * .6);
		}
		else
		{
			//Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky, joy.rightJoyStickx);
			//Drivetrain.FRCarcadedrive(joy.leftJoySticky, joy.rightJoyStickx);
			//Drivetrain.runMEATDrivetrain(joy.leftJoySticky, joy.rightJoyStickx);
			Drivetrain.yeetDrive(joy.leftJoySticky, joy.rightJoyStickx);
		}
	}
	
	public void runMotorSafety()
	{
		safetyChecker.setSafetyEnabled(false);
	}
	
	public void runTests()
	{
		if(driveEncoders)
		{
			enc.testEncoders();
    	}
		if(driveCurrent)
		{
			Drivetrain.testDrivetrainCurrent();
    	}
    	if(driveVelocity)
    	{
    		enc.testEncoderVelocity();
    	}
    	if(driveClError)
    	{
    		enc.testEncoderCLError();
		}
		if(navXAngle)
		{
			navX.testAngle();
		}
	}
}
