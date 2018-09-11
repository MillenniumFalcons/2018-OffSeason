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

    public static void soloPathAuto(Encoders enc)
    {
        TrajectoryFollower traj = new TrajectoryFollower();
        enc.setEncoderValues();
        enc.resetEncoders();
        switch(currentState)
        {
            case 0:
                traj.followPath("StraightandLeftCurve");
                //traj.followPath("StraightTenFeet");
                System.out.println("loaded path");
                currentState = 1;
                break;
            case 1:
              traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue);
              if(traj.pathFinished)
              {
                System.out.println("path finished");
                currentState = 2;
              }
              else
              {
                System.out.println("path not finished");
              }
              break;
            case 2:
              break;
        }
    }

    public static void twoPathAuto(Encoders enc)
    {
        TrajectoryFollower traj = new TrajectoryFollower();
        enc.setEncoderValues();
        enc.resetEncoders();
        switch(currentState)
        {
            case 0:
                traj.followPath("StraightandLeftCurve");
                System.out.println("loaded path 1");
                currentState = 1;
                break;
            case 1:
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue);
                if(traj.pathFinished)
                {
                    System.out.println("path 1 finished");
                    currentState = 2;
                }
                else
                {
                    System.out.println("path 1 not finished");
                }
                break;
            case 2:
                enc.resetEncoders();
                traj.followPath("CurveRightandStraight");
                System.out.println("loaded path 2");
                currentState = 3;
            case 3:
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue);
                if(traj.pathFinished)
                {
                    System.out.println("path 2 finished");
                    currentState = 4;
                }
                else
                {
                    System.out.println("path 2 not finished");
                }
                break;
            case 4:
                break;
        }
    }
}