import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    static RamseteFollower follower;
    static ArrayList<Odometry> robotPos = new ArrayList<>();

    public static double spicyRandomness(double min, double max){
        return (Math.random() * (max-min)) + min;
    }

    public static void main(String[] args)
    {
        follower = new RamseteFollower("LeftToCloseScale0");
        robotPos.add(new Odometry(1,6.8, 0.1));//new Odometry(1.2, 4, 5.8) //new Odometry(1,7.3, 0)
        int odometryIdx = 0;

        while(!follower.isFinished())
        {
            Odometry current = robotPos.get(odometryIdx);
            follower.setOdometry(current);
            follower.runPath();
            double w = follower.angVel;
            double v = follower.linVel;
            double dt = .02;
            double heading = w * dt;
            double pos = v * dt;
            double x = pos * Math.cos(current.theta + heading);
            double y = pos * Math.sin(current.theta + heading);

            double newX = current.x + x;
            double newY = current.y + y;
            double newTheta = current.theta + heading;

            System.out.println("New X: " + newX + " New Y: " + newY + " New Heading: " + Math.toDegrees(newTheta));
            robotPos.add(new Odometry(newX, newY, newTheta));
            odometryIdx++;
        }

        System.out.println(System.getProperty("user.dir"));
        File trajLoc = new File(System.getProperty("user.dir") + "\\out\\path.csv");
        File robotLoc = new File(System.getProperty("user.dir") + "\\out\\robot.csv");
        try
        {
            PrintWriter pw = new PrintWriter(robotLoc);
            StringBuilder writer = new StringBuilder();
            writer.append("x,y,heading");
            writer.append("\n");
            for(Odometry odo: robotPos)
            {
                writer.append(odo.x + "," + odo.y + "," + odo.theta + "\n");
            }
            pw.write(writer.toString());
            pw.close();
        }
        catch(Exception e)
        {
            System.out.println("ya done goofed");
        }
        Pathfinder.writeToCSV(trajLoc, follower.sourceTrajectory);
    }
}
