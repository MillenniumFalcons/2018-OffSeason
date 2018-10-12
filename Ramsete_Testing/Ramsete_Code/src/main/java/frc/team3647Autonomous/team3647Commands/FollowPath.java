package frc.team3647Autonomous.team3647Commands;

import com.ctre.phoenix.time.StopWatch;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.team3647Autonomous.RamseteFollower;

public class FollowPath extends Command 
{
  RamseteFollower pathFollower;
  Notifier pathThread;
  Timer stopWatch = new Timer();

  public FollowPath(String path) 
  {
    requires(Robot.mDrivetrain);
    pathFollower = new RamseteFollower(path);
    System.out.println("Created Ramsete follower");
    pathThread = new Notifier(() ->{
      pathFollower.runPath();
    });
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() 
  {
    Robot.mDrivetrain.resetEncoders();
    stopWatch.start();
    System.out.println("Initialized path");
    pathThread.startPeriodic(0.02);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    System.out.println("Path currently running");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    return pathFollower.isFinished();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() 
  {
    pathThread.stop();
    System.out.println("Done with path!");
    stopWatch.stop();
    System.out.println("Time to complete path: " + stopWatch.get());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() 
  {
    System.out.println("Path interrupted!");
  }
}
