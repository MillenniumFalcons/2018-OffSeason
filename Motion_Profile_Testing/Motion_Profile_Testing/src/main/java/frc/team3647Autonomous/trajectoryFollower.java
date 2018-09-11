package frc.team3647Autonomous;


import jaci.pathfinder.*;
import jaci.pathfinder.followers.EncoderFollower;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647Subsystems.Drivetrain;
import frc.team3647Subsystems.Encoders;
import edu.wpi.first.wpilibj.*;
import java.io.File;

public class trajectoryFollower
{
    Trajectory leftTrajectory, rightTrajectory;
    public static boolean pathFinished = false;

    public void runPath(Encoders enc)
    {

        EncoderFollower right = new EncoderFollower(leftTrajectory);
        EncoderFollower left = new EncoderFollower(rightTrajectory);

        enc.setEncoderValues();
        right.configureEncoder(enc.rightEncoderValue, 1440, Constants.wheelDiameter);
        left.configureEncoder(enc.leftEncoderValue, 1440, Constants.wheelDiameter);

        //set PID values
        right.configurePIDVA(Constants.rPFkP, Constants.rPFkI, Constants.rPFkD, Constants.rPFkV, Constants.rPFkA);
        left.configurePIDVA(Constants.lPFkP, Constants.lPFkI, Constants.lPFkD, Constants.lPFkV, Constants.lPFkA);

        double rValue = right.calculate(enc.rightEncoderValue);
        double lValue = left.calculate(enc.leftEncoderValue);
        //set output
        Drivetrain.setPercentOutput(lValue, rValue);

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
        rightTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_right.csv"));
        leftTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_left.csv"));
    }
}