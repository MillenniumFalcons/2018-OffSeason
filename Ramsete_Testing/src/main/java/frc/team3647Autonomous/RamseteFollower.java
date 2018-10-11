package frc.team3647Autonomous;

import frc.team3647Subsystems.*;
import frc.team3647Autonomous.*;
import frc.team3647Utility.*;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import java.io.File;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class RamseteFollower
{
    Trajectory sourceTrajectory;
    Odometry odo = new Odometry();
    Drivetrain mDrivetrain = new Drivetrain();
    int pointNum = 0;

    public RamseteFollower(String path)
    {
        sourceTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_source_Jaci.csv"));
        System.out.println("Loaded path");
        odo.odometryInit();
        System.out.println(sourceTrajectory.get(0).x + " " + sourceTrajectory.get(0).y + " " +sourceTrajectory.get(0).heading);
        odo.setOdometry(sourceTrajectory.get(0).x, sourceTrajectory.get(0).y, sourceTrajectory.get(0).heading);
    }

    public void runPath()
    {
        Segment currentSegment = sourceTrajectory.get(pointNum);
        double linVel = adjustedLinVel(currentSegment.x, currentSegment.y, currentSegment.heading, currentSegment.velocity, targetAngVel());
        double angVel = adjustedAngVel(currentSegment.x, currentSegment.y, currentSegment.heading, currentSegment.velocity, targetAngVel());
        double lOutput = ((-Units.inchesToMeters(Constants.kWheelBase) * angVel) / 2 + linVel) * (1/Units.feetToMeters(Constants.kMaxVelocity)); //calculate velocity in m/s then convert to scale of -1 to 1
        double rOutput = ((+Units.inchesToMeters(Constants.kWheelBase) * angVel) / 2 + linVel) * (1/Units.feetToMeters(Constants.kMaxVelocity)); //v = ω*r
        SmartDashboard.putNumber("Target Velocity", linVel);
        SmartDashboard.putNumber("Target Angular Velocity", angVel);
        SmartDashboard.putNumber("lOutput", lOutput);
        SmartDashboard.putNumber("rOutput", rOutput);

        pointNum++;
        odo.printPosition();

        mDrivetrain.setSpeed(lOutput, rOutput);
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
    
    public double kGain(double targetAngVel, double targetLinVel)
    {
        return 2 * Constants.kZeta * Math.sqrt(targetAngVel * targetAngVel + Constants.kBeta * targetLinVel * targetLinVel);
    }
    
    public double adjustedLinVel(double targetX, double targetY, double targetTheta, double targetLinVel, double targetAngVel)
    {
        double kGain = kGain(targetAngVel, targetLinVel);
        double xError = targetX - odo.x;
        double yError = targetY - odo.y;
        double thetaError = clampTheta(targetTheta - odo.theta);
        return targetLinVel * Math.cos(thetaError) + kGain * (Math.cos(odo.theta) * xError + Math.sin(odo.theta) * yError);
    }

    public double adjustedAngVel(double targetX, double targetY, double targetTheta, double targetLinVel, double targetAngVel)
    {
        double kGain = kGain(targetAngVel, targetLinVel);
        double xError = targetX - odo.x;
        double yError = targetY - odo.y;
        double thetaError = clampTheta(targetTheta - odo.theta);
        return targetAngVel + Constants.kBeta * targetLinVel * (Math.sin(thetaError) / thetaError) * (Math.cos(odo.theta) * yError + Math.sin(odo.theta) * xError) + kGain* thetaError;
    }

    
    public double clampTheta(double theta)
    {
        // while (theta >= Math.PI)
        // {
        //     theta -= 2*Math.PI;
        // }
        // while (theta < -Math.PI)
        // {
        //     theta += 2*Math.PI;
        // }
        return Pathfinder.d2r(Pathfinder.boundHalfDegrees(Pathfinder.r2d(theta)));
    }
    
    public boolean isFinished() 
    {
        return pointNum == sourceTrajectory.length();
    }
}