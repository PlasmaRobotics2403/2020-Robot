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
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.Drive;
import frc.robot.Intake;
import frc.robot.auto.util.Action;
import frc.robot.auto.util.GenerateTrajectory;

public class followTrajectory implements Action {

	Drive drive;
	Intake intake;
    GenerateTrajectory generateTrajectory;

	RamseteCommand ramsete;
	Trajectory trajectory0;
	Trajectory trajectory1;
	Trajectory trajectory2;
	Trajectory trajectory3;
	Trajectory trajectory4;
	Trajectory trajectory5;
	Trajectory trajectory6;
	Trajectory trajectory7;
	Trajectory trajectory8;
	Trajectory trajectory9;
	Trajectory trajectory10;
	Trajectory trajectory11;
	TrajectoryConfig config0;
	TrajectoryConfig config1;
	TrajectoryConfig config2;
	TrajectoryConfig config3;
	TrajectoryConfig config4;
	TrajectoryConfig config5;
	TrajectoryConfig config6;
	TrajectoryConfig config7;
	TrajectoryConfig config8;
	TrajectoryConfig config9;
	TrajectoryConfig config10;
	TrajectoryConfig config11;

	Trajectory[] trajectoryArray;
	int trajectoryNumber;
	
	int i = 0;
	
	public followTrajectory(int trajectoryNumber, final Drive drive, final Intake intake) {
		this.trajectoryNumber = trajectoryNumber;
		this.drive = drive;  
		this.intake = intake; 
		DriverStation.reportWarning("getting trajectory", false);
		config0 = new TrajectoryConfig(2.5, 1.75)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11));
		trajectory0 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(2, 1.2)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3.5, 1.2, new Rotation2d(0)),
            // Pass config
            config0
		);
		
		config1 = new TrajectoryConfig(1.5, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11));
		trajectory1 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
				new Translation2d(2, 1.2)
            ),
            // End 3 meters straight ahead of where we started, facing forward
			new Pose2d(6.3, 1.2, new Rotation2d(0)),
            // Pass config
            config1
		);
		
		config2 = new TrajectoryConfig(3, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11))
								.setReversed(true);
		trajectory2 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
				new Translation2d(-2.5, 0)
            ),
            // End 3 meters straight ahead of where we started, facing forward
			new Pose2d(-5.5, -1.2, new Rotation2d(0)),
            // Pass config
            config2
		);
		
		config3 = new TrajectoryConfig(2.5, 1.75)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11))
								.setReversed(true);
		trajectory3 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
				new Translation2d(-2, -1.2)
            ),
            // End 3 meters straight ahead of where we started, facing forward
			new Pose2d(-3.5, -1.2, new Rotation2d(0)),
            // Pass config
            config3
		);
		
		config4 = new TrajectoryConfig(2.5, 1.75)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11));
		trajectory4 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(2, 1.2)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(4.6, 1.2, new Rotation2d(0)),
            // Pass config
            config4
		);

		config5 = new TrajectoryConfig(2.5, 1.75)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11))
								.setReversed(true);
		trajectory5 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
				new Translation2d(-2.25, -0.6)
            ),
            // End 3 meters straight ahead of where we started, facing forward
			new Pose2d(-4.5, -1.2, new Rotation2d(0)),
            // Pass config
            config5
		);

		config6 = new TrajectoryConfig(1.5, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11));
		trajectory6 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(2, 1.2)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(4.5, 1.2, new Rotation2d(0)),
            // Pass config
            config6
		);

		config7 = new TrajectoryConfig(1.5, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11));
		trajectory7 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(1, 1.2) //1.3, 1.8
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(2.5, 1.7, new Rotation2d(Math.toRadians(20))),
            // Pass config
            config7
		);

		config8 = new TrajectoryConfig(1.5, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11));
		trajectory8 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(1.4, 0) //1.3, 1.8
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(1.9, -0.25, new Rotation2d(Math.toRadians(-62))),
            // Pass config
            config8
		);

		config9 = new TrajectoryConfig(1.5, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11))
								.setReversed(true);
		trajectory9 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(Math.toRadians(0))),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(-1, 0) //1.3, 1.8
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(-1.7, 0, new Rotation2d(Math.toRadians(62))),
            // Pass config
            config9
		);

		config10 = new TrajectoryConfig(1.5, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11));
								
		trajectory10 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(Math.toRadians(0))),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(2.3, 0) //1.3, 1.8
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(4.6, 0, new Rotation2d(Math.toRadians(0))),
            // Pass config
            config10
		);

		config11 = new TrajectoryConfig(3, 1.5)
								.setKinematics(new DifferentialDriveKinematics(Constants.WHEEL_BASE))
								.addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(.277, 1.78, .275), new DifferentialDriveKinematics(Constants.WHEEL_BASE), 11))
								.setReversed(true);
		trajectory11 = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(Math.toRadians(0))),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(-3.5, 0)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(-5.5, -1, new Rotation2d(Math.toRadians(0))),
            // Pass config
            config11
		);
		
		trajectoryArray = new Trajectory[20];
		trajectoryArray[0] = trajectory0;
		trajectoryArray[1] = trajectory1;
		trajectoryArray[2] = trajectory2;
		trajectoryArray[3] = trajectory3;
		trajectoryArray[4] = trajectory4;
		trajectoryArray[5] = trajectory5;
		trajectoryArray[6] = trajectory6;
		trajectoryArray[7] = trajectory7;
		trajectoryArray[8] = trajectory8;
		trajectoryArray[9] = trajectory9;
		trajectoryArray[10] = trajectory10;
		trajectoryArray[11] = trajectory11;
		DriverStation.reportWarning("got Trajectory", false);
	}

	@Override
	public boolean isFinished() {
		return ramsete.isFinished();
	}
	
	@Override
	public void start() {
		DriverStation.reportWarning("before ramsete", false);
		ramsete = new RamseteCommand(trajectoryArray[trajectoryNumber],
									 drive::getPose,
									 new RamseteController(), 
									 new SimpleMotorFeedforward(.277, 1.78, .275), //.257, 1.82, .274
									 new DifferentialDriveKinematics(Constants.WHEEL_BASE), 
									 drive::getWheelSpeeds, 
									 new PIDController(3, 0.0, 0.0), 
									 new PIDController(3, 0.0, 0.0),
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
		drive.resetOdometry();
	}

}
