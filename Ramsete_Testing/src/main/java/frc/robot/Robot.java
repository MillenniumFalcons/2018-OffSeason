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
import frc.team3647Subsystems.*;
import frc.team3647Autonomous.*;
import frc.team3647Autonomous.team3647Commands.TestCommandGroup;
import frc.team3647Utility.*;



public class Robot extends IterativeRobot 
{
  //Objects
	Command auto;
	Joysticks joy;
	MotorSafety safety;
	MotorSafetyHelper safetyChecker;
	CameraServer server;
	
	//Subsystems
	public static final Drivetrain mDrivetrain = new Drivetrain();

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
			joy = new Joysticks();
			mDrivetrain.drivetrainInitialization();
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
		navXAngle = false;
	}
	
	@Override
	public void autonomousInit() 
	{
		auto = new TestCommandGroup();
	}

	@Override
	public void autonomousPeriodic() 
	{
		Scheduler.getInstance().run();
	}
	
	@Override
	public void disabledPeriodic()
	{
		mDrivetrain.setToCoast();
	}
	
	@Override
	public void teleopInit()
	{
		mDrivetrain.resetAngle();
		mDrivetrain.setToCoast();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		try 
		{
			updateJoysticks();
			runDrivetrain();
			runTests();
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
		if(joy.leftBumper)
		{
			mDrivetrain.newArcadeDrive(joy.leftJoySticky * 0.5, joy.rightJoyStickx * 0.5, mDrivetrain.getYaw());
		}
  	else
		{
			//Drivetrain.FRCarcadedrive(joy.leftJoySticky, joy.rightJoyStickx);
			mDrivetrain.newArcadeDrive(joy.leftJoySticky, joy.rightJoyStickx, mDrivetrain.getYaw());
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
			mDrivetrain.testEncoderPosition();
    }
		if(driveCurrent)
		{
		  mDrivetrain.testDrivetrainCurrent();
    }
    if(driveVelocity)
    {
    	mDrivetrain.testEncoderVelocity();
    }
		if(navXAngle)
		{
			mDrivetrain.testAngle();
		}
	}
}
