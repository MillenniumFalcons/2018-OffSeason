package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class Wrist {
	public static int elevatorState, aimedElevatorState;
	public static int aimedWristState;
	/*
	 * 0. Surya Big Gay
	 * 1. Flat
	 * 2. Aim
	 * 3. Idle
	 */

	public static boolean flat, aim, idle, suryaBigGay, manualOverride, originalPositionButton, limitSwitchState;
	public static double overrideValue, speed, wristEncoder; 
	public static int pidMode, wristEncoderValue;
	public static WPI_TalonSRX wristMotor = new WPI_TalonSRX(Constants.wristPin);
	static DigitalInput limitSwitch = new DigitalInput(Constants.wristLimitSwitch); 
  	public static DigitalInput bannerSensor = new DigitalInput(Constants.intakeBannerSensor);

	public static void wristInitialization()
	{
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.kTimeoutMs);
		wristMotor.setSensorPhase(true);
		wristMotor.selectProfileSlot(Constants.cubePID, 0);
		wristMotor.config_kF(Constants.noCubePID, Constants.noCubeF, Constants.kTimeoutMs);
		wristMotor.config_kP(Constants.noCubePID, Constants.noCubeP, Constants.kTimeoutMs);
		wristMotor.config_kI(Constants.noCubePID, Constants.noCubeI, Constants.kTimeoutMs);
		wristMotor.config_kD(Constants.noCubePID, Constants.noCubeD, Constants.kTimeoutMs);	

		wristMotor.config_kF(Constants.cubePID, Constants.cubeF, Constants.kTimeoutMs);		
		wristMotor.config_kP(Constants.cubePID, Constants.cubeP, Constants.kTimeoutMs);		
		wristMotor.config_kI(Constants.cubePID, Constants.cubeI, Constants.kTimeoutMs);		
		wristMotor.config_kD(Constants.cubePID, Constants.cubeD, Constants.kTimeoutMs);		
	}
	
	public static void testWristEncoder()
	{
		System.out.println("Wrist Encoder Value: " + wristEncoderValue);
	}
	
	public static void resetWristEncoder()
	{
		Wrist.wristMotor.getSensorCollection().setQuadraturePosition(0, 10);
	}
	public static void setWristIdle()
	{
		Wrist.wristMotor.getSensorCollection().setQuadraturePosition(Constants.idle, 10);
	}
	
	public static void setWristEncoder()
	{
		if(reachedFlat())
		{
			resetWristEncoder();
		}
		wristEncoderValue = -Wrist.wristMotor.getSensorCollection().getQuadraturePosition();
	}

	public static void setLimitSwitch()
	{
		limitSwitchState = limitSwitch.get();
	}
	
	public static void testLimitSwitch()
	{
		if(reachedFlat())
		{
			System.out.println("true");
		}
		else
		{
			System.out.println("false");
		}
	}
	
	public static boolean getIntakeBannerSensor()
	{
		return bannerSensor.get();
	}

	public static void testBannerSensor()
	{
		if(getIntakeBannerSensor())
		{
			System.out.println("Intake Banner = True");
		} else {
			System.out.println("Intake Banner = False");
		}
	}

	public static boolean reachedFlat()
	{
			if(limitSwitch.get())
			{
				return true;
			} 
			else 
			{
				return false;
			}
	}

	public static void testWristCurrent()
	{
		System.out.println("Wrist Current: " + wristMotor.getOutputCurrent());
	}
	
	public static void setWristButtons(boolean flatButton, boolean aimButton, boolean idleButton)
	{
		flat = flatButton;
		aim = aimButton;
		idle = idleButton;
	}
	
	public static void moveWristPosition(double position)
	{
		wristMotor.set(ControlMode.Position, position);
	}

	public static void moveWrist(double speed)
	{
		wristMotor.set(ControlMode.PercentOutput, speed);
	}
	
	public static void stopWrist()
	{
		moveWrist(0);
	}
	
	public static void setManualWristOverride(double jValue)
	{
		if(Math.abs(jValue) <.1 )
		{
			manualOverride = false;
		} 
		else 
		{
			overrideValue = jValue;
			manualOverride = true;
		}
	}
	
	public static void runWrist()
	{
		if(getIntakeBannerSensor()) //no cube
		{
			wristMotor.selectProfileSlot(Constants.noCubePID, 0);
			if(flat)
			{
				aimedWristState = 1;
			}
			else if(aim)
			{
				aimedWristState = 2;
			}
			else if (idle)
			{
				aimedWristState = 3;
			}
			else if(manualOverride)
			{
				aimedWristState = -1;
			}
			switch(aimedWristState)
			{
				case 0:
				break;
				case 1:
					if(reachedFlat())
					{
						resetWristEncoder();
						stopWrist();
					}
					else
					{
						moveWrist(-.2);
					}
				break;
				case 2:
					moveWristPosition(Constants.aim);
				break;
				case 3:
					moveWristPosition(Constants.idle);
				break;
				case -1:
					if(!manualOverride)
					{
						overrideValue = 0;
					}
					moveWrist(overrideValue);
				break;
			}
		}
		else
		{
			wristMotor.selectProfileSlot(Constants.cubePID, 0);
			if(flat)
			{
				aimedWristState = 1;
			}
			else if(aim)
			{
				aimedWristState = 2;
			}
			else if (idle)
			{
				aimedWristState = 3;
			}
			else if(manualOverride)
			{
				aimedWristState = -1;
			}
			switch(aimedWristState)
			{
				case 0:
				break;
				case 1:
					if(reachedFlat())
					{
						resetWristEncoder();
						stopWrist();
					}
					else
					{
						moveWrist(-0.2);
					}
				break;
				case 2:
					moveWristPosition(Constants.aim);
				break;
				case 3:
					moveWristPosition(Constants.idle);
				break;
				case -1:
					if(!manualOverride)
					{
						overrideValue = 0;
					}
					moveWrist(overrideValue);
				break;
			}
		}
	}
}