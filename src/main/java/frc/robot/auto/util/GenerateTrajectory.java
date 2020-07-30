package frc.robot.auto.util;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Constants;
import frc.robot.Drive;

public class GenerateTrajectory {

    Drive drive;
    Trajectory fiveFeetForward;
    TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(16), Units.feetToMeters(16))
                                .setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE));

    public GenerateTrajectory() {

        fiveFeetForward = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(1, 0),
                new Translation2d(2, 0)
            ),
            // End 5 meters straight ahead of where we started, facing forward
            new Pose2d(5, 0, new Rotation2d(0)),
            // Pass config
            config
        );

    }
        
    public Trajectory getFiveFeetForward(){
        DriverStation.reportWarning("generating Trajectory", false);
        return fiveFeetForward;
    }
}