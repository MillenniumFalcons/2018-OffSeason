package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Drivetrain 
{
	public static boolean velocityMode, closedPositionLoop;
	public static double targetPositionRotationsForRight, targetPositionRotationsForLeft, targetVelocityForLeftMotor_UnitsPer100ms, targetVelocityForRightMotor_UnitsPer100ms;
	public static double checks, checkt, speed, turn, lSpeed, rSpeed;
	
	public static void setMode()
	{
		velocityMode = false;
		closedPositionLoop = true;
	}
	
	public static TalonSRX leftSRX = new TalonSRX(Constants.leftMaster);
	public static TalonSRX rightSRX = new TalonSRX(Constants.rightMaster);
	
	public static VictorSPX leftSPX1 = new VictorSPX(Constants.leftSlave1);
	public static VictorSPX rightSPX1 = new VictorSPX(Constants.rightSlave1);
	public static VictorSPX leftSPX2 = new VictorSPX(Constants.leftSlave2);
	public static VictorSPX rightSPX2 = new VictorSPX(Constants.rightSlave2);

	public static void driveTrainInitialization()
	{
		if(velocityMode)
		{
			leftSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , 0, Constants.kTimeoutMs);
			leftSRX.setSensorPhase(true);
			leftSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
			leftSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
			leftSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
			leftSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
			
			rightSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , 0, Constants.kTimeoutMs);
			rightSRX.setSensorPhase(true);
			rightSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
			rightSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
			rightSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
			rightSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		}
		else if(closedPositionLoop)
		{
			leftSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx,
					Constants.kTimeoutMs);
			leftSRX.setSensorPhase(Constants.kSensorPhase);
			leftSRX.setInverted(Constants.kMotorInvert);
			leftSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
			leftSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
			leftSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
			leftSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
			leftSRX.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
			int absolutePosition = leftSRX.getSensorCollection().getPulseWidthPosition();
			absolutePosition &= 0xFFF;
			if (Constants.kSensorPhase)
				absolutePosition *= -1;
			if (Constants.kMotorInvert)
				absolutePosition *= -1;
			/* set the quadrature (relative) sensor to match absolute */
			leftSRX.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

			
		}
		
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
		if(velocityMode)
		{
			targetVelocityForRightMotor_UnitsPer100ms = rSpeed * 500.0 * 4096 / 600;
			targetVelocityForLeftMotor_UnitsPer100ms = lSpeed * 500.0 * 4096 / 600;
			rightSRX.set(ControlMode.Velocity, targetVelocityForRightMotor_UnitsPer100ms);
			leftSRX.set(ControlMode.Velocity, targetVelocityForLeftMotor_UnitsPer100ms);
		}
		else if(closedPositionLoop)
		{
			targetPositionRotationsForRight = rSpeed * 10.0 * 4096;
			targetPositionRotationsForLeft = lSpeed * 10.0 * 4096;
			rightSRX.set(ControlMode.Position, targetPositionRotationsForRight);
			leftSRX.set(ControlMode.Position, targetPositionRotationsForLeft);
		}
		else
		{
			rightSRX.set(ControlMode.PercentOutput, 0);
			leftSRX.set(ControlMode.PercentOutput, 0);
			System.out.println("Code BBBBBBroke");
		}
	}
	
	
}
