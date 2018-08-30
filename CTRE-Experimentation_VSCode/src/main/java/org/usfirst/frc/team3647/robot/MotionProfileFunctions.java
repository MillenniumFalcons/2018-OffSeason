package org.usfirst.frc.team3647.robot;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Talon;

public class MotionProfileFunctions
{
    private TalonSRX masterTalon;
    private SetValueMotionProfile mpSetValue = SetValueMotionProfile.Disable;
    private MotionProfileStatus mpStatus = new MotionProfileStatus();
    int mpState = 0;
    int minPoints = 5;
    boolean startMP = false;
    boolean finishedPath = false;

    class PeriodicRunnable implements Runnable 
    {
        public void run()
        {
            masterTalon.processMotionProfileBuffer();
        }
    }
    Notifier notifier = new Notifier(new PeriodicRunnable());

    public MotionProfileFunctions(TalonSRX talon)
    {
        masterTalon = talon;
        masterTalon.changeMotionControlFramePeriod(5);
        notifier.startPeriodic(0.005);
    }

    public void runTalon(String csvFile)
    {
        int state = 0;
        ReadMotionProfileData profileData = new ReadMotionProfileData(csvFile);
        SetValueMotionProfile setOutput = mpSetValue;
        while(!finishedPath)
        {
            switch(state)
            {
                case 0:
                finishedPath = false;
                profileData.parseCSVFile(csvFile);
                sendMPPoints(profileData.data, profileData.lineCount);
                state = 1;
                case 1:
                if(mpStatus.btmBufferCnt > minPoints)
                {
                    mpSetValue = SetValueMotionProfile.Enable;
                    masterTalon.set(ControlMode.MotionProfile, setOutput.value);
                    state = 2;
                }
                case 2:
                if(mpStatus.activePointValid && mpStatus.isLast)
                {
                    finishedPath = true;
                    mpSetValue = SetValueMotionProfile.Hold;
                }
                break;
            }
        }
    }

    public void reset()
    {
        masterTalon.clearMotionProfileTrajectories();
        mpSetValue = SetValueMotionProfile.Disable;
        mpState = 0;
        startMP = false;
    }

    private TrajectoryDuration getTrajectoryDuration(int duration)
    {
        TrajectoryDuration returnDuration = TrajectoryDuration.Trajectory_Duration_0ms;

        returnDuration = returnDuration.valueOf(duration);

        if(returnDuration.value != duration)
        {
            DriverStation.reportError("Trajectory Duration BORKEN MAN FIX YO PATH", false);
        }

        return returnDuration;
    }

    public void sendMPPoints(double[][] data, int numPoints)
    {
        TrajectoryPoint point = new TrajectoryPoint();

        masterTalon.clearMotionProfileTrajectories();
        masterTalon.configMotionProfileTrajectoryPeriod(0, 0);

        for (int i = 0;  i < numPoints; ++i)
        {
            double position = data[i][0];
            double velocity = data[i][1];

            point.position = position;
            point.velocity = velocity;
            point.profileSlotSelect0 = 0;
            point.timeDur = getTrajectoryDuration((int)data[i][2]);
            point.zeroPos = false;
            if (i == 0)
            {
                point.zeroPos = true;
            }
            point.isLastPoint = false;
            if((i + 1) == numPoints)
            {
                point.isLastPoint = false;
            }

            masterTalon.pushMotionProfileTrajectory(point);
        }
    }
}