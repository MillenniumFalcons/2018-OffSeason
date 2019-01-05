package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;
import frc.team3647inputs.Joysticks;
import frc.team3647subsystems.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends IterativeRobot 
{
	Joysticks stick;
	double rightSpeed;
	double leftSpeed;
	double speed;
	double prevError = 0;
	double sumError = 0;
	NetworkTable table;
	
  
  @Override
  public void robotInit() 
  {
	  table = NetworkTableInstance.getDefault().getTable("limelight");
	  stick = new Joysticks();
	  Drivetrain.drivetrainInitialization(false);
  }



  
  	@Override
  	public void autonomousInit() 
  	{
		Drivetrain.FRCarcadedrive(0.5, 0.5);
  	}	

  
  	@Override
  	public void autonomousPeriodic() 
  	{
	
		NetworkTableEntry tx = table.getEntry("tx");
		NetworkTableEntry ty = table.getEntry("ty");
		NetworkTableEntry ta = table.getEntry("ta");
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
		double area = ta.getDouble(0.0);

		SmartDashboard.putNumber("LimelightX", x);
		SmartDashboard.putNumber("LimelightY", y);
		SmartDashboard.putNumber("LimelightArea", area);

		System.out.println("X: " + x);
		double s = .25;
		// System.out.println("Y: " + y);
		// System.out.println("Area: " + area);
		if(x > 1 && x < -1)
		{
			Drivetrain.FRCarcadedrive(0, 0);

		}
		else if(x > 1)
		{
			Drivetrain.leftSRX.set(-s);
			Drivetrain.rightSRX.set(s);
		}
		else
		{
			Drivetrain.leftSRX.set(s);
			Drivetrain.rightSRX.set(-s);
		}
  	}
	
	  // This is the function that runs during Tele-Operated Period
	public void teleopPeriodic() 
	{
		stick.setMainContollerValues();
		// stick.setCoDriverContollerValues();
		// System.out.println("It Has Begun");
		// parseCam();
		// visionPID(XCenter, YCenter);
		Drivetrain.FRCarcadedrive(stick.rightJoyStickx, stick.leftJoySticky);
		//visionPID(XCenter,YCenter);
	}

  
  @Override
  public void testPeriodic() 
  {

  }
}
