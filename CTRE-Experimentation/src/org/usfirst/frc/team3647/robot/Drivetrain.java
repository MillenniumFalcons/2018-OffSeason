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
	public static double targetPositionRotationsForRight, targetPositionRotationsForLeft, targetVelocityForLeftMotor_UnitsPer100ms, targetVelocityForRightMotor_UnitsPer100ms;
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
			
		leftSRX.config_kF(Constants.kPIDLoopIdx, 0.1097, Constants.kTimeoutMs);
		leftSRX.config_kP(Constants.kPIDLoopIdx, 0.113333, Constants.kTimeoutMs);
		leftSRX.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		leftSRX.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
			
		rightSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , 0, Constants.kTimeoutMs);
		rightSRX.setSensorPhase(true);
		rightSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
			
		rightSRX.config_kF(Constants.kPIDLoopIdx, 0.1097, Constants.kTimeoutMs);
		rightSRX.config_kP(Constants.kPIDLoopIdx, 0.113333, Constants.kTimeoutMs);
		rightSRX.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		rightSRX.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);    
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
	}
	
	public static void runDrivetrain(double yValue, double xValue)
	{
		checks = Math.abs(yValue);
		checkt = Math.abs(xValue);
		speed = checks * yValue;
		turn = checkt * xValue;
		lSpeed = speed + turn;
		rSpeed = -speed - turn;
		
		targetVelocityForRightMotor_UnitsPer100ms = rSpeed * 500.0 * 4096 / 600;
		targetVelocityForLeftMotor_UnitsPer100ms = lSpeed * 500.0 * 4096 / 600;
		rightSRX.set(ControlMode.Velocity, targetVelocityForRightMotor_UnitsPer100ms);
		leftSRX.set(ControlMode.Velocity, targetVelocityForLeftMotor_UnitsPer100ms);
	}
	
	public static void setSpeed(double lSpeed, double rSpeed)
	{
		targetVelocityForRightMotor_UnitsPer100ms = rSpeed * 500.0 * 4096 / 600;
		targetVelocityForLeftMotor_UnitsPer100ms = lSpeed * 500.0 * 4096 / 600;
		rightSRX.set(ControlMode.Velocity, targetVelocityForRightMotor_UnitsPer100ms);
		leftSRX.set(ControlMode.Velocity, targetVelocityForLeftMotor_UnitsPer100ms);
	}
	
	public static double leftEncoderValue, rightEncoderValue;
	
	public void setEncoderValues()
	{
		leftEncoderValue = leftSRX.getSensorCollection().getQuadraturePosition();
		rightEncoderValue = -rightSRX.getSensorCollection().getQuadraturePosition();
	}
	
	public static void resetEncoders()
	{
		leftSRX.getSensorCollection().setQuadraturePosition(0, 10);
		rightSRX.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public static void testEncoders()
	{
		System.out.println("Left Encoder Value: " + leftEncoderValue);
		System.out.println("Right Encoder Value: " + rightEncoderValue);
	}
	
	public static void printError()
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
	
	public static void kms()
	{
		System.out.println("right error: " + rightSRX.getClosedLoopError());
		System.out.println("left error: " + leftSRX.getClosedLoopError());
	}
	
}
