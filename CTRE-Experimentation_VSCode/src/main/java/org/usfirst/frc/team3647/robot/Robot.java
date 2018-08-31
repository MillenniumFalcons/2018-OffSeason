package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot 
{
	Joysticks joy;
	
	boolean elevatorEncoder, driveEncoderPosition, driveEncoderVelocity, driveEncoderVelocityErrorR, driveEncoderVelocityErrorL, driveVelocity, wristEncoderPosition, wristMotorCurrent, wristBannerSensor, wristLimitSwitch, intakeBannerSensor;
	
	public void setTests(){
		driveEncoderPosition = false;
		driveEncoderVelocity = false;
		driveEncoderVelocityErrorR = false;
		driveEncoderVelocityErrorL = false;
		driveVelocity = false;
		wristEncoderPosition = false;
		wristMotorCurrent = false;
		wristBannerSensor = false;
		intakeBannerSensor = false;
		wristLimitSwitch = false;
		elevatorEncoder = true;
	}
	
	@Override
	public void robotInit() 
	{
		joy = new Joysticks();
		Drivetrain.driveTrainInitialization();
		Wrist.wristInitialization();
		Elevator.elevatorInitialization();
	}

	@Override
	public void autonomousInit() 
	{
		//Autonomous.runAuto();
	}

	@Override
	public void autonomousPeriodic() 
	{
		
	}

	@Override
	public void teleopInit()
	{
		Drivetrain.resetEncoders();
		Wrist.aimedWristState = 1;
		Elevator.aimedElevatorState = 1;
		Drivetrain.setEncoderValues();
		setTests();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		Drivetrain.setEncoderValues();
		updateJoysticks();
		//Drivetrain.runYeetDrive(joy.leftJoySticky, joy.rightJoyStickx);
		//Drivetrain.runDrive(1, -1);
		//Drivetrain.runArcadeDrivetrain(joy.leftJoySticky, joy.rightJoyStickx);
		//Drivetrain.setSpeed(joy.leftJoySticky, joy.leftJoySticky);
		runWrist();
		runElevator();
		runTests();
		Compressor007.runCompressor();
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
		Wrist.setManualWristOverride(joy.leftJoySticky1 * 0.6);
		Wrist.runWrist();
	}
	public void runElevator()
	{
		Elevator.setElevatorEncoder();
		Elevator.setElevatorButtons(joy.buttonA1, joy.buttonB1,  joy.buttonY1, joy.buttonX1);
		Elevator.setManualOverride(joy.rightJoySticky1 * .6);
		Elevator.runElevator();
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
		else if(driveVelocity)
		{
			Drivetrain.printVelocity();
		}
		else if(elevatorEncoder)
		{
			Elevator.testElevatorEncoders();
		}
	}
}


