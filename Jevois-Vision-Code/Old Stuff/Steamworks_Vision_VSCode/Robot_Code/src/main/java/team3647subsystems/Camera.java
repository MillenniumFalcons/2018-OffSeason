package team3647subsystems;
import edu.wpi.first.wpilibj.*;

public class Camera 
{
//config a serialport and specify baudrate and port on the Rio
    SerialPort cam = new SerialPort(921600, SerialPort.Port.kUSB); //.kUSB# -------> # = nothing if cam0, 1 if cam1, 2 if cam2
    
    public Camera()
    {
        System.out.println("Camera Initialized");
    }

    public void operatorControl()
    {
        try 
        {
            System.out.println("Data: ");
            System.out.println(cam.readString());
        } 
        catch (Exception e)
        {
            System.out.println("Error");
        }
        Timer.delay(.005);
    }
}