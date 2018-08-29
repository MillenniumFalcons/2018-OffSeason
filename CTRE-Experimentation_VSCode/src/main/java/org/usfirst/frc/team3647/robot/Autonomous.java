package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Autonomous
{
    int point;
    boolean startMotionProfile = false;

    public static void autonomousIntialization()
    {
       Drivetrain.rightSRX.pushMotionProfileTrajectory(point);
    }

    public void reset()
    {
        Drivetrain.rightSRX.clearMotionProfileTrajectories();
        startMotionProfile = false;
    }
}