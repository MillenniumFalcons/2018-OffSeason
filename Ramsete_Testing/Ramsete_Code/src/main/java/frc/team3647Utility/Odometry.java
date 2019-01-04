package frc.team3647Utility;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.Robot;
import frc.team3647Subsystems.Drivetrain;

public class Odometry
{
    //Robot (x,y) point and angle
    public double x, y, theta;

    private double currentPosition, deltaPosition, lastPosition;

    public void odometryInit(boolean backwards)
    {
        lastPosition = 0;
        Notifier odoThread = new Notifier(() ->                                     //create a notifier event
        {
            if(backwards == false)
            {
                currentPosition = (Robot.mDrivetrain.leftSRX.getSelectedSensorPosition(0) + Robot.mDrivetrain.leftSRX.getSelectedSensorPosition(0))/2;
                deltaPosition = Units.ticksToMeters(currentPosition - lastPosition);    //delta position calculated by difference in encoder ticks
                theta = Math.toRadians(Robot.mDrivetrain.getYaw());                     //Gyro angle in Radians
                x +=  Math.cos(theta) * deltaPosition;                                  //Getting x position from cosine of the change in position
                y +=  Math.sin(theta) * deltaPosition;                                  //Getting y position from sine of the change in position
                lastPosition = currentPosition;
            }
            else
            {
                currentPosition = (Robot.mDrivetrain.leftSRX.getSelectedSensorPosition(0) + Robot.mDrivetrain.leftSRX.getSelectedSensorPosition(0))/2;
                deltaPosition = Units.ticksToMeters(currentPosition - lastPosition);    //delta position calculated by difference in encoder ticks
                theta = Math.toRadians(Robot.mDrivetrain.getYaw());                     //Gyro angle in Radians
                x +=  Math.cos(theta) * deltaPosition;                                  //Getting x position from cosine of the change in position
                y +=  Math.sin(theta) * deltaPosition;                                  //Getting y position from sine of the change in position
                x *= -1;
                y *= -1;
                lastPosition = currentPosition;
            }
        });
        odoThread.startPeriodic(0.01);                                              //run the notifier event periodically requeued every .01 seconds
    }

    //manually set Odometry properties
    public void setOdometry(double xPos, double yPos, double thetaPos)
    {
        x = xPos;
        y = yPos;
        theta = thetaPos;
    }

    //reset odometry properties
    public void resetOdometry()
    {
        x = 0;
        y = 0;
        theta = 0;
    }

    //print current position of robot
    public void printPosition()
    {
        System.out.println("X: " + Units.metersToFeet(x) + " Y: " + Units.metersToFeet(y) + " Theta: " + Units.radiansToDegress(theta));
    }
}