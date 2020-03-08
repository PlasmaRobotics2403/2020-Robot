package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.auto.util.cheesypath.lib.geometry.Pose2dWithCurvature;
import frc.robot.auto.util.cheesypath.lib.trajectory.TimedView;
import frc.robot.auto.util.cheesypath.lib.trajectory.Trajectory;
import frc.robot.auto.util.cheesypath.lib.trajectory.TrajectoryIterator;
import frc.robot.auto.util.cheesypath.lib.trajectory.timing.TimedState;
import frc.robot.Drive;

public class FollowTrajectory implements Action {

    Drive drive;
    TrajectoryIterator<TimedState<Pose2dWithCurvature>> trajectoryIterator;
    boolean resetPosition;

    public FollowTrajectory(Trajectory<TimedState<Pose2dWithCurvature>> trajectory) {
        this(trajectory, false);
    }

    public FollowTrajectory(Trajectory<TimedState<Pose2dWithCurvature>> trajectory, boolean resetPose) {
        trajectoryIterator = new TrajectoryIterator<>(new TimedView<>(trajectory));
        resetPosition = resetPose;
    }


    @Override
    public boolean isFinished() {
        if (drive.isDoneWithTrajectory()) {
            System.out.println("Trajectory finished");
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        System.out.println("Starting trajectory! (length=" + trajectoryIterator.getRemainingProgress() + ")");
        if (resetPosition) {
            //mRobotState.reset(Timer.getFPGATimestamp(), mTrajectory.getState().state().getPose());
        }
        drive.setTrajectory(trajectoryIterator);

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void end() {
        // TODO Auto-generated method stub

    }

}