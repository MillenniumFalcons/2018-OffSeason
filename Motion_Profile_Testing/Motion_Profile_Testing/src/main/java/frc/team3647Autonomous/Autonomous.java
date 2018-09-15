package frc.team3647Autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647Subsystems.*;
import frc.team3647Inputs.*;

public class Autonomous
{
    static TrajectoryFollower traj = new TrajectoryFollower();
    public static Timer stopWatch = new Timer();
    public static int currentState;
    public static double time;
    public static void initialization(Encoders enc, NavX navX)
    {
        enc.resetEncoders();
        navX.resetAngle();
        Drivetrain.setToBrake();
        Drivetrain.stop();
        currentState = 0;
    }

    public static void middleToRightSwitch(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0: //initialize
                stopWatch.start(); 
                enc.resetEncoders();
                navX.resetAngle();
                System.out.println("Loading Path");
                traj.initialize();
                traj.followPath("MiddleToRightSwitch", false);
                currentState = 1;
                break;
            case 1: //move elevator down
                //Wrist.moveUp();
				// if(Elevator.elevatorEncoderValue == 0)
				// {
				// 	    Elevator.stopElevator();
				// 	    currentState = 2;
				// }
				// else if(stopWatch.get() > Constants.elevatorTimeout)
				// {
				// 	    Elevator.stopElevator();
				// 	    currentState = 2;
				// }
				// else
				// {
				// 	    Elevator.moveElevator(-.3);
                // }
                currentState = 2;
				break;
            case 2: //go to right switch from middle and move elevator up and wrist to flat
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                //wrist.currentWristState = 0;
                //Elevator.moveSwitch();
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    currentState = 3;
                    stopWatch.reset();
                    stopWatch.start();
                }
                break;
            case 3: //keep elevator up and drop cube
                //Elevator.moveSwitch();
                time = stopWatch.get();
                if(time < Constants.shootCubeTime)
                {
                    //IntakeWheels.runIntake(0, 0, true, -1, -1, false);
                    enc.resetEncoders();
                    navX.resetAngle();
                }
                else
                {
                    currentState = 4;
                    stopWatch.reset();
                }
                break;
            case 4: //maintain elevator position
                //Elevator.moveSwitch();
                break;
        }
    }

    public static void middleToLeftSwitch(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0: //initialize
                stopWatch.start(); 
                enc.resetEncoders();
                navX.resetAngle();
                System.out.println("Loading Path");
                traj.initialize();
                traj.followPath("MiddleToLeftSwitch", false);
                currentState = 1;
                break;
            case 1: //move elevator down
                //Wrist.moveUp();
				// if(Elevator.elevatorEncoderValue == 0)
				// {
				// 	    Elevator.stopElevator();
				// 	    currentState = 2;
				// }
				// else if(stopWatch.get() > Constants.elevatorTimeout)
				// {
				// 	    Elevator.stopElevator();
				// 	    currentState = 2;
				// }
				// else
				// {
				// 	    Elevator.moveElevator(-.3);
                // }
                currentState = 2;
				break;
            case 2: //go to right switch from middle and move elevator up and wrist to flat
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                //wrist.currentWristState = 0;
                //Elevator.moveSwitch();
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    currentState = 3;
                    stopWatch.reset();
                    stopWatch.start();
                }
                break;
            case 3: //keep elevator up and drop cube
                //Elevator.moveSwitch();
                time = stopWatch.get();
                if(time < Constants.shootCubeTime)
                {
                    //IntakeWheels.runIntake(0, 0, true, -1, -1, false);
                    enc.resetEncoders();
                    navX.resetAngle();
                }
                else
                {
                    currentState = 4;
                    stopWatch.reset();
                }
                break;
            case 4: //maintain elevator position
                //Elevator.moveSwitch();
                break;
        }
    }

    // TESTING AUTOS
    public static void soloPathAuto(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0:
                enc.resetEncoders();
                navX.resetAngle();
                System.out.println("Loading Path");
                traj.initialize();
                traj.followPath("MiddleToRightSwitch", false);
               // traj.followPath("MiddleToRightSwitch", true);
                //traj.followPath("StraightTenFeet", false);
               // traj.followPath("SuryaOmegaLul", false);
                //traj.followPath("StraightandLeftCurve", false);
                currentState = 1;
                break;
            case 1:
            System.out.println("Running Path (1/2)");
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
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
                System.out.println("Loading Path");
                traj.initialize();
                traj.followPath("StraightandLeftCurve", false);
                //traj.followPath("StraightTenFeet");
                currentState = 1;
                break;
            case 1:
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    currentState = 2;
                }
                break;
            case 2:
                enc.resetEncoders();
                navX.resetAngle();
                traj.initialize();
                System.out.println("Loading Path");
                traj.followPath("StraightandRightCruve", false);
                currentState = 3;
            case 3:
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    System.out.println("path 2 finished");
                    currentState = 4;
                }
                break;
            case 4:
                break;
        }
    }
}