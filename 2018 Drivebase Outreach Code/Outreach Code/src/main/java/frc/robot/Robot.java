package frc.robot;
import edu.wpi.first.wpilibj.*;
import frc.team3647inputs.*;
import frc.team3647subsystems.Drivetrain;


public class Robot extends IterativeRobot 
{
	//Objects
	Encoders enc;
	NavX navX;
	Joysticks stick;

	@Override
	public void robotInit() 
	{
		enc = new Encoders();
		stick = new Joysticks();
	  	Drivetrain.drivetrainInitialization(false);
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
		stick.setMainContollerValues();
		Drivetrain.FRCarcadedrive(stick.rightJoyStickx, stick.leftJoySticky);
	}

	@Override
	public void testInit()
	{
	}
	
	@Override
	public void testPeriodic() 
	{
	}
}
