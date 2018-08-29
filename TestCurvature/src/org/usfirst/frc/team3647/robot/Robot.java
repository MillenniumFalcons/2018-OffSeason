package org.usfirst.frc.team3647.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.IterativeRobot;


public class Robot extends IterativeRobot {

	@Override
	public void robotInit() 
	{
		
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
		Arrays.fill(Drivetrain.array, "0");
	}
	
	@Override
	public void teleopPeriodic() 
	{
		
	}

	@Override
	public void testPeriodic() 
	{
		
	}
}

