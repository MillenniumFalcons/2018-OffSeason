package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
	Joysticks joy;
	
	boolean testEncoders = false;
	boolean testError = false;
	
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
		joy.setMainContollerValues();
		Drivetrain.setEncoderValues();
		Drivetrain.runDrivetrain(joy.leftJoySticky, joy.rightJoyStickx);
		if(testEncoders)
		{
			Drivetrain.testEncoders();
		}
		if(testError)
		{
			Drivetrain.printError();
		}
	}

	@Override
	public void testPeriodic() 
	{
		
	}
}


