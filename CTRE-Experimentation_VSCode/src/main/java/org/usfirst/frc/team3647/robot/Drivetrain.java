package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Drivetrain 
{
	public static boolean velocityMode, closedPositionLoop;
	public static double targetPositionRotationsForRight, targetPositionRotationsForLeft, targetVelocityLeft, targetVelocityRight;
	public static double checks, checkt, speed, turn, lSpeed, rSpeed;
	
	public static TalonSRX leftSRX = new TalonSRX(Constants.leftMaster);
	public static TalonSRX rightSRX = new TalonSRX(Constants.rightMaster);
	
	public static VictorSPX leftSPX1 = new VictorSPX(Constants.leftSlave1);
	public static VictorSPX rightSPX1 = new VictorSPX(Constants.rightSlave1);
	public static VictorSPX leftSPX2 = new VictorSPX(Constants.leftSlave2);
	public static VictorSPX rightSPX2 = new VictorSPX(Constants.rightSlave2);
	
	public static void driveTrainInitialization()
	{
		//Config left side PID settings
		leftSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , 0, Constants.kTimeoutMs);
		leftSRX.setSensorPhase(false);
		leftSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		//Config left side PID Values
		leftSRX.selectProfileSlot(Constants.drivePID, 0);
		leftSRX.config_kF(Constants.drivePID, Constants.lDrivekF, Constants.kTimeoutMs);
		leftSRX.config_kP(Constants.drivePID, Constants.lDrivekP, Constants.kTimeoutMs);
		leftSRX.config_kI(Constants.drivePID, Constants.lDrivekI, Constants.kTimeoutMs);
		leftSRX.config_kD(Constants.drivePID, Constants.lDrivekD, Constants.kTimeoutMs);
		//Config right side PID settings
		rightSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , Constants.drivePID, Constants.kTimeoutMs);
		rightSRX.setSensorPhase(false);
		rightSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		//Config right side PID Values
		rightSRX.selectProfileSlot(Constants.drivePID, 0);
		rightSRX.config_kF(Constants.drivePID, Constants.rDrivekF, Constants.kTimeoutMs);
		rightSRX.config_kP(Constants.drivePID, Constants.rDrivekP, Constants.kTimeoutMs);
		rightSRX.config_kI(Constants.drivePID, Constants.rDrivekI, Constants.kTimeoutMs);
		rightSRX.config_kD(Constants.drivePID, Constants.rDrivekD, Constants.kTimeoutMs);
		//Set up followers
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
		rightSRX.setInverted(true);
		rightSPX1.setInverted(true);
		rightSPX2.setInverted(true);
		//Set neutral mode
		Drivetrain.leftSRX.setNeutralMode(NeutralMode.Brake);
		Drivetrain.rightSRX.setNeutralMode(NeutralMode.Brake);
		Drivetrain.leftSPX1.setNeutralMode(NeutralMode.Brake);
		Drivetrain.leftSPX2.setNeutralMode(NeutralMode.Brake);
		Drivetrain.rightSPX1.setNeutralMode(NeutralMode.Brake);
		Drivetrain.rightSPX2.setNeutralMode(NeutralMode.Brake);
	}
	
	public static void runArcadeDrivetrain(double yValue, double xValue)
	{
		
	//	speed = Math.pow(yValue, 1.9);
	//	turn = Math.pow(xValue, 1.9);
		checks = Math.abs(yValue);
		checkt = Math.abs(xValue);
		speed = checks * yValue;
		turn = checkt * xValue;
		
		lSpeed = speed + turn;
		rSpeed = speed - turn;
		
		targetVelocityRight = rSpeed * Constants.velocityConstant;
		targetVelocityLeft = lSpeed * Constants.velocityConstant;
		rightSRX.set(ControlMode.Velocity, targetVelocityRight);
		leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	}
	
	public static double turnRatioR;
	public static double turnRatioL;
	
	// public static void runYeetDrive(double yValue, double xValue) // doesn't work lmao
	// {
	// 	speed = deadZone(Math.pow(yValue, 1.96));
	// 	turn = deadZone(Math.pow(xValue, 1.96));
		
	// 	if(turn == 0)
	// 	{
	// 		turnRatioR = 1;
	// 		turnRatioL = 1;
	// 	}
	// 	else if (speed == 0)
	// 	{
	// 		turnRatioR = -1;
	// 		turnRatioL = 1;
	// 		speed = turn;
	// 	}
	// 	else if(turn>0 && !(speed == 0))
	// 	{
	// 		turnRatioR = 1 - turn;
	// 		turnRatioL = 1;
	// 	}
	// 	else if(turn<0 &&!(speed == 0))
	// 	{
	// 		turnRatioR = 1;
	// 		turnRatioL = 1 - turn;
	// 	}
	// 	rSpeed = speed * Math.pow(turnRatioR, 2);
	// 	lSpeed = speed * Math.pow(turnRatioL, 2);
	// 	targetVelocityRight = rSpeed * Constants.velocityConstant;
	// 	targetVelocityLeft = lSpeed * Constants.velocityConstant;
	// 	rightSRX.set(ControlMode.Velocity, targetVelocityRight);
	// 	leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	// }

	public static void yeetDrive(double yValue, double xValue)
	{
		double powerOfStraight = 1, powerOfTurn = .6;
		double speedOfStraight = 1, speedOfTurn = 1;

		if(yValue != 0 && xValue == 0)
		{
			setSpeed(yValue, yValue);
		}
		else if(yValue == 0 && xValue != 0)
		{
			setSpeed(xValue, -xValue);
		}
		else if(yValue != 0 && xValue != 0)
		{
			yValue = Math.pow(yValue, powerOfStraight);
			xValue = Math.pow(xValue, powerOfStraight);
			yValue *= speedOfStraight;
			xValue *= speedOfTurn;
			setSpeed(yValue + xValue, yValue - xValue);
		}
	}
	
	public static void setSpeed(double lSpeed, double rSpeed)
	{
		targetVelocityRight = rSpeed * Constants.velocityConstant;
		targetVelocityLeft = lSpeed * Constants.velocityConstant;
		rightSRX.set(ControlMode.Velocity, targetVelocityRight);
		leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	}

	public static void runDrive(double lSpeed, double rSpeed)
	{
		rightSRX.set(ControlMode.PercentOutput, rSpeed);
		leftSRX.set(ControlMode.PercentOutput, lSpeed);
	}
	
	public static double leftEncoderValue, rightEncoderValue, leftEncoderVelocity, rightEncoderVelocity;
	
	public static void setEncoderValues()
	{
		leftEncoderValue = leftSRX.getSelectedSensorPosition(Constants.drivePID);
		rightEncoderValue = rightSRX.getSelectedSensorPosition(Constants.drivePID);
		leftEncoderVelocity = leftSRX.getSelectedSensorVelocity(Constants.drivePID);
		rightEncoderVelocity = rightSRX.getSelectedSensorVelocity(Constants.drivePID);
	}
	
	public static void resetEncoders()
	{
		leftSRX.getSensorCollection().setQuadraturePosition(0, 10);
		rightSRX.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public static void testEncodersPosition()
	{
		System.out.println("Left Encoder Value: " + leftEncoderValue);
		System.out.println("Right Encoder Value: " + rightEncoderValue);
	}
	
	public static void printValueError()
	{
		double error = Math.abs(leftEncoderValue - rightEncoderValue);
		if(Math.abs(leftEncoderValue) > Math.abs(rightEncoderValue))
		{
			System.out.println("Left going too fast; " + error);
		}
		else if(Math.abs(leftEncoderValue) < Math.abs(rightEncoderValue))
		{
			System.out.println("Right going too fast; " + error);
		}
		else
		{	
			System.out.println("No error");
		}
	}
	
	public static void printVelocityError(int side)
	{
		int rightVelocityError = rightSRX.getClosedLoopError(Constants.drivePID);
		int leftVelocityError = leftSRX.getClosedLoopError(Constants.drivePID);
		
		int velocityDifference = Math.abs(Math.abs(rightSRX.getSelectedSensorVelocity(Constants.drivePID)) - Math.abs(leftSRX.getSelectedSensorVelocity(Constants.drivePID)));
		
		switch(side)//right
		{
			case 0:
				
				System.out.println("Target: " + targetVelocityRight + "Actual: " + rightEncoderVelocity + "Error: " + rightVelocityError);
				break;
				
			case 1://left
				
				System.out.println("Target: " + targetVelocityLeft + "Actual: " + leftEncoderVelocity + "Error: " + leftVelocityError);
				break;
				
			case 2://both
			
				System.out.println("Difference between Velocity: " + velocityDifference + "Right Velocity: " + rightEncoderVelocity + "Left Velocity: " + leftEncoderVelocity);
				//System.out.println("left/right speed: " + rightEncoderVelocity/leftEncoderVelocity);
				break;
		}
	}

	public static void printVelocity()
	{
		System.out.println("Right Velocity: " + rightSRX.getSelectedSensorVelocity(Constants.drivePID) + "Left Velocity: " + leftSRX.getSelectedSensorVelocity(Constants.drivePID));
		//System.out.println("attemping print");
	}
	
	public static double adjustedJValue;
	
	public static double deadZone(double input)
	{
		if(Math.abs(input) < Constants.deadZone)
		{
			adjustedJValue = 0;
		}
		else
		{
			adjustedJValue = input;
		}
		return (adjustedJValue);
	}
}
