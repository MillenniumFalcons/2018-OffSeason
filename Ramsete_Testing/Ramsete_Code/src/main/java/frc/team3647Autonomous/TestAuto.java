package frc.team3647Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3647Autonomous.team3647Commands.*;

public class TestAuto extends CommandGroup 
{
  //test ye autos here
  public TestAuto() 
  {
    addSequential(new FollowPath("StraightFiveMAndCurveLeft", true));
  }
}
