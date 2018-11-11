package frc.team3647inputs;

import frc.team3647subsystems.Drivetrain;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;
import frc.robot.*;

public class Encoders 
{
	public int leftEncoderValue, rightEncoderValue;
	public int  rVelocity, lVelocity;
	public double rVelocityFPS, lVelocityFPS;

	public int prevLEncoder, prevREncoder;
	
	public void setEncoderValues()
	{
		leftEncoderValue = Drivetrain.leftSRX.getSelectedSensorPosition(Constants.drivePID);
		rightEncoderValue = Drivetrain.rightSRX.getSelectedSensorPosition(Constants.drivePID);
	}

	public void dontSkip()
	{	
		if(prevLEncoder > leftEncoderValue)
		{
			Drivetrain.leftSRX.setSelectedSensorPosition(prevLEncoder, Constants.drivePID, Constants.kTimeoutMs);
		}
		else 
		{
			prevLEncoder = leftEncoderValue;
		}
		if(prevREncoder > rightEncoderValue)
		{
			Drivetrain.rightSRX.setSelectedSensorPosition(prevREncoder, Constants.drivePID, Constants.kTimeoutMs);
		}
		else 
		{
			prevREncoder = rightEncoderValue;
		}
	}
	
	public void resetEncoders()
	{
		Drivetrain.leftSRX.setSelectedSensorPosition(0, Constants.drivePID, Constants.kTimeoutMs);
		Drivetrain.rightSRX.setSelectedSensorPosition(0, Constants.drivePID, Constants.kTimeoutMs);
	}

	public void testEncodersWithDrive(boolean jValue)
	{
		testEncoders();
		if(jValue)
		{
			resetEncoders();
		}
	}
	
	public void testEncoders()
	{
		System.out.println("Left Encoder Value: " + leftEncoderValue);
		System.out.println("Right Encoder Value: " + rightEncoderValue);
	}

	public void testEncoderVelocity()
	{
		rVelocity = Drivetrain.rightSRX.getSelectedSensorVelocity(Constants.drivePID);
		lVelocity = Drivetrain.leftSRX.getSelectedSensorVelocity(Constants.drivePID);
		rVelocityFPS = (rVelocity / 1440) * 10 * 5 * Math.PI / 12;
		System.out.println("Left Encoder Velocity: " + lVelocity + "Left FPS: " + lVelocityFPS);
		System.out.println("Right Encoder Velocity: " + rVelocity + "Right FPS: " + rVelocityFPS);
	}

	public void encoderVelocity()
	{
		rVelocity = Drivetrain.rightSRX.getSelectedSensorVelocity(Constants.drivePID);
		lVelocity = Drivetrain.leftSRX.getSelectedSensorVelocity(Constants.drivePID);
		System.out.println("L: " + lVelocity + " R: " + rVelocity);
	}
 	public void testEncoderCLError()
	{
		System.out.println("Left Encoder CL Error: " + Drivetrain.leftSRX.getClosedLoopError(Constants.drivePID));
		System.out.println("Right Encoder CL Error: " + Drivetrain.rightSRX.getClosedLoopError(Constants.drivePID));
	}

}
