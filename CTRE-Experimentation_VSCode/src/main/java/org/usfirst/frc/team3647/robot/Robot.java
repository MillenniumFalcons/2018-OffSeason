package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
	Joysticks joy;
	
	boolean driveEncoderPosition, driveEncoderVelocity, driveEncoderVelocityErrorR, driveEncoderVelocityErrorL, wristEncoderPosition, wristMotorCurrent, wristBannerSensor, wristLimitSwitch, intakeBannerSensor;
	
	public void setTests(){
		driveEncoderPosition = false;
		driveEncoderVelocity = false;
		driveEncoderVelocityErrorR = false;
		driveEncoderVelocityErrorL = false;
		wristEncoderPosition = false;
		wristMotorCurrent = false;
		wristBannerSensor = false;
		intakeBannerSensor = false;
		wristLimitSwitch = false;
	}
	
	@Override
	public void robotInit() 
	{
		joy = new Joysticks();
		Drivetrain.driveTrainInitialization();
	}

	@Override
	public void autonomousInit() 
	{
		
	}

	
	@Override
	public void autonomousPeriodic() 
	{
		
	}

	@Override
	public void teleopInit()
	{
		Drivetrain.resetEncoders();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		updateJoysticks();
		Drivetrain.setEncoderValues();
		Drivetrain.runYeetDrive(joy.leftJoySticky, joy.rightJoyStickx);
		//Drivetrain.setSpeed(1, 0);
		runWrist();
		runTests();
	}

	@Override
	public void testPeriodic() 
	{
		
	}

	public void updateJoysticks()
	{
		joy.setMainContollerValues();
		joy.setCoDriverContollerValues();
		joy.setDPadValues();
	}

	public void runWrist()
	{
		Wrist.setWristEncoder();
		Wrist.setWristButtons(joy.dPadDown,joy.dPadSide,joy.dPadUp);
		Wrist.setManualWristOverride(joy.leftJoySticky * 0.6);
		Wrist.runWrist();
	}

	public void runTests(){
		if(driveEncoderPosition)
		{
			Drivetrain.testEncodersPosition();
		}
		else if(driveEncoderVelocity)
		{
			Drivetrain.printVelocityError(2);
		}
		else if(driveEncoderVelocityErrorR)
		{
			Drivetrain.printVelocityError(0);
		}
		else if(driveEncoderVelocityErrorL)
		{
			Drivetrain.printVelocityError(1);
		}
		else if(wristEncoderPosition)
		{
			Wrist.testWristEncoder();
		}
		else if(wristMotorCurrent)
		{
			Wrist.testWristCurrent();
		}
		else if(intakeBannerSensor)
		{
			Wrist.testBannerSensor();
		}
		else if(wristLimitSwitch)
		{
			Wrist.testLimitSwitch();
		}
	}
}


