package frc.robot;

import org.json.JSONObject;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;
import frc.team3647inputs.Joysticks;
import frc.team3647subsystems.Drivetrain;
import org.json.*;

import jssc.SerialPort.*;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Robot extends IterativeRobot 
{
	Joysticks stick;
	SerialPort cam;
	public static int middleX = 160;
	public static int middleY = 120;
	public static double XCenter = -1;
	public static double YCenter = -1;
	double rightSpeed;
	double leftSpeed;
	double speed;
	double prevError = 0;
	double sumError = 0;
  
  @Override
  public void robotInit() 
  {
	  stick = new Joysticks();
	  cam = new SerialPort(921600, SerialPort.Port.kUSB); //.kUSB# -------> # = nothing if cam0, 1 if cam1, 2 if cam2
	  Drivetrain.drivetrainInitialization(false);
  }


  public void parseCam()
	{
		
		String input = cam.readString();
        
		if(!input.contains("}"))
		{
			System.out.println("Data not fully received");
		}
		else
		{
    		try
    		{
				JSONObject obj = new JSONObject(input);    			
    			XCenter = obj.getInt("XCntr");
				YCenter = obj.getInt("YCntr");
				System.out.println("This is X Center " + XCenter);
    			System.out.println("This is Y Center " + YCenter);
			}
			catch(NullPointerException e)
			{
    			System.out.println(e);
			}
		}
        
	}

	

	public void visionPID(double videoFeedX) 
	{
		double leftVideoFeedX = videoFeedX;
		double rightVideoFeedX = 160 - videoFeedX;

		double kp = 0.0;
		double ki = 0.0;
		double kd = 0.0;

		double error = leftVideoFeedX - rightVideoFeedX;
		double diffError = error - prevError;
		
		sumError = sumError + error;
		double inputValue = kp * error + ki * sumError + kd * diffError;
		speed = .8*leftSpeed - inputValue/2;
		Drivetrain.FRCarcadedrive(speed, speed);
		prevError = error;
	}

	public void visionPID(double videoFeedX, double videoFeedY) 
	//not true PID yet. need to add kP,I,D constants to make it go truly correct
	//int videoFeedX and Y is the value from camera
	{
		if((videoFeedX > middleX))
		{
			Drivetrain.FRCarcadedrive(.25, -.25);
			
			// Motors007.leftTalon.set(.1);
			// Motors007.rightTalon.set(.05);
		}
		else if((videoFeedX < middleX))
		{

			Drivetrain.FRCarcadedrive(-.25, .25);
			// Motors007.leftTalon.set(.05);
			// Motors007.rightTalon.set(.1);
		}
		else if((videoFeedX == middleX))
		{
			Drivetrain.FRCarcadedrive(0, 0);
			// Motors007.leftTalon.set(0);
			// Motors007.rightTalon.set(0);
		}
		else
		{
			Drivetrain.FRCarcadedrive(0, 0);
			
			// Motors007.leftTalon.set(.1);
			// Motors007.rightTalon.set(.1);	
		}

	}

 
  @Override
  public void robotPeriodic() 
  {
  }

  
  @Override
  public void autonomousInit() 
  {
	  Drivetrain.FRCarcadedrive(0.5, 0.5);
  }	

  
  @Override
  public void autonomousPeriodic() 
  {
	  parseCam();
      visionPID(XCenter,YCenter);
  }

  
  

	
	

	

	// This is the function that runs during Tele-Operated Period
	public void teleopPeriodic() 
	{
		stick.setMainContollerValues();
		// stick.setCoDriverContollerValues();
		// System.out.println("It Has Begun");
		// parseCam();
		// visionPID(XCenter, YCenter);
		Drivetrain.FRCarcadedrive(stick.rightJoyStickx, stick.leftJoySticky);
		//visionPID(XCenter,YCenter);
	}

  
  @Override
  public void testPeriodic() 
  {

  }
}
