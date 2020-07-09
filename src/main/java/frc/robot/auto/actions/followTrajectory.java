package frc.robot.auto.actions;

import java.io.File;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
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
	
	int i = 0;
	
	public followTrajectory(final String name, final Drive drive) {
		this.drive = drive;        
		trajectory = generateTrajectory.getFiveFeetForward();

		
	}

	@Override
	public boolean isFinished() {
		return false;
	}
	
	@Override
	public void start() {
		
		ramsete = new RamseteCommand(generateTrajectory.getFiveFeetForward(),
									 drive::getPose,
									 new RamseteController(2.0, 0.7), 
									 new SimpleMotorFeedforward(.24, 1.83, .36), 
									 drive.getKinematics(), 
									 drive.getWheelSpeeds(), 
									 new PIDController(0, 0, 0), 
									 new PIDController(0, 0, 0),
									 drive::setOutput,
									 drive);
		
		
	}

	@Override
	public void update() {
		SmartDashboard.putNumber("Left Error", drive.leftDrive.getClosedLoopError(0));
		SmartDashboard.putNumber("Right Error", drive.rightDrive.getClosedLoopError(0));
		//SmartDashboard.putNumber("leftPosition Error", leftFollower.getSegment().position - (drive.leftDrive.getSelectedSensorPosition(0) * Constants.DRIVE_ENCODER_CONVERSION));
	}

	@Override
	public void end() {
		drive.leftDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		drive.rightDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		drive.zeroGyro();
		drive.leftDrive.set(ControlMode.PercentOutput, 0);
		drive.rightDrive.set(ControlMode.PercentOutput, 0);
		drive.leftDriveSlave.set(ControlMode.PercentOutput, 0);
		drive.rightDriveSlave.set(ControlMode.PercentOutput, 0);
		
	}

}
