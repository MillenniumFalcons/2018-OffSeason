package frc.team3647Autonomous;


import jaci.pathfinder.*;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.*;
import java.io.File;

public class TrajectoryFollower
{
    Trajectory leftTrajectory, rightTrajectory;
    Trajectory adjustedLeftTrajectory, adjustedRightTrajectory;
    Trajectory preReverseR, preReverseL;
    

    EncoderFollower right = new EncoderFollower();
    EncoderFollower left = new EncoderFollower();
    int adjustedREncoder, adjustedlEncoder;
    boolean finalReverse;
    double angleAdjustment;
    
    public void runPath(int lEncoder, int rEncoder, double navXAngle)
    {
        if(!finalReverse)
        {
            adjustedREncoder = rEncoder;
            adjustedlEncoder = lEncoder;
            angleAdjustment= 0;
        }
        else
        {
            adjustedlEncoder = -rEncoder;
            adjustedREncoder = -lEncoder;
            angleAdjustment = 180;
        }
        
        //set follower values
        double rValue = right.calculate(adjustedREncoder);
        double lValue = left.calculate(adjustedlEncoder);
            
        //navX gyro code
        double gyroHeading = -1*navXAngle + angleAdjustment; //invert since RHR
        double desiredHeading = Pathfinder.r2d(right.getHeading());
        double headingDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = 0.8 * (-1.0/80.0) * headingDifference;
        double rPower = rValue + turn;
        double lPower = lValue - turn;

        if(finalReverse)
        {
            Drivetrain.setPercentOutput(-rPower, -lPower); //with gyro
        }
        else
        {
            Drivetrain.setPercentOutput(lPower, rPower);
        }
        //set output
        //Drivetrain.setPercentOutput(lValue, rValue); //no gyro
        //Drivetrain.setPercentOutput(lPower, rPower);

        SmartDashboard.putNumber("target left speed", lValue);
        SmartDashboard.putNumber("target right speed", rValue);
        SmartDashboard.putNumber("left encoder value", lEncoder);
        SmartDashboard.putNumber("right encoder value", rEncoder);
    }

    public void followPath(String path, boolean backward, boolean reverse)
    {
        if(backward)
        {
            rightTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_left_Jaci.csv"));
            leftTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_right_Jaci.csv"));
        }
        else
        {
            rightTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_right_Jaci.csv"));
            leftTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_left_Jaci.csv"));
        }

        finalReverse = backward;
        
        if(reverse)
        {
            right.setTrajectory(reverseTrajectory2(rightTrajectory));
            left.setTrajectory(reverseTrajectory2(leftTrajectory));
        }
        else
        {
            right.setTrajectory(rightTrajectory);
            left.setTrajectory(leftTrajectory);
        }
    }

    public void followPath(Trajectory trajPoints, boolean backward, boolean reverse)
    {
        // Trajectory.Config configPoints = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, Constants.MPTimeStep, maxVelocity, maxAcceleration, Constants.maxJerk);
        // Trajectory trajPoints = Pathfinder.generate(points, configPoints);

        TankModifier tankModifier = new TankModifier(trajPoints);
        tankModifier.modify(Constants.wheelBase);

        if(backward)
        {
            leftTrajectory = tankModifier.getRightTrajectory();
            rightTrajectory = tankModifier.getLeftTrajectory();
        }
        else
        {
            leftTrajectory = tankModifier.getLeftTrajectory();
            rightTrajectory = tankModifier.getRightTrajectory();
        }

        finalReverse = backward;

        if(reverse)
        {
            right.setTrajectory(reverseTrajectory(rightTrajectory));
            left.setTrajectory(reverseTrajectory(leftTrajectory));
        }
        else
        {
            right.setTrajectory(rightTrajectory);
            left.setTrajectory(leftTrajectory);
        }
    }

    public void initialize()
    {
        right.configureEncoder(0, 1440, Constants.wheelDiameter);//first arg set to 0 since encoders reset just before
        left.configureEncoder(0, 1440, Constants.wheelDiameter);

        //set PID values
        right.configurePIDVA(Constants.rPFkP, Constants.rPFkI, Constants.rPFkD, Constants.rPFkV, Constants.rPFkA);
        left.configurePIDVA(Constants.lPFkP, Constants.lPFkI, Constants.lPFkD, Constants.lPFkV, Constants.lPFkA);
    }

    public boolean isFinished()
    {
        return left.isFinished() && right.isFinished();
    }
    
    public Trajectory reverseTrajectory(Trajectory trajectory)
    {

        Collections.reverse(Arrays.asList(trajectory));
        return trajectory;
    }

    public Trajectory reverseTrajectory2(Trajectory trajectory)
    {
        // System.out.println("***************** COPYING TRAJECTORY ***********************");
        Trajectory newTrajectory  = trajectory.copy(); //MAKING COPY OF ARRRAY ARR

        // System.out.println("***************** INITIAL ARRAY ***********************");
        // for (Segment index : copy.segments) 
        // {
        //     System.out.println("Position: " + index.position);
        //     System.out.println("Heading: " + index.heading);
        // }
        // System.out.println("***************** END OF INTITAL ARRAY ***********************");        
        
        Double distance = trajectory.segments[ newTrajectory.segments.length - 2 ].position;
        // System.out.println("*************************DISTANCE IS " + distance + "*************************");

        // System.out.println("***************** REVERSING ARRAY *************************");
        Collections.reverse(Arrays.asList(newTrajectory.segments));
        // System.out.println("***************** REVERSED ARRAY **************************");
        System.out.println("Position: " + newTrajectory.segments[0].position);
        System.out.println("Velocity: " + newTrajectory.segments[0].velocity);
        // for (Segment index : newTrajectory.segments)
        // {
        //     index.position = distance - index.position;
        //     System.out.println("Position: " + index.position);
        //     System.out.println("Heading: " + index.heading);
        // }
        // System.out.println("***************** END REVERSED ARRAY ***********************");




        for(int i = 0; i < newTrajectory.segments.length; i++)
        {
            newTrajectory.segments[i].heading = trajectory.segments[i].heading;
            newTrajectory.segments[i].velocity *= -1;
        }



        // for (Segment var : copy.segments)
        // {
        //     var.position = distance - var.position;
        // }
        
        return newTrajectory;
    }
}


// public void runPath(int lEncoder, int rEncoder, double navXAngle, boolean reverse)
// {
//     //set follower values
//         double rValue = right.calculate(rEncoder);
//         double lValue = left.calculate(lEncoder);
        
//     //navX gyro code
//     double gyroHeading = -navXAngle; //invert since RHR
//     double desiredHeading = Pathfinder.r2d(right.getHeading());
//     double headingDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
//     double turn = 0.8 * (-1.0/80.0) * headingDifference;
//     double rPower = rValue + turn;
//     double lPower = lValue - turn;

//     //set output
//     //Drivetrain.setPercentOutput(lValue, rValue); //no gyro
//     Drivetrain.setPercentOutput(lPower, rPower); //with gyro

//     SmartDashboard.putNumber("target left speed", lValue);
//     SmartDashboard.putNumber("target right speed", rValue);
//     SmartDashboard.putNumber("left encoder value", lEncoder);
//     SmartDashboard.putNumber("right encoder value", rEncoder);
// }