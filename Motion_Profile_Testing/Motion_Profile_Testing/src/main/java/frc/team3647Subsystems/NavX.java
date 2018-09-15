package frc.team3647Subsystems;

import frc.team3647ConstantsAndFunctions.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;

public class NavX
{
    AHRS AHRSnavX = new AHRS(SPI.Port.kMXP);

    public double yaw, pitch, roll;

    public void setAngle()
    {
        yaw = AHRSnavX.getYaw();
        pitch = AHRSnavX.getPitch();
        roll = AHRSnavX.getRoll();
    }

    public void resetAngle()
    {
        AHRSnavX.zeroYaw();
    }

    public void testAngle()
    {
        System.out.println("Yaw: " + -yaw + " Pitch: " + pitch + " Roll: " + roll);
    }
}