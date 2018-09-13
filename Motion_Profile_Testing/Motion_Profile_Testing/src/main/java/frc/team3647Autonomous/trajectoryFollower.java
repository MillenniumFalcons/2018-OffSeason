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

    public void runPath(int lEncoder, int rEncoder, double navXAngle)
    {
        EncoderFollower right = new EncoderFollower(leftTrajectory);
        EncoderFollower left = new EncoderFollower(rightTrajectory);

        right.configureEncoder(rEncoder, 1440, Constants.wheelDiameter);
        left.configureEncoder(lEncoder, 1440, Constants.wheelDiameter);

        //set PID values
        right.configurePIDVA(Constants.rPFkP, Constants.rPFkI, Constants.rPFkD, Constants.rPFkV, Constants.rPFkA);
        left.configurePIDVA(Constants.lPFkP, Constants.lPFkI, Constants.lPFkD, Constants.lPFkV, Constants.lPFkA);
        //set follower values
        double rValue = right.calculate(rEncoder);
        double lValue = left.calculate(lEncoder);

        //navX gyro code
        double gyroHeading = -navXAngle; //invert since RHR
        double desiredHeading = Pathfinder.r2d(right.getHeading());
        double headingDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = 0.8 * (-1.0/80.0) * headingDifference;

        double rPower = rValue + turn;
        double lPower = lValue - turn;
        //set output
        Drivetrain.setPercentOutput(lPower, rPower); //with gyro
        //Drivetrain.setPercentOutput(lValue, rValue); //no gyro

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
        rightTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_right_Jaci.csv"));
        leftTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_left_Jaci.csv"));
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