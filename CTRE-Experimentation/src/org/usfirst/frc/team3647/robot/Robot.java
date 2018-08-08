package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
	Joysticks joy;
	
	boolean testEncoders = true;
	boolean testError = false;
	
	@Override
	public void robotInit() 
	{
		joy = new Joysticks();
		Drivetrain.driveTrainInitialization();
		Drivetrain.leftSRX.setNeutralMode(NeutralMode.Brake);
		Drivetrain.rightSRX.setNeutralMode(NeutralMode.Brake);
		Drivetrain.leftSPX1.setNeutralMode(NeutralMode.Brake);
		Drivetrain.leftSPX2.setNeutralMode(NeutralMode.Brake);
		Drivetrain.rightSPX1.setNeutralMode(NeutralMode.Brake);
		Drivetrain.rightSPX2.setNeutralMode(NeutralMode.Brake);
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
		System.out.println(joy.lol.getPOV() + "; not lol");
		System.out.println(joy.lol.getPOV(1) + "; lol");
		//Drivetrain.setEncoderValues();
		// Drivetrain.runDrivetrain(joy.leftJoySticky, joy.rightJoyStickx);
		// if(testEncoders)
		// {
		// 	Drivetrain.testEncoders();
		// }
		// if(testError)
		// {
		// 	Drivetrain.printError();
		// }
	}

	@Override
	public void testPeriodic() 
	{
		
	}
}


