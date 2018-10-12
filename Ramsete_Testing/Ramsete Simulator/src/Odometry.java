
/** Wrapper class for robot pose estimation, giving the robot's x-y position and heading on the field
* The positive X-axis is defined as the robot going straight forward towards the center of the field.
* The positive Y-axis is defined as the robot moving left from its starting point against the back wall.
* Theta is within the range [-2pi, 2pi] and is defined to be counter-clockwise positive, following the Unit Circle.
 */
public class Odometry
{
    public double x, y, theta;

    private double currentPosition, deltaPosition, lastPosition;

//    public void odometryInit()
//    {
//        lastPosition = 0;
//        Notifier odoThread = new Notifier(() ->{
//            currentPosition = (Drivetrain.leftSRX.getSelectedSensorPosition(0) + Drivetrain.leftSRX.getSelectedSensorPosition(0))/2;
//            deltaPosition = Units.ticksToMeters(currentPosition - lastPosition);
//            theta = Math.toRadians(Robot.mDrivetrain.getYaw());
//            x +=  Math.cos(theta) * deltaPosition;
//            y +=  Math.sin(theta) * deltaPosition;
//            lastPosition = currentPosition;
//        });
//        odoThread.startPeriodic(0.01);
//    }
    public Odometry(double x, double y, double theta)
    {
        this.x = x;
        this.y = y;
        this.theta = theta;
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
