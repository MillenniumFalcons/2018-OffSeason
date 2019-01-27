//Class created by Kunal Singla

package frc.team3647inputs;

import frc.team3647subsystems.Drivetrain;                   //using Drivetrain class to move motors
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; //Smart dashboard to display values from limelight
import edu.wpi.first.networktables.*;

public class Limelight
{
    private double x, y, area, speed, sumError, prevError;
    //x is the tx degree value from Limelight Network Table
    //y is the ty degree value from Limelight Network Table
    //area is the ta value from Limelight Network Table. The ratio of the object area to the entire picture area, as a percent.
    //speed is a given, cmon
    //sumError is theglobal vairable to keep track of the sum of all the error values in the PID loop
    //prevError is the global variable to keep track of the previous error in the PID loop

    public NetworkTable table;  // NetworkTable is the class used to grab values from the Limelight Network Table


    public Limelight()  //used to initalize the main, important things
    {
        table = NetworkTableInstance.getDefault().getTable("limelight");    //initializing the network table to grab values from limelight
        updateLimelight();
    }

    public void updateLimelight()
    {

        NetworkTableEntry tx = table.getEntry("tx"); //setting the N.T. entry to the tx value from limelight N.T.
		NetworkTableEntry ty = table.getEntry("ty"); //setting the N.T. entry to the ty value from limelight N.T.
        NetworkTableEntry ta = table.getEntry("ta"); //setting the N.T. entry to the ta value from limelight N.T.
        
		x = tx.getDouble(0.0);      //x is set to tx, and setting the default value to 0 if not recieving values from limelight
		y = ty.getDouble(0.0);      //y is set to ty, and setting the default value to 0 if not recieving values from limelight
		area = ta.getDouble(0.0);   //area is set to ta, and setting the default value to 0 if not recieving values from limelight

		SmartDashboard.putNumber("LimelightX", x);          //adding the values to SmartDashboard
		SmartDashboard.putNumber("LimelightY", y);          //adding the values to SmartDashboard
		SmartDashboard.putNumber("LimelightArea", area);    //adding the values to SmartDashboard
    }

    // Drivebase Bot -> kP = .45, kI = 0.035, kD = .9
    public void center(double kp, double ki, double kd, double errorThreshold)  //method to center the robot to target without moving toward target
	{
		double error = this.x / 27; //error is x / 27. x is measured in degrees, where the max x is 27. We get a value from -1 to 1 to scale for speed output
		if(error > -errorThreshold && error < errorThreshold)   //checking if the error is within a threshold to stop the robot from moving
		{
			speed = 0;                              //setting global variable speed equal to zero
			Drivetrain.setSpeed(speed, speed);      //setting Drivetrain to 0 speed
		}
		else
		{            
			centerAlgorithm(error, kp, ki, kd);
		}
    }
    
    public void follow(double kp, double ki, double kd, double errorThreshold, double defaultSpeed, double targetArea)    //method for robot to follow a target while maintaing center point
	{
        double error = this.x / 27;                                 //error is x / 27. x is measured in degrees, where the max x is 27. We get a value from -1 to 1 to scale for speed output
        if(this.area >= targetArea-2)                               //redundant "if" in order to make sure the robot stops
        {
            Drivetrain.setSpeed(0,0);                               //set drivetrain to 0 speed if target distance is unreached
        }

		if( (error > -errorThreshold) && (error < errorThreshold) ) //if error is in between the threshold execute following statements
		{
            if(this.area < targetArea-2)
            {
                Drivetrain.setSpeed(defaultSpeed,defaultSpeed);     //set drivetrain to default speed if target distance is unreached
            }
            else
            {
                Drivetrain.setSpeed(0, 0);                          //set drivetrain to zero, stop robot if it has reached target distance
            }			
		}
		else
		{
            centerAlgorithm(error, kp, ki, kd);                     //use center algorithm to center the robot to target
        }
    }

    private void centerAlgorithm(double error, double kp, double ki, double kd)
    {
        double diffError = (error - prevError); // difference in the current error minus the (global variable) previous
                                                // error
        sumError = sumError + error;            // sum of the error (global variable) + the current error
        double output = (kp * error) + (ki * sumError) + (kd * diffError); // value calculated to output to the motor,
                                                                           // a.k.a. actual speed output
        speed = output;

        if (speed > 0 && speed < .25)       // Speed threshold if in between 0 and .25, set speed to .25
            speed = 0.25;
        else if (speed < 0 && speed > -.25) // Speed threshold if in between -.25 and 0, set speed to -.25
            speed = -0.25;
        else if (speed > 1)                 // Speed threshold if greater than 1, set speed to 1
            speed = 1;
        else if (speed < -1)                // Speed threshold if less than -1, set speed to -1
            speed = -1;

        prevError = error; // update prevError to current Error

        Drivetrain.setSpeed(speed, -speed); // set motor speeds to opposite adjusted PID values
    }

    public void bangBang(double speed, double threshold) //bang bang vision controller (simplest non-PID centering algorithm)
    {
        if (x > threshold && x < -threshold)    //if x is between threshold, drivetrain is set to zero speed
		{
			Drivetrain.setSpeed(0, 0);
		}
		else if (x > threshold)                 //if x is greater than threshold, then turn right
		{
			Drivetrain.setSpeed(speed, -speed);
		}
		else if(x < -threshold)                 //if x is less than -threshold, then turn left
		{
            Drivetrain.setSpeed(-speed, speed);
        }
        else                                    //if all else fails just stop the robot, redundant for safety
        {
            Drivetrain.setSpeed(0, 0);
        }
    }


    

    public double getX()            //get x, because x is private
    {
        return this.x;
    }

    public double getY()            //get y, because y is private
    {
        return this.y;
    }

    public double getArea()         //get area, because area is private
    {
        return this.area;
    }

    public double getSpeed()        //get speed, because speed is private
    {
        return this.speed;
    }

    public double getPrevError()    //get prevError, because prevError is private
    {
        return this.prevError;
    }

    public double getSumError()     //get sumError, because sumError is private
    {
        return this.sumError;
    }
}