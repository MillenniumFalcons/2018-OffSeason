package frc.team3647Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647Inputs.Encoders;
import frc.team3647Inputs.NavX;
import frc.team3647Subsystems.Drivetrain;

public class Autonomous
{
    static TrajectoryFollower traj = new TrajectoryFollower();
    public static Timer stopWatch = new Timer();
    public static int currentState;
    public static double time;
    public static boolean twoCube, rSwitch, rScale;
    public static String switchSide, scaleSide;

    public static void initialization(Encoders enc, NavX navX)
    {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            //determine switch side
            if(gameData.charAt(0) == 'R')
            {
                switchSide = "Right";
            }
            else if(gameData.charAt(0) == 'R')
            {
                switchSide = "Left";
            }
            //determine scale side
            if(gameData.charAt(1) == 'R')
            {
                scaleSide = "Right";
            }
            else if(gameData.charAt(1) == 'L')
            {
                scaleSide = "Left";
            }
        }
        else
        {
            System.out.println("No game data recieved!");
        }
        enc.resetEncoders();
        navX.resetAngle();
        traj.initialize();
        Drivetrain.setToBrake();
        Drivetrain.stop();
        currentState = 0;
        twoCube = true;
    }

    public static void runAuto(Encoders enc, NavX navX)
    {
        int auto = 0; //0 is crossline, 1 is switch only, 2 is scale only, 3 is scale + switch, 4 is scale clear
        int startPosition = 0; //0 is middle, -1 is left, 1 is right
        //determine which auto to run
        switch(auto)
        {
            case 0: //crossline
                crossLine(enc, navX);
                break;
            case 1: //switch only
                if(startPosition == 0) //if start in middle run middle switch auto
                {
                    middleSwitch(enc, navX);
                }
                else if(startPosition == -1 && switchSide == "Left" || startPosition == 1 && switchSide == "Right") //if switch on same side as start position run same side switch
                {
                    //sideSameSwitch(enc,navX);
                }
                else if(startPosition == -1 && switchSide == "Right" || startPosition == 1 && switchSide == "Left") //if switch on opposite side run far side auto
                {
                    //sideFarSwitch(enc,navX);
                }
                break;
            case 2: //scale only
                if(startPosition == -1 && scaleSide == "Left" || startPosition == 1 && scaleSide == "Right") //if scale on same side run same side auto
                {
                    //sideSameScale(enc, navX, false);
                }
                else if(startPosition == -1 && scaleSide == "Right" || startPosition == 1 && scaleSide == "Left") //if scale on opposite side run far scale auto
                {
                    //sideFarScale(enc, navX, false);
                }
                break;
            case 3: //scale and switch
                if(startPosition == -1 && scaleSide == "Left" || startPosition == 1 && scaleSide == "Right") //if same side run same side auto and boolean true for go to switch
                {
                    //sideSameScale(enc, navX, true);
                }
                else if(startPosition == -1 && scaleSide == "Right" || startPosition == 1 && scaleSide == "Left") //if opposite side run far auto and boolean true for go to switch
                {
                    //sideFarScale(enc, navX, true);
                }
                break;
            case 4: //scale clear
                if(startPosition == -1 && scaleSide == "Left" || startPosition == 1 && scaleSide == "Right") //if same side run the scale clear auto
                {
                    //sideSameScaleClear(enc, navX);
                }
                else if(startPosition == -1 && scaleSide == "Right" || startPosition == 1 && scaleSide == "Left") //if not same side run ??????
                {
                    
                }
                break;
        }
    }

    //Comp Autos
    public static void middleSwitch(Encoders enc, NavX navX)
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
                traj.followPath(switchSide + "MiddleToSwitch", false, false);
                // Elevator.currentWristState = 0;
                currentState = 1;
                break;
            case 1: //move elevator down (ZERO OUT ELEVATOR)
                // Wrist.moveUp();
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
                currentState = 2;//remove in final
				break;
            case 2: //go to right switch from middle and move elevator up and wrist to flat
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                //Elevator.moveSwitch();
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    stopWatch.reset();
                    stopWatch.start();
                    currentState = 3;
                }
                break;
            case 3: //keep elevator up and drop cube
                //Elevator.moveSwitch();
                time = stopWatch.get();
                if(time < Constants.shootCubeTime)
                {
                    //IntakeWheels.runIntake(0, 0, true, Constants.autoShootSpeed, Constants.autoShootSpeed, false);
                }
                else
                {
                    stopWatch.reset();
                    enc.resetEncoders();
                    currentState = 4;
                }
                break;
            case 4: //maintain elevator position and if 2 cube proceed
                //Elevator.moveSwitch();
                if(twoCube)
                {
                    enc.resetEncoders();
                    System.out.println("Loading Path");
                    traj.followPath(switchSide + "SwitchMoveToSecondCube", true, false);                  
                    currentState = 5;
                }
                break;
            case 5: //move elevator and wrist down, move to position to pick up 2nd cube
                //Elevator.moveBottom(false);
                //Wrist.moveToBottom();
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    enc.resetEncoders();
                    System.out.println("Loading Path");
                    traj.followPath(switchSide + "StraightToSecondCube", false, false);                  
                    currentState = 6;
                }
                break;
            case 6: //move to pick up 2 cube and run intake
                //IntakeWheels.runIntake(0, 0, true, Constants.autoIntakeSpeed, Constants.autoIntakeSpeed, false);
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    enc.resetEncoders();
                    System.out.println("Loading Path back up to right switch");
                    traj.followPath(switchSide + "BackUpToSwitch", true, false);
                    System.out.println("Loaded path back up to right switch");
                    currentState = 7;
                }
                break;
            case 7: //move wrist up and back up to line up to switch
                //Wrist.moveToUp();
                System.out.println("running path 1/2 back up to right switch");
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                System.out.println("Finished running back up to right switch");
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    enc.resetEncoders();
                    System.out.println("Loading Path");
                    traj.followPath("StraightToScoreSecondCube", false, false);
                    // Elevator.currentWristState = 0;                  
                    currentState = 8;
                }
                System.out.println(traj.isFinished());
                break;
            case 8: // move elevator to switch (moves wrist down automatically) and go straight to score
               //Elevator.moveSwitch(); 
               traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
               if(traj.isFinished())
               {
                    System.out.println("Path Finished (2/2)");
                    stopWatch.reset();
                    stopWatch.start();
                    currentState = 9;
               }
               break;
            case 9: //keep elevator up and shoot cube for x amount of time
                //Elevator.moveSwitch();
                time = stopWatch.get();
                if(time < Constants.shootCubeTime)
                {
                    //IntakeWheels.runIntake(0, 0, true, Constants.autoShootSpeed, Constants.autoShootSpeed, false);
                }
                else
                {
                    stopWatch.reset();
                    enc.resetEncoders();
                    currentState = 10;
                }
                break;
            case 10: //done with auto -- keep elevator up
                //Elevator.moveSwitch();
                break;
        }
    }

    public static void crossLine(Encoders enc, NavX navX)
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
                traj.followPath("CrossLine", false, false);
                    // Elevator.currentWristState = 0;
                currentState = 1;
                break;
            case 1: //move elevator down (ZERO OUT ELEVATOR)
                // Wrist.moveUp();
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
                currentState = 2;//remove in final
                break;
            case 2:
                //Wrist.moveUp();
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                }
                break;        
        }
    }

    public static void leftToLeftScale(Encoders enc, NavX navX)
    {
        double neededDistance = Constants.scaleAutoElevatorUpDistance;
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0: //initialize
                stopWatch.start(); 
                enc.resetEncoders();
                navX.resetAngle();
                System.out.println("Loading Path");
                traj.followPath("leftToLeftScale", false, false);
                // Elevator.currentWristState = 0;
                currentState = 1;
                break;
            case 1: //move elevator down (ZERO OUT ELEVATOR)
                // Wrist.moveUp();
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
                currentState = 2;//remove in final
				break;
            case 2: //go to left scale from left side and move elevator up and wrist to flat
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(enc.leftEncoderValue > neededDistance && enc.rightEncoderCLError > neededDistance)
                    //Elevator.moveScale();
                //Elevator.moveScale();
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    stopWatch.reset();
                    stopWatch.start();
                    currentState = 3;
                }
                break;
            case 3: //keep elevator up and drop cube
                //Elevator.moveScale();
                time = stopWatch.get();
                if(time < Constants.shootCubeTime)
                {
                    //IntakeWheels.runIntake(0, 0, true, Constants.autoShootSpeed, Constants.autoShootSpeed, false);
                }
                else
                {
                    stopWatch.reset();
                    enc.resetEncoders();
                    navX.resetAngle();
                    currentState = 4;
                }
                break;
            case 4: //auto path complete - elevator down?
                //Elevator.moveToBottom();
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
                traj.followPath("leftSwitchFromMiddle1", false, false);   
                //traj.followPath(WaypointPaths.middleToRightSwitch(), false);
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
                traj.followPath("MiddleToRightSwitch", false, false);
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
                // navX.resetAngle();
                traj.initialize();
                System.out.println("Loading Path");
                traj.followPath("MiddleToRightSwitch", true, true);
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