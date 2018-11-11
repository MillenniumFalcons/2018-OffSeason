 package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.*;
import frc.team3647inputs.*;
import edu.wpi.first.wpilibj.CameraServer;


public class Robot extends IterativeRobot 
{
	//Objects
	Encoders enc;
	Joysticks joy;
	NavX navX;

	@Override
	public void robotInit() 
	{
		enc = new Encoders();
		joy = new Joysticks();
	}
	
	
	@Override
	public void autonomousInit() 
	{
	}
 
	@Override
	public void autonomousPeriodic() 
	{
	}
	
	@Override
	public void disabledPeriodic()
	{

	}
	
	@Override
	public void teleopInit()
	{
		
	}

	// This is the function that runs during Tele-Operated Period
	public void teleopPeriodic() 
	{

	}

	@Override
	public void testInit()
	{
	}
	
	@Override
	public void testPeriodic() 
	{
	}
	
	public void updateJoysticks()
	{
		joy.setMainContollerValues();
		joy.setCoDriverContollerValues();
	}	
}
