package frc.robot;

import org.json.JSONObject;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;
import frc.team3647subsystems.Drivetrain;
import org.json.*;

import jssc.SerialPort.*;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Robot extends IterativeRobot 
{
  
  @Override
  public void robotInit() 
  {

  }

 
  @Override
  public void robotPeriodic() 
  {
  }

  
  @Override
  public void autonomousInit() 
  {
    
  }

  
  @Override
  public void autonomousPeriodic() 
  {
    
  }

  
  SerialPort cam = new SerialPort(921600, SerialPort.Port.kUSB); //.kUSB# -------> # = nothing if cam0, 1 if cam1, 2 if cam2

	public static int middleX = 160;
	public static int middleY = 120;
	public static double XCenter = -1;
	public static double YCenter = -1;
	

	public void parseCam()
	{
		String input = cam.readString();
		if(!input.contains("}"))
		{
			System.out.println("Data not fully received");
		}
		else
		{
			System.out.println(input);
        	JSONObject obj = new JSONObject(input);
    		try
    		{    			
    			XCenter = obj.getInt("XCntr");
				YCenter = obj.getInt("YCntr");
				System.out.println("This is X Center" + XCenter);
    			System.out.println("This is Y Center" + YCenter);
			}
			catch(NullPointerException e)
			{
    			System.out.println(e);
			}
		}
        
	}

	public void visionPID(double videoFeedX, double videoFeedY) 
	//not true PID yet. need to add kP,I,D constants to make it go truly correct
	//int videoFeedX and Y is the value from camera
	{
		if((videoFeedX > middleX) && (videoFeedY != middleY))
		{
			Drivetrain.leftSRX.set(.1);
			Drivetrain.rightSRX.set(.05);
			// Motors007.leftTalon.set(.1);
			// Motors007.rightTalon.set(.05);
		}
		else if((videoFeedX < middleX) && (videoFeedY != middleY))
		{
			Drivetrain.leftSRX.set(.05);
			Drivetrain.rightSRX.set(.1);
			// Motors007.leftTalon.set(.05);
			// Motors007.rightTalon.set(.1);
		}
		else if((videoFeedX == middleX) && (videoFeedY == middleY))
		{
			Drivetrain.leftSRX.set(0);
			Drivetrain.rightSRX.set(0);
			// Motors007.leftTalon.set(0);
			// Motors007.rightTalon.set(0);
		}
		else
		{
			Drivetrain.leftSRX.set(.05);
			Drivetrain.rightSRX.set(.05);
			// Motors007.leftTalon.set(.1);
			// Motors007.rightTalon.set(.1);	
		}

	}

	// This is the function that runs during Tele-Operated Period
	public void teleopPeriodic() 
	{
		System.out.println("It Has Begun");
		parseCam();
		//visionPID(XCenter,YCenter);
	}

  
  @Override
  public void testPeriodic() 
  {

  }
}
