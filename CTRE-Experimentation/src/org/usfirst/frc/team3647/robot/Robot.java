package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
	Joysticks joy;
	
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
		
	}
	
	@Override
	public void teleopPeriodic() 
	{
		joy.setMainContollerValues();
		Drivetrain.runDrivetrain(joy.leftJoySticky, joy.rightJoyStickx);
	}

	@Override
	public void testPeriodic() 
	{
		
	}
}


