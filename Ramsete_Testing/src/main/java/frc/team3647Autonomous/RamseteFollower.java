package frc.team3647Autonomous;

import frc.team3647Subsystems.*;
import frc.team3647Autonomous.*;
import frc.team3647Utility.*;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import java.io.File;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;

public class RamseteFollower
{
    Trajectory sourceTrajectory;
    Odometry odo;
    int pointNum = 0;

    public void followPath(String path)
    {
        sourceTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_source_Jaci.csv"));
    }

    public void runPath()
    {
        Segment currentSegment = sourceTrajectory.get(pointNum);
        double targetAngVel = targetAngVel();
        double velocity = calcVelocity(currentSegment.x, currentSegment.y, currentSegment.heading, currentSegment.velocity, targetAngVel);
        double angVel = calcAngVel(currentSegment.x, currentSegment.y, currentSegment.heading, currentSegment.velocity, targetAngVel);
        SmartDashboard.putNumber("Target Velocity", velocity);
        SmartDashboard.putNumber("Target Angular Velocity", angVel);
        double lOutput = ((-Constants.kWheelBase * angVel) / 2 + velocity) * (1/Units.feetToMeters(Constants.kMaxVelocity)); //calculate velocity in m/s then convert to scale of -1 to 1
        double rOutput = ((+Constants.kWheelBase * angVel) / 2 + velocity) * (1/Units.feetToMeters(Constants.kMaxVelocity));

        pointNum++;

        Robot.mDrivetrain.setSpeed(lOutput, rOutput);
    }

    public boolean isFinished()
    {
        return pointNum == sourceTrajectory.length();
    }

    public double targetAngVel()
    {
        if(pointNum < sourceTrajectory.length() - 1)
        {
            double previousTheta = sourceTrajectory.get(pointNum).heading;
            double nextTheta = sourceTrajectory.get(pointNum + 1).heading;
            return (nextTheta - previousTheta) / sourceTrajectory.get(pointNum).dt;
        }
        else
        {
            return 0;
        }
    }

    public double calcKGain(double targetAngVel, double targetVelocity)
    {
        return 2 * Constants.kZeta * Math.sqrt((Math.pow(targetAngVel, 2) + Math.pow(targetVelocity, 2)));
    }

    public double calcVelocity(double targetX, double targetY, double targetTheta, double targetVelocity, double targetAngVel)
    {
        double kGain = calcKGain(targetAngVel, targetVelocity);
        double xError = targetX - odo.x;
        double yError = targetY - odo.y;
        double thetaError = clampTheta(targetTheta - odo.theta);
        return targetVelocity * Math.cos(thetaError) + kGain * (Math.cos(odo.theta) * yError + Math.sin(odo.theta) * xError);
    }

    public double calcAngVel(double targetX, double targetY, double targetTheta, double targetVelocity, double targetAngVel)
    {
        double kGain = calcKGain(targetAngVel, targetVelocity);
        double xError = targetX - odo.x;
        double yError = targetY - odo.y;
        double thetaError = clampTheta(targetTheta - odo.theta);
        return targetAngVel + kGain * targetVelocity * (Math.sin(thetaError) / thetaError) * (Math.cos(odo.theta) * yError + Math.sin(odo.theta) * xError) + kGain* thetaError;
    }

    public double clampTheta(double theta)
    {
        while (theta >= Math.PI) theta -= 2*Math.PI;
        while (theta < -Math.PI) theta += 2*Math.PI;
        return theta;
    }
}