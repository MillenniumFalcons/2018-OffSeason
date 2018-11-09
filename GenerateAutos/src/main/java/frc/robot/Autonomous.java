package frc.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
    public static Timer stopWatch = new Timer();
    public static int currentState, autoState, runAuto, RhighValue, LhighValue;
    public static double time;
	static double currTime, prevTime;

    static double prevLeftEncoder, prevRightEncoder;
	static double newLenc, newRenc, oldLenc, oldRenc;

    public static void initialize(Encoders enc, NavX navX)
    {
        enc.resetEncoders();
        navX.resetAngle();
        Drivetrain.setToBrake();
        Drivetrain.stop();
		currentState = 0;
		autoState = 0;
		enc.prevLEncoder = 0;
		enc.prevREncoder = 0;
    }
}