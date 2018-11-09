
package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;

public class Robot extends IterativeRobot 
{
    Encoders enc;
	Joysticks joy;
    NavX navX;
    MotorSafety safety;
	MotorSafetyHelper safetyChecker;

    @Override
    public void robotInit() 
    {
        try
		{
			enc = new Encoders();
			joy = new Joysticks();
            navX = new NavX();
            safetyChecker = new MotorSafetyHelper(safety);
            Drivetrain.drivetrainInitialization(false);
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
    }

    @Override
    public void autonomousInit() 
    {
        try 
		{
			CrashChecker.logAutoInit();
			Autonomous.initialize(enc, navX);
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
    }

    @Override
    public void autonomousPeriodic() 
    {
        while(DriverStation.getInstance().isAutonomous() && !DriverStation.getInstance().isDisabled())
		{
            safetyChecker.setSafetyEnabled(false);
        }
    }


    @Override
    public void teleopPeriodic() 
    {
        safetyChecker.setSafetyEnabled(false);
        joy.setMainContollerValues();
        Drivetrain.newArcadeDrive(joy.leftJoySticky, joy.rightJoyStickx, navX.yaw, navX);
    }

    @Override
    public void testPeriodic() 
    {

    }

    @Override
	public void disabledPeriodic()
	{
		Drivetrain.setToCoast();
	}
}
