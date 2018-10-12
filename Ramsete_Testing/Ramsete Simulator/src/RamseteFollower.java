import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.*;

import java.io.File;

/**3863 implementation of https://www.dis.uniroma1.it/~labrob/pub/papers/Ramsete01.pdf
/*special thanks to Solomon and Prateek
/*thanks to Jaci for her work on Pathfinder!
 */
public class RamseteFollower
{
    Trajectory sourceTrajectory;
    Odometry odo;
    //Drivetrain mDrivetrain = new Drivetrain();
    int pointNum;

    public RamseteFollower(String path)
    {
        sourceTrajectory = Pathfinder.readFromCSV(new File(System.getProperty("user.dir") + "\\src\\paths\\" + path + "_source_Jaci.csv"));
        System.out.println("Loaded path");
        //odo.odometryInit();
        pointNum = 0;
        System.out.println(sourceTrajectory.get(0).x + " " + sourceTrajectory.get(0).y + " " +sourceTrajectory.get(0).heading);
        odo = new Odometry(sourceTrajectory.get(0).x, sourceTrajectory.get(0).y, sourceTrajectory.get(0).heading);
    }

    double linVel, angVel;

    public void runPath()
    {
        Segment currentSegment = sourceTrajectory.get(pointNum);
        linVel = adjustedLinVel(currentSegment.x, currentSegment.y, currentSegment.heading, currentSegment.velocity, targetAngVel());
        angVel = adjustedAngVel(currentSegment.x, currentSegment.y, currentSegment.heading, currentSegment.velocity, targetAngVel());
        double lOutput = ((-Units.inchesToMeters(Constants.kWheelBase) * angVel) / 2 + linVel) * (1/Units.feetToMeters(Constants.kMaxVelocity)); //calculate velocity in m/s then convert to scale of -1 to 1
        double rOutput = ((+Units.inchesToMeters(Constants.kWheelBase) * angVel) / 2 + linVel) * (1/Units.feetToMeters(Constants.kMaxVelocity)); //v = ω*r
//        SmartDashboard.putNumber("Target Velocity", linVel);
//        SmartDashboard.putNumber("Target Angular Velocity", angVel);
//        SmartDashboard.putNumber("lOutput", lOutput);
//        SmartDashboard.putNumber("rOutput", rOutput);

        pointNum++;
        odo.printPosition();

//        mDrivetrain.setSpeed(lOutput, rOutput);
    }

    public void setOdometry(Odometry odometry)
    {
        odo = odometry;
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
        double sinThetaErrOverThetaErr = (Math.sin(thetaError) / thetaError);
        if (Math.abs(thetaError) < 0.00001)
        {
            sinThetaErrOverThetaErr = 1; //this is the limit as sin(x)/x approaches zero
        }
        else
        {
            sinThetaErrOverThetaErr = Math.sin(thetaError) / (thetaError);
        }
        return targetAngVel + Constants.kBeta * targetLinVel * sinThetaErrOverThetaErr * (Math.cos(odo.theta) * yError - Math.sin(odo.theta) * xError) + kGain* thetaError;
    }

    public Odometry startPos()
    {
        return new Odometry(sourceTrajectory.get(0).x, sourceTrajectory.get(0).y, sourceTrajectory.get(0).heading);
    }


    public double clampTheta(double theta)
    {

        return Pathfinder.d2r(Pathfinder.boundHalfDegrees(Pathfinder.r2d(theta)));
    }

    public boolean isFinished()
    {
        return pointNum == sourceTrajectory.length();
    }
}