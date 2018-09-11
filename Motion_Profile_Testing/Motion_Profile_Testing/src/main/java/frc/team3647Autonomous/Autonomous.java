package frc.team3647Autonomous;

import frc.team3647Subsystems.Drivetrain;
import frc.team3647Subsystems.Encoders;

public class Autonomous
{
    static int currentState = 0;
   

    public static void initialization(Encoders enc)
    {
        enc.resetEncoders();
        Drivetrain.setToBrake();
        Drivetrain.stop();
    }

    public static void testAuto(trajectoryFollower traj, Encoders enc) // do i use object for trajectory??
    {
        System.out.println("path finished?: " + trajectoryFollower.pathFinished);
        traj.followPath("test");
        traj.runPath(enc);
    }
}