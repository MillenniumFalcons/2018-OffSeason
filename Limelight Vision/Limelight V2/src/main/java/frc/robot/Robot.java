package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;
import frc.team3647inputs.Joysticks;
import frc.team3647inputs.Limelight;
import frc.team3647inputs.NavX;
import frc.team3647subsystems.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends IterativeRobot
{
	Joysticks stick;
	Limelight one;


	@Override
	public void robotInit()
	{
		one = new Limelight();
		stick = new Joysticks();
		Drivetrain.drivetrainInitialization(false);
	}

	int input = 1;

	@Override
	public void autonomousInit()
	{
		Drivetrain.FRCarcadedrive(0.5, 0.5);
		Drivetrain.setToCoast();
		input = 1;
	}

	
	@Override
	public void autonomousPeriodic()
	{
		one.updateLimelight();
		// one.follow(.3, 0, 1.1, .1, 0.5, 6); //drvivebase bot
		// one.center(.3, 0, 1.1 , .075);		//drivebase bot
		one.follow(.3, 0, 3, .1, 0.5, 5.5); //2018 comp bot
		// one.center(.3, 0, 4, .035);		//2018 comp bot
	}


	// This is the function that runs during Tele-Operated Period
	public void teleopPeriodic()
	{
		stick.setMainContollerValues();
		Drivetrain.FRCarcadedrive(stick.rightJoyStickx, stick.leftJoySticky);

	}

	@Override
	public void testPeriodic()
	{

	}
}
