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
    static Waypoint[] waypoints = new Waypoint[]{
            new Waypoint(0 ,0,0),
            new Waypoint(4, 0, 0),
            new Waypoint(8, 4, Math.toRadians(90.0))
    };

    public static double spicyRandomness(double min, double max){
        return (Math.random() * (max-min)) + min;
    }

    public static void main(String[] args){
        follower = new RamseteFollower(traj);
        robotPos.add(follower.sourceTrajectory.get(0).x, follower.sourceTrajectory.get(0).y, follower.sourceTrajectory.get(0).heading);
        int odometryIdx = 0;
        DriveSignal driveSignal;
        while(!follower.isFinished()){
            Odometry current = robotPos.get(odometryIdx);
            follower.setOdometry(current);
            driveSignal = follower.getNextDriveSignal();
            double w = (-driveSignal.getLeft() + driveSignal.getRight()) / 2.166666;
            double v = (driveSignal.getLeft() + driveSignal.getRight()) / 2;
            //double dt = .02 * spicyRandomness(1.0, 1.0);
            double dt = 0.2;
            double heading = w * dt;
            double pos = v * dt;
            double x = pos * Math.cos(current.theta + heading);
            double y = pos * Math.sin(current.theta + heading);

            double newX = current.x + x;
            double newY = current.y + y;
            double newTheta = current.theta + heading;

            System.out.println("New X: " + newX + " New Y: " + newY + " New Heading: " + Math.toDegrees(newTheta));
            robotPos.add(current.setOdometry(newX, newY, newTheta));
            odometryIdx++;
        }

        System.out.println(System.getProperty("user.dir"));
        File trajLoc = new File(System.getProperty("user.dir") + "\\out\\path.csv");
        File robotLoc = new File(System.getProperty("user.dir") + "\\out\\robot.csv");
        try {
            PrintWriter pw = new PrintWriter(robotLoc);
            StringBuilder writer = new StringBuilder();
            writer.append("x,y,heading");
            writer.append("\n");
            for(Odometry odo: robotPos){
                writer.append(odo.getX() + "," + odo.getY() + "," + odo.getTheta() + "\n");
            }
            pw.write(writer.toString());
            pw.close();
        }catch(Exception e){
            System.out.println("ya done goofed");
        }
        Pathfinder.writeToCSV(trajLoc, traj);
    }
}
