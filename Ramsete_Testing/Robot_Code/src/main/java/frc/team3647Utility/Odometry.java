package frc.team3647Utility;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.Robot;
import frc.team3647Subsystems.Drivetrain;

public class Odometry
{
    public double x, y, theta;

    private double currentPosition, deltaPosition, lastPosition;

    public void odometryInit()
    {
        lastPosition = 0;
        Notifier odoThread = new Notifier(() ->{
            currentPosition = (Drivetrain.leftSRX.getSelectedSensorPosition(0) + Drivetrain.leftSRX.getSelectedSensorPosition(0))/2;
            deltaPosition = Units.ticksToMeters(currentPosition - lastPosition);
            theta = Math.toRadians(Robot.mDrivetrain.getYaw());
            x +=  Math.cos(theta) * deltaPosition;
            y +=  Math.sin(theta) * deltaPosition;
            lastPosition = currentPosition;
        });
        odoThread.startPeriodic(0.01);
    }

    public void setOdometry(double xPos, double yPos, double thetaPos)
    {
        x = xPos;
        y = yPos;
        theta = thetaPos;
    }

    public void printPosition()
    {
        System.out.println("X: " + Units.metersToFeet(x) + " Y: " + Units.metersToFeet(y) + " Theta: " + Units.radiansToDegress(theta));
    }
}