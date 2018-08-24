package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DigitalInput;

public class Elevator 
{
	/*
	 * 0. Start
	 * 1. Stop
	 * 2. Switch
	 * 3. Scale
	 * 4. Lower Scale
	 */
	public static int aimedElevatorState, elevatorEncoderValue;
	
	public static boolean bottom, sWitch, scale, lowerScale, moving, manualOverride, originalPositionButton;
    public static double overrideValue;
	
	public static DigitalInput bannerSensor = new DigitalInput(Constants.elevatorBannerSensor); 

	public static WPI_TalonSRX leftGearboxMaster = new WPI_TalonSRX(Constants.leftGearboxSRX);
	public static WPI_TalonSRX rightGearbox = new WPI_TalonSRX(Constants.rightGearboxSRX);
	public static VictorSPX leftGearboxSPX = new VictorSPX(Constants.leftGearboxSPX);
	public static VictorSPX rightGearboxSPX = new VictorSPX(Constants.rightGearboxSPX);
	
	public static DifferentialDrive elevatorDrive = new DifferentialDrive(leftGearboxMaster, rightGearbox);
	public static boolean reachedBottom = false;
    
    public static void elevatorInitialization()
	{
		leftGearboxMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.kTimeoutMs);
		leftGearboxMaster.setSensorPhase(true);
		leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
		leftGearboxMaster.config_kF(Constants.carriagePID, Constants.carriageF, Constants.kTimeoutMs);
		leftGearboxMaster.config_kP(Constants.carriagePID, Constants.carriageP, Constants.kTimeoutMs);
		leftGearboxMaster.config_kI(Constants.carriagePID, Constants.carriageI, Constants.kTimeoutMs);
		leftGearboxMaster.config_kD(Constants.carriagePID, Constants.carriageD, Constants.kTimeoutMs);	

		leftGearboxMaster.config_kF(Constants.interstagePID, Constants.interstageF, Constants.kTimeoutMs);		
		leftGearboxMaster.config_kP(Constants.interstagePID, Constants.interstageP, Constants.kTimeoutMs);		
		leftGearboxMaster.config_kI(Constants.interstagePID, Constants.interstageI, Constants.kTimeoutMs);		
        leftGearboxMaster.config_kD(Constants.interstagePID, Constants.interstageD, Constants.kTimeoutMs);	
        
        leftGearboxMaster.follow(leftGearboxMaster);
        rightGearboxSPX.follow(leftGearboxMaster);
        leftGearboxSPX.follow(leftGearboxMaster);
        rightGearbox.setInverted(true);
        rightGearboxSPX.setInverted(true);
	}

	public static void setElevatorEncoder()
	{
        if(reachedBottom())
		{
            resetElevatorEncoders();
		}
		elevatorEncoderValue = Elevator.leftGearboxMaster.getSensorCollection().getQuadraturePosition();
	}
	
	public static void resetElevatorEncoders()
	{
        Elevator.leftGearboxMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
    public static void stopElevator()
    {
        moveElevator(0);
    }
    
    public static void moveElevator(double speed)
    {
        leftGearboxMaster.set(ControlMode.PercentOutput, -speed);
        //leftGearboxMasterSPX.set(ControlMode.PercentOutput, -speed);
        //rightGearboxSPX.set(ControlMode.PercentOutput, -speed);
    }
    
    public static void moveElevatorPosition(double position)
    {
        leftGearboxMaster.set(ControlMode.Position, position);
    }

	public static boolean reachedBottom()
	{
        if(bannerSensor.get())
		{
            return false;
		}
		else
		{
            return true;
		}
	}
    
	public static void setElevatorButtons(boolean bottomButton, boolean switchButton, boolean scaleButton, boolean LSButton)
	{
        bottom = bottomButton;
		sWitch = switchButton;
		lowerScale = LSButton;
		scale = scaleButton;
	}
	
	public static void setManualOverride(double jValue)
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
    
	public static void runDarthVader()
	{
		if(bottom)
		{
			leftGearboxMaster.selectProfileSlot(Constants.carriagePID, 0);
			aimedElevatorState = 1;
		}
		else if(sWitch)
		{
			leftGearboxMaster.selectProfileSlot(Constants.carriagePID, 0);
			aimedElevatorState = 2;
		}
		else if(lowerScale)
		{
			leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
			aimedElevatorState = 3;
		}
		else if(scale)
		{
			leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
			aimedElevatorState = 4;
		}
        switch(aimedElevatorState)
		{
			case 0:
				if(manualOverride)
				{
					aimedElevatorState = -1;
				}
				else if(reachedBottom)
				{
					stopElevator();
					aimedElevatorState = 1;
				}
				else
				{
					moveElevator(-.2);
				}
			break;
			case 1:
				if(reachedBottom())
				{
					resetElevatorEncoders();
					stopElevator();
				}
				else
				{
					moveElevator(-0.2);
				}
			break;
			case 2:
				moveElevatorPosition(Constants.sWitch);
			break;
			case 3:
				moveElevatorPosition(Constants.lowerScale);
			break;
			case 4:
				moveElevatorPosition(Constants.scale);
			break;
			case -1:
				if(!manualOverride)
				{
					overrideValue = 0;
				}
				moveElevator(overrideValue);
			break;
        }
    }
    
    public static void testElevatorEncoders()
    {
        System.out.println("Elevator Encoder Value: " + elevatorEncoderValue);
    }
    
    public static void testBannerSensor()
    {
        if(reachedBottom())
        {
            System.out.println("Banner Sensor Triggered!");
        }
        else
        {
            System.out.println("Banner Sensor Not Triggered!");
        }
    }

    public static void testElevatorCurrent()
    {
        System.out.println("Right Elevator Current:" + leftGearboxMaster.getOutputCurrent());
    }
}