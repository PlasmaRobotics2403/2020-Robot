package frc.robot.auto.actions;

import java.io.File;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.Drive;
import frc.robot.auto.util.Action;
import frc.robot.auto.util.GenerateTrajectory;

public class followTrajectory implements Action {

    Drive drive;
    GenerateTrajectory generateTrajectory;

	RamseteCommand ramsete;
	Trajectory trajectory;
	TrajectoryConfig config;
	
	int i = 0;
	
	public followTrajectory(final String name, final Drive drive) {
		this.drive = drive;   
		DriverStation.reportWarning("getting trajectory", false);
		config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2))
                                .setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE));
		trajectory = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(2, 0),
                new Translation2d(3, 0)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(5, 0, new Rotation2d(0)),
            // Pass config
            config
        );
		DriverStation.reportWarning("got Trajectory", false);
		
	}

	@Override
	public boolean isFinished() {
		return ramsete.isFinished();
	}
	
	@Override
	public void start() {
		DriverStation.reportWarning("before ramsete", false);
		ramsete = new RamseteCommand(trajectory,
									 drive::getPose,
									 new RamseteController(2.0, 0.7), 
									 new SimpleMotorFeedforward(.257, 1.82, .274), //.24, 1.83, .36
									 new DifferentialDriveKinematics(Constants.WHEEL_BASE), 
									 drive::getWheelSpeeds, 
									 new PIDController(0.000382, 0.0, 0.000179), 
									 new PIDController(0.000382, 0.0, 0.000179),
									 drive::setOutput,
									 drive);
		
		ramsete.initialize();
		DriverStation.reportWarning("finished creating ramsete", false);
	}

	@Override
	public void update() {
		SmartDashboard.putNumber("Left Error", drive.leftDrive.getClosedLoopError(0));
		SmartDashboard.putNumber("Right Error", drive.rightDrive.getClosedLoopError(0));
		//SmartDashboard.putNumber("leftPosition Error", leftFollower.getSegment().position - (drive.leftDrive.getSelectedSensorPosition(0) * Constants.DRIVE_ENCODER_CONVERSION));
		ramsete.execute(); 
		DriverStation.reportWarning("updated", false);
	}

	@Override
	public void end() {
		//drive.leftDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		//drive.rightDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		drive.zeroGyro();
		drive.leftDrive.set(ControlMode.PercentOutput, 0);
		drive.rightDrive.set(ControlMode.PercentOutput, 0);
		drive.leftDriveSlave.set(ControlMode.PercentOutput, 0);
		drive.rightDriveSlave.set(ControlMode.PercentOutput, 0);
		
	}

}
