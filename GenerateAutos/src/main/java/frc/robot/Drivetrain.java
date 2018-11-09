package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.*;

public class Drivetrain 
{
	public static double aimedRatio, currentRatio, sum, speed, turn, turnRatioR, turnRatioL, rSpeed, lSpeed, constant, diff;
	public static boolean withinRange;

	static double supposedAngle;
	
	public static double initialCorrection = 0;//-.04//.085
	public static double correction = .08;
	
	public static WPI_TalonSRX leftSRX = new WPI_TalonSRX(Constants.leftMaster);
	public static WPI_TalonSRX rightSRX = new WPI_TalonSRX(Constants.rightMaster);
	
	public static VictorSPX leftSPX1 = new VictorSPX(Constants.leftSlave1);
	public static VictorSPX rightSPX1 = new VictorSPX(Constants.rightSlave1);
	public static VictorSPX leftSPX2 = new VictorSPX(Constants.leftSlave2);
	public static VictorSPX rightSPX2 = new VictorSPX(Constants.rightSlave2);
	
	public static DifferentialDrive drive = new DifferentialDrive(leftSRX, rightSRX);
	
	public static double adjustmentFactor = .88;
	
	static double []adjustmentValues = new double[2];

	public static boolean turnFinished;

	
	public static void drivetrainInitialization(boolean pracBot)
	{
		if(!pracBot)
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
		}
		else 
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
			leftSRX.config_kF(Constants.drivePID, Constants.lDrivekF1, Constants.kTimeoutMs);
			leftSRX.config_kP(Constants.drivePID, Constants.lDrivekP1, Constants.kTimeoutMs);
			leftSRX.config_kI(Constants.drivePID, Constants.lDrivekI1, Constants.kTimeoutMs);
			leftSRX.config_kD(Constants.drivePID, Constants.lDrivekD1, Constants.kTimeoutMs);
			//Config right side PID settings
			rightSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder , Constants.drivePID, Constants.kTimeoutMs);
			rightSRX.setSensorPhase(false);
			rightSRX.configNominalOutputForward(0, Constants.kTimeoutMs);
			rightSRX.configNominalOutputReverse(0, Constants.kTimeoutMs);
			rightSRX.configPeakOutputForward(1, Constants.kTimeoutMs);
			rightSRX.configPeakOutputReverse(-1, Constants.kTimeoutMs);
			//Config right side PID Values
			rightSRX.selectProfileSlot(Constants.drivePID, 0);
			rightSRX.config_kF(Constants.drivePID, Constants.rDrivekF1, Constants.kTimeoutMs);
			rightSRX.config_kP(Constants.drivePID, Constants.rDrivekP1, Constants.kTimeoutMs);
			rightSRX.config_kI(Constants.drivePID, Constants.rDrivekI1, Constants.kTimeoutMs);
			rightSRX.config_kD(Constants.drivePID, Constants.rDrivekD1, Constants.kTimeoutMs);
			//Set up followers
			leftSPX1.follow(leftSRX);
			leftSPX2.follow(leftSRX);
			rightSPX1.follow(rightSRX);
			rightSPX2.follow(rightSRX);
			rightSRX.setInverted(true);
			rightSPX1.setInverted(true);
			rightSPX2.setInverted(true);
		}
		
	}
	
	static double avg;

	public static double absYValue;
	public static double absXValue;
	public static void FRCarcadedrive(double yValue, double xValue)
	{
		drive.arcadeDrive(yValue, xValue, false);
	}
	
	public static void tankDrive(double lYValue, double rYValue)
	{
		drive.tankDrive(lYValue * .986, rYValue, false);
	}
	
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
			xValue = Math.pow(xValue, powerOfTurn);
			yValue *= speedOfStraight;
			xValue *= speedOfTurn;
			setSpeed(yValue + xValue, yValue - xValue);
		}
	}
	
	public static void curvatureDrive(double throttle, double turn)
	{
		drive.curvatureDrive(throttle, turn, true);
	}

	public static void jankStraight(double angle, double speed)
	{
		if(angle > 0)
		{
			constant = 1 - (.04 * angle);
			setSpeed(speed * constant, speed);
		}
		else if(angle < 0)
		{
			angle = Math.abs(angle);
			constant = 1 - (.04 * angle);
			setSpeed(speed, speed * constant);
		}
		else 
		{
			setSpeed(speed, speed);
		}
	}

	public static void jankStraightNotFirst(double angle, double speed, double supposedAngle)
	{
		diff = supposedAngle - angle;
		if(diff > 0)
		{
			constant = 1 - (.02 * diff);
			setSpeed(speed * constant, speed);
		}
		else if(diff < 0)
		{
			diff = Math.abs(diff);
			constant = 1 - (.02 * diff);
			setSpeed(speed, speed * constant);
		}
		else 
		{
			setSpeed(speed, speed);
		}
	}

	public static void newArcadeDrive(double yValue, double xValue, double angle, NavX gyro)
	{
		if(yValue != 0 && Math.abs(xValue) < 0.15)
	 	{
			//System.out.println(0);
			setSpeed(yValue, yValue);
	 	}
		else if(yValue == 0 && Math.abs(xValue) < 0.15)
		{
			gyro.resetAngle();
			stop();
			supposedAngle = angle;
		}
		else
		{
			gyro.resetAngle();
			curvatureDrive(xValue, yValue);
			supposedAngle = angle;
			//System.out.println(1);
		}
	}

	public static void setPercentOutput(double lOutput, double rOutput)
	{
		rightSRX.set(ControlMode.PercentOutput, lOutput);
		leftSRX.set(ControlMode.PercentOutput, rOutput);
	}

	
	public static void stop()
	{
		rightSRX.stopMotor();
		leftSRX.stopMotor();
	}

	public static boolean stopped()
	{
		if(rightSRX.getSelectedSensorVelocity(Constants.drivePID) == 0 && leftSRX.getSelectedSensorVelocity(Constants.drivePID) == 0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	public static void straightFow(double yValue, double angle, double supposedAngle)
	{
		diff = supposedAngle - angle;
		if(diff != 0)
		{
			if(diff > 0)
			{
				constant = 1 - (.02 * diff);
				setSpeed(yValue, yValue * constant);
				//System.out.println("Left speed: " + yValue + "; Right speed: " + yValue * constant);
			}
			else 
			{
				diff = Math.abs(diff);
				constant = 1 - (.02 * (diff));
				setSpeed(yValue * constant, yValue);
				//System.out.println("Left speed: " + yValue  * constant + "; Right speed: " + yValue);
			}
		}
		else 
		{
			setSpeed(yValue, yValue);
		}
	}

	public static void turnDegrees(NavX navX, double goalAngle, double rotationRate, double toleranceDegrees)
	{
		double output;
		double currentAngle = -navX.yawUnClamped;
		double angleDifference = goalAngle - currentAngle;
		if(angleDifference < toleranceDegrees)
		{
			turnFinished = true;

		}
		else
		{
			turnFinished = false;
			if(angleDifference > 10 || angleDifference < -10)
			{
				output = 0.7*rotationRate;
			}
			else
			{
				output = 0.5*rotationRate;
			}

			if(angleDifference > 0)
			{
				setSpeed(-output, output);
			}
			else
			{
				setSpeed(output, -output);
			}
		}
	}
	
	public static void setSpeed(double lSpeed, double rSpeed)
	{
		double targetVelocityRight = rSpeed * Constants.velocityConstant;
		double targetVelocityLeft = lSpeed * Constants.velocityConstant;
		rightSRX.set(ControlMode.Velocity, targetVelocityRight);
		leftSRX.set(ControlMode.Velocity, targetVelocityLeft);
	}

	public static void testDrivetrainCurrent()
	{
		System.out.println("Left Motor Current: " + leftSRX.getOutputCurrent());
		System.out.println("Right Motor Current:" + rightSRX.getOutputCurrent());
	}
	
	public static void enableCurrentLimiting(double amps)
	{
		leftSRX.enableCurrentLimit(true);
		rightSRX.enableCurrentLimit(true);
	}
	
	public static void setToBrake()
	{
		leftSRX.setNeutralMode(NeutralMode.Brake);
		rightSRX.setNeutralMode(NeutralMode.Brake);
		leftSPX1.setNeutralMode(NeutralMode.Brake);
		leftSPX2.setNeutralMode(NeutralMode.Brake);
		rightSPX1.setNeutralMode(NeutralMode.Brake);
		rightSPX2.setNeutralMode(NeutralMode.Brake);
	}
	
	public static void setToCoast()
	{
		leftSRX.setNeutralMode(NeutralMode.Coast);
		rightSRX.setNeutralMode(NeutralMode.Coast);
		leftSPX1.setNeutralMode(NeutralMode.Coast);
		leftSPX2.setNeutralMode(NeutralMode.Coast);
		rightSPX1.setNeutralMode(NeutralMode.Coast);
		rightSPX2.setNeutralMode(NeutralMode.Coast);
	}
}