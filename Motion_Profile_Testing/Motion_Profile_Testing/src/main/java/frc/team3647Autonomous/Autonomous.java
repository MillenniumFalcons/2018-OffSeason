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

    public static void testAuto(Encoders enc) // do i use object for trajectory??
    {
        TrajectoryFollower traj = new TrajectoryFollower();
        enc.setEncoderValues();
        System.out.println("path finished?: " + TrajectoryFollower.pathFinished);
        traj.followPath("test");
        traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue);
    }
}