package frc.robot.auto.util;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

public class GenerateTrajectory {

    ArrayList<Pose2d> fiveFeetForward;
    TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(16), Units.feetToMeters(16));

    public GenerateTrajectory() {

        fiveFeetForward.add(new Pose2d(Units.feetToMeters(0), Units.feetToMeters(0), Rotation2d.fromDegrees(0)));
        fiveFeetForward.add(new Pose2d(Units.feetToMeters(5), Units.feetToMeters(0), Rotation2d.fromDegrees(0)));

    }
        
    public Trajectory getFiveFeetForward(){
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(fiveFeetForward, config);
        return trajectory;
    }
}