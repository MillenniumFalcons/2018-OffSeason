package frc.team3647Subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem
{
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
	
	public static WPI_TalonSRX leftSRX = new WPI_TalonSRX(Constants.kLeftMaster);
	public static WPI_TalonSRX rightSRX = new WPI_TalonSRX(Constants.kRightMaster);
	public static VictorSPX leftSPX1 = new VictorSPX(Constants.kLeftSlave1);
	public static VictorSPX rightSPX1 = new VictorSPX(Constants.kRightSlave1);
	public static VictorSPX leftSPX2 = new VictorSPX(Constants.kLeftSlave2);
	public static VictorSPX rightSPX2 = new VictorSPX(Constants.kRightSlave2);
	
	public static DifferentialDrive drive = new DifferentialDrive(leftSRX, rightSRX);
	AHRS AHRSnavX = new AHRS(SPI.Port.kMXP);

	public void drivetrainInitialization()
	{
		//Config left side PID settings
		leftSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , 0, Constants.kTimeoutMs);
		leftSRX.setSensorPhase(false);
		leftSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		//Config left side PID Values
		leftSRX.selectProfileSlot(Constants.kDrivePID, 0);
		leftSRX.config_kF(Constants.kDrivePID, Constants.kLDrivekF, Constants.kTimeoutMs);
		leftSRX.config_kP(Constants.kDrivePID, Constants.kLDrivekP, Constants.kTimeoutMs);
		leftSRX.config_kI(Constants.kDrivePID, Constants.kLDrivekI, Constants.kTimeoutMs);
		leftSRX.config_kD(Constants.kDrivePID, Constants.kLDrivekD, Constants.kTimeoutMs);
		//Config right side PID settings
		rightSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , Constants.kDrivePID, Constants.kTimeoutMs);
		rightSRX.setSensorPhase(false);
		rightSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		//Config right side PID Values
		rightSRX.selectProfileSlot(Constants.kDrivePID, 0);
		rightSRX.config_kF(Constants.kDrivePID, Constants.kRDrivekF, Constants.kTimeoutMs);
		rightSRX.config_kP(Constants.kDrivePID, Constants.kRDrivekP, Constants.kTimeoutMs);
		rightSRX.config_kI(Constants.kDrivePID, Constants.kRDrivekI, Constants.kTimeoutMs);
		rightSRX.config_kD(Constants.kDrivePID, Constants.kRDrivekD, Constants.kTimeoutMs);
		//Set up followers
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
		rightSRX.setInverted(true);
		rightSPX1.setInverted(true);
		rightSPX2.setInverted(true);
	}

	public void FRCarcadedrive(double yValue, double xValue)
	{
		drive.arcadeDrive(yValue, xValue, false);
	}
	
	public void tankDrive(double lYValue, double rYValue)
	{
		drive.tankDrive(lYValue * .986, rYValue, false);
	}
	
	public void yeetDrive(double yValue, double xValue)
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
			xValue = Math.pow(xValue, powerOfTurn);
			yValue *= speedOfStraight;
			xValue *= speedOfTurn;
			setSpeed(yValue + xValue, yValue - xValue);
		}
	}

	public void newArcadeDrive(double yValue, double xValue, double angle)
	{
		
		if(yValue != 0 && xValue == 0)
	 	{
			//System.out.println(0);
			setSpeed(yValue, yValue);
	 	}
		else if(yValue == 0 && xValue == 0)
		{
			tankDrive(0, 0);
		}
		else
		{
			drive.curvatureDrive(xValue, yValue, true);
			//System.out.println(1);
		}
	}

	public void setPercentOutput(double lOutput, double rOutput)
	{
		rightSRX.set(ControlMode.PercentOutput, lOutput);
		leftSRX.set(ControlMode.PercentOutput, rOutput);
	}

	
	public void stop()
	{
		rightSRX.stopMotor();
		leftSRX.stopMotor();
	}

	public boolean stopped()
	{
		if(rightSRX.getSelectedSensorVelocity(Constants.kDrivePID) == 0 && leftSRX.getSelectedSensorVelocity(Constants.kDrivePID) == 0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public void setSpeed(double lSpeed, double rSpeed)
	{
		double targetVelocityRight = rSpeed * Constants.kVelocityConstant;
		double targetVelocityLeft = lSpeed * Constants.kVelocityConstant;
		rightSRX.set(ControlMode.Velocity, targetVelocityRight);
		leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	}

	public void testDrivetrainCurrent()
	{
		System.out.println("Left Motor Current: " + leftSRX.getOutputCurrent());
		System.out.println("Right Motor Current:" + rightSRX.getOutputCurrent());
	}

	public void resetEncoders()
	{
		leftSRX.setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
		rightSRX.setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
	}
	
	public void testEncoderPosition()
	{
		System.out.println("Left Encoder Value: " + leftSRX.getSelectedSensorPosition(0));
		System.out.println("Right Encoder Value: " + rightSRX.getSelectedSensorPosition(0));
	}

	public void testEncoderVelocity()
	{
		System.out.println("Left Encoder Velocity: " + leftSRX.getSelectedSensorVelocity(0));
		System.out.println("Right Encoder Velocity: " + rightSRX.getSelectedSensorVelocity(0));
	}

    public void resetAngle()
    {
        AHRSnavX.zeroYaw();
    }

	public double getYawUnclamped()
    {
        return AHRSnavX.getAngle();
    }
    
    public double getYaw()
    {
        return AHRSnavX.getYaw();
	}
	
	public void testAngle()
    {
        System.out.println("Yaw: " + -getYaw() + "Yaw (Unclamped): " + getYawUnclamped());
    }
	
	public void setToBrake()
	{
		leftSRX.setNeutralMode(NeutralMode.Brake);
		rightSRX.setNeutralMode(NeutralMode.Brake);
		leftSPX1.setNeutralMode(NeutralMode.Brake);
		leftSPX2.setNeutralMode(NeutralMode.Brake);
		rightSPX1.setNeutralMode(NeutralMode.Brake);
		rightSPX2.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setToCoast()
	{
		leftSRX.setNeutralMode(NeutralMode.Coast);
		rightSRX.setNeutralMode(NeutralMode.Coast);
		leftSPX1.setNeutralMode(NeutralMode.Coast);
		leftSPX2.setNeutralMode(NeutralMode.Coast);
		rightSPX1.setNeutralMode(NeutralMode.Coast);
		rightSPX2.setNeutralMode(NeutralMode.Coast);
	}
}
