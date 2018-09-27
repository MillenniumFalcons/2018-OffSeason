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
	
	//Timer stopWatch = new Timer();
	//Timer newStopWatch = new Timer();
	int run = 0;
	double prevLeftEncoder = 0, prevRightEncoder = 0;

	//Test Variables
	boolean driveEncoders, driveCurrent, driveVelocity, driveClError, navXAngle;

	class PeriodicRunnable implements Runnable
    {
        public void run()
        {
            autonomousPeriodic();
        }
    }

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
		driveEncoders = true;
		driveCurrent = false;
    	driveVelocity = false;
		driveClError = false;
		navXAngle = false;
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
		Autonomous.soloPathAuto(enc, navX);
		//Autonomous.twoPathAuto(enc, navX);
		//Autonomous.middleSwitch(enc, navX);
		//Drivetrain.turnDegrees(navX, 180, 0.5, 2);
		//Autonomous.runAuto(enc, navX);
		if(Autonomous.autoFinished)
		{
			Autonomous.autoNotifier.stop();
		}
	}
	
	@Override
	public void disabledPeriodic()
	{
		Drivetrain.setToCoast();
	}
	
	@Override
	public void teleopInit()
	{
		navX.resetAngle();
		enc.resetEncoders();
		Drivetrain.setToCoast();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		// System.out.println("NAVX READING: " + navX.yawUnClamped);
		// System.out.println("LEFT ENCODER: " + enc.leftEncoderValue);
		// System.out.println("RIGHT ENCODER: " + enc.rightEncoderValue);
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
			Drivetrain.newArcadeDrive(joy.leftJoySticky, joy.rightJoyStickx, navX.yaw);
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
