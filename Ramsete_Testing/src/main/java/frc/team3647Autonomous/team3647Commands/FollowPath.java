package frc.team3647Autonomous.team3647Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.team3647Autonomous.RamseteFollower;
import frc.team3647Utility.Odometry;

public class FollowPath extends Command 
{
  RamseteFollower pathFollower;
  Odometry odo;
  Timer stopWatch;

  public FollowPath(String path) 
  {
    requires(Robot.mDrivetrain);
    pathFollower = new RamseteFollower(path);
    System.out.println("Created Ramsete follower");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() 
  {
    Robot.mDrivetrain.resetEncoders();
    stopWatch.start();
    System.out.println("Initialized path");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    pathFollower.runPath();
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
