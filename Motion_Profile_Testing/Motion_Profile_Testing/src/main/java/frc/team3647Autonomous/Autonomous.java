package frc.team3647Autonomous;

import frc.team3647Subsystems.*;

public class Autonomous
{
    static int currentState = 0;
    static TrajectoryFollower traj = new TrajectoryFollower();
   
    public static void initialization(Encoders enc, NavX navX)
    {
        enc.resetEncoders();
        navX.resetAngle();
        Drivetrain.setToBrake();
        Drivetrain.stop();
    }

    public static void soloPathAuto(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0:
               // traj.followPath("StraightandLeftCurve");
                enc.resetEncoders();
                navX.resetAngle();
                System.out.println("Loading Path");
                traj.initialize();
                traj.followPath("StraightTenFeet");
                currentState = 1;
                break;
            case 1:
            System.out.println("Running Path (1/2)");
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yaw);
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    currentState = 2;
                }
                else
                {
                    System.out.println("PATH NOT FINISHED");
                }
                break;
            case 2:
                System.out.println("CASE 2 REACHED (path finished)");
                break;
        }
    }

    public static void twoPathAuto(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0:
                enc.resetEncoders();
                navX.resetAngle();
                traj.initialize();
                traj.followPath("StraightandLeftCurve");
                System.out.println("loaded path 1");
                currentState = 1;
                break;
            case 1:
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yaw);
                if(traj.isFinished())
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
                navX.resetAngle();
                traj.initialize();
                traj.followPath("CurveRightandStraight");
                System.out.println("loaded path 2");
                currentState = 3;
            case 3:
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yaw);
                if(traj.isFinished())
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