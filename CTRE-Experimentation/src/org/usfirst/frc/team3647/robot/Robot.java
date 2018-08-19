package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
	Joysticks joy;
	
	boolean driveEncoderPosition, driveEncoderVelocity, driveEncoderVelocityErrorR, driveEncoderVelocityErrorL;
	
	public void setTests(){
		driveEncoderPosition = false;
		driveEncoderVelocity = false;
		driveEncoderVelocityErrorR = false;
		driveEncoderVelocityErrorL = false;
		
	}
	
	@Override
	public void robotInit() 
	{
		joy = new Joysticks();
		Drivetrain.driveTrainInitialization();
		Drivetrain.leftSRX.setNeutralMode(NeutralMode.Coast);
		Drivetrain.rightSRX.setNeutralMode(NeutralMode.Coast);
		Drivetrain.leftSPX1.setNeutralMode(NeutralMode.Coast);
		Drivetrain.leftSPX2.setNeutralMode(NeutralMode.Coast);
		Drivetrain.rightSPX1.setNeutralMode(NeutralMode.Coast);
		Drivetrain.rightSPX2.setNeutralMode(NeutralMode.Coast);
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
		joy.setMainContollerValues();
		Drivetrain.setEncoderValues();
		Drivetrain.runYeetDrive(joy.leftJoySticky, joy.rightJoyStickx);
		//Drivetrain.setSpeed(1, 0);
		runTests();
	}

	@Override
	public void testPeriodic() 
	{
		
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
	}
}


