package frc.robot.auto.util.cheesypath.lib.trajectory;


import frc.robot.auto.util.cheesypath.lib.geometry.Pose2d;
import frc.robot.auto.util.cheesypath.lib.geometry.Twist2d;

public interface IPathFollower {
    public Twist2d steer(Pose2d pose2d);

    public boolean isDone();
}
