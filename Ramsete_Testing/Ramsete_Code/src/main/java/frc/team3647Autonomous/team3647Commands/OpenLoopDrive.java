package frc.team3647Autonomous.team3647Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3647Subsystems.*;
import frc.team3647Autonomous.*;
import frc.team3647Utility.*;
import frc.robot.*;

public class OpenLoopDrive extends Command 
{
  double lOutput, rOutput, timeMS;
  public static Timer stopWatch = new Timer();

  public OpenLoopDrive(double l, double r, double ms) 
  {
    requires(Robot.mDrivetrain);
    timeMS = ms;
    rOutput = r;
    lOutput = l;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    stopWatch.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    if(stopWatch.get() < timeMS)
    {
      Robot.mDrivetrain.setSpeed(lOutput, rOutput);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    return stopWatch.get() == timeMS;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() 
  {
    stopWatch.stop();
    Robot.mDrivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() 
  {

  }
}
