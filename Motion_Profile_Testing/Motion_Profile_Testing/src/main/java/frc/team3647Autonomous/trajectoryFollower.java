package frc.team3647Autonomous;


import jaci.pathfinder.*;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.File;

public class TrajectoryFollower
{
    Trajectory leftTrajectory, rightTrajectory;
    public boolean pathFinished = false;

    public void runPath(int lEncoder, int rEncoder)
    {
        EncoderFollower right = new EncoderFollower(leftTrajectory);
        EncoderFollower left = new EncoderFollower(rightTrajectory);

        right.configureEncoder(rEncoder, 1440, Constants.wheelDiameter);
        left.configureEncoder(lEncoder, 1440, Constants.wheelDiameter);

        //set PID values
        right.configurePIDVA(Constants.rPFkP, Constants.rPFkI, Constants.rPFkD, Constants.rPFkV, Constants.rPFkA);
        left.configurePIDVA(Constants.lPFkP, Constants.lPFkI, Constants.lPFkD, Constants.lPFkV, Constants.lPFkA);

        double rValue = right.calculate(rEncoder);
        double lValue = left.calculate(lEncoder);
        //set output
        Drivetrain.setPercentOutput(lValue, rValue);

        SmartDashboard.putNumber("target left speed", lValue);
        SmartDashboard.putNumber("target right speed", rValue);
        
        SmartDashboard.putNumber("left encoder value", lEncoder);
        SmartDashboard.putNumber("right encoder value", rEncoder);

        if(left.isFinished() && right.isFinished())
        {
          pathFinished = true;
        } 
        else
        {
          pathFinished = false;
        }
    }

    public void followPath(String path)
    {
        rightTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/src/main/paths/" + path + "_right_Jaci.csv"));
        leftTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/src/main/paths/" + path + "_left_Jaci.csv"));
    }

    public void followPath(Waypoint[] points)
    {
        Trajectory.Config configPoints = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, Constants.MPTimeStep, Constants.maxVelocity, Constants.maxAcceleration, Constants.maxJerk);
        Trajectory trajPoints = Pathfinder.generate(points, configPoints);

        TankModifier tankModifier = new TankModifier(trajPoints);
        tankModifier.modify(Constants.wheelBase);

        leftTrajectory = tankModifier.getLeftTrajectory();
        rightTrajectory = tankModifier.getRightTrajectory();
    }
}