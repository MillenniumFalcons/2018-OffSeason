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
		leftSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , 0, Constants.kTimeoutMs);
		leftSRX.setSensorPhase(true);
		leftSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		
		leftSRX.selectProfileSlot(Constants.drivePID, 0);
		leftSRX.config_kF(Constants.drivePID, Constants.lDrivekF, Constants.kTimeoutMs);
		leftSRX.config_kP(Constants.drivePID, Constants.lDrivekP, Constants.kTimeoutMs);
		leftSRX.config_kI(Constants.drivePID, Constants.lDrivekI, Constants.kTimeoutMs);
		leftSRX.config_kD(Constants.drivePID, Constants.lDrivekD, Constants.kTimeoutMs);
			
		rightSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , 0, Constants.kTimeoutMs);
		rightSRX.setSensorPhase(true);
		rightSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		
		rightSRX.selectProfileSlot(Constants.drivePID, 0);
		rightSRX.config_kF(Constants.drivePID, Constants.rDrivekF, Constants.kTimeoutMs);
		rightSRX.config_kP(Constants.drivePID, Constants.rDrivekP, Constants.kTimeoutMs);
		rightSRX.config_kI(Constants.drivePID, Constants.rDrivekI, Constants.kTimeoutMs);
		rightSRX.config_kD(Constants.drivePID, Constants.rDrivekD, Constants.kTimeoutMs);
		
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);    
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
	}
	
	
	public static void runArcadeDrivetrain(double yValue, double xValue)
	{
		
		speed = Math.pow(yValue, 1.9);
		turn = Math.pow(xValue, 1.9);
	//	checks = Math.abs(yValue);
	//	checkt = Math.abs(xValue);
	//	speed = checks * yValue;
	//	turn = checkt * xValue;
		
		lSpeed = speed + turn;
		rSpeed = -speed - turn;
		
		targetVelocityRight = rSpeed * Constants.velocityConstant;
		targetVelocityLeft = lSpeed * Constants.velocityConstant;
		rightSRX.set(ControlMode.Velocity, targetVelocityRight);
		leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	}
	
	public static double turnRatioR;
	public static double turnRatioL;
	
	public static void runYeetDrive(double yValue, double xValue)
	{
		speed = Math.pow(yValue, 1.96);
		turn = Math.pow(xValue, 1.96);
		if(turn == 0)
		{
			turnRatioR = 1;
			turnRatioL = 1;
		}
		else if (speed == 0 && turn != 0)
		{
			turnRatioR = -1;
			turnRatioL = 1;
			speed = turn;
		}
		else if(turn>0)
		{
			turnRatioR = 1 - turn;
			turnRatioL = 1;
		}
		else if(turn<0)
		{
			turnRatioR = 1;
			turnRatioL = 1 - turn;
		}
		rSpeed = speed * Math.pow(turnRatioR, 2);
		lSpeed = speed * Math.pow(turnRatioL, 2);
		targetVelocityRight = rSpeed * Constants.velocityConstant;
		targetVelocityLeft = lSpeed * Constants.velocityConstant;
		rightSRX.set(ControlMode.Velocity, targetVelocityRight);
		leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	}
	
	public static void setSpeed(double lSpeed, double rSpeed)
	{
		targetVelocityRight = rSpeed * Constants.velocityConstant;
		targetVelocityLeft = lSpeed * Constants.velocityConstant;
		rightSRX.set(ControlMode.Velocity, targetVelocityRight);
		leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	}
	
	public static double leftEncoderValue, rightEncoderValue, leftEncoderVelocity, rightEncoderVelocity;
	
	public void setEncoderValues()
	{
		leftEncoderValue = leftSRX.getSensorCollection().getQuadraturePosition();
		rightEncoderValue = -rightSRX.getSensorCollection().getQuadraturePosition();
		leftEncoderVelocity = leftSRX.getSelectedSensorVelocity();
		rightEncoderVelocity = -rightSRX.getSelectedSensorVelocity();
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
	
	public static void testEncoderVelocity()
	{
		System.out.println("Left Encoder Velocity: " + leftEncoderVelocity);
		System.out.println("Right Encoder Velocity: " + rightEncoderVelocity);
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
		int velocityDifference = Math.abs(rightSRX.getSelectedSensorVelocity() - leftSRX.getSelectedSensorVelocity());
		
		if(side == 0)//right
		{
			System.out.println("Target: " + targetVelocityRight + "Actual: " + rightEncoderVelocity + "Error: " + rightVelocityError);
		}
		else if(side == 1)//left
		{
			System.out.println("Target: " + targetVelocityLeft + "Actual: " + leftEncoderVelocity + "Error: " + leftVelocityError);
		}
		else if(side == 2)//both
		{
			System.out.println("Difference between Velocity: " + velocityDifference + "Right Velocity: " + rightEncoderVelocity + "Left Velocity: " + leftEncoderVelocity);
		}
	}
	
	public static void kms()
	{
//		System.out.println("right error: " + rightSRX.getClosedLoopError());
//		System.out.println("left error: " + leftSRX.getClosedLoopError());
	}
	
}
