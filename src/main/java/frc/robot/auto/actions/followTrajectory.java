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
import frc.robot.Drive;
import frc.robot.auto.util.Action;
import frc.robot.auto.util.GenerateTrajectory;
import frc.robot.auto.util.generateTrajectory;

public class followTrajectory implements Action {

    Drive drive;
    GenerateTrajectory generateTrajectory;

	RamseteCommand ramsete;

	int i = 0;
	
	public followTrajectory(String name, Drive drive) {
		this.drive = drive;        
		Trajectory trajectory = generateTrajectory.getFiveFeetForward();

		ramsete = new RamseteCommand(trajectory,
									 drive.getPose(),
									 new RamseteController(2.0, 0.7), 
									 feedforward, 
									 drive.getKinematics(), 
									 drive.getWheelSpeeds(), 
									 new PIDController(0,0,0), 
									 new PIDController(0, 0, 0), 
									 drive::driveVolts, 
									 drive);
	}

	@Override
	public boolean isFinished() {
		return leftFollower.isFinished() || rightFollower.isFinished();
	}
	
	@Override
	public void start() {
		
		drive.leftDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		drive.rightDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		drive.zeroGyro();
		
		leftFollower.configureEncoder(drive.leftDrive.getSelectedSensorPosition(0), (int) (1/Constants.DRIVE_ENCODER_CONVERSION), 1/Math.PI);
		rightFollower.configureEncoder(drive.rightDrive.getSelectedSensorPosition(0), (int) (1/Constants.DRIVE_ENCODER_CONVERSION), 1/Math.PI);
		leftFollower.configurePIDVA(.4, 0, 0, 1, 0);
		rightFollower.configurePIDVA(.4, 0, 0, 1, 0);
		followLoop.startPeriodic(.01);
		
		
	}

	@Override
	public void update() {
		SmartDashboard.putNumber("Left Error", drive.leftDrive.getClosedLoopError(0));
		SmartDashboard.putNumber("Right Error", drive.rightDrive.getClosedLoopError(0));
		//SmartDashboard.putNumber("leftPosition Error", leftFollower.getSegment().position - (drive.leftDrive.getSelectedSensorPosition(0) * Constants.DRIVE_ENCODER_CONVERSION));
	}

	@Override
	public void end() {
		followLoop.stop();
		drive.leftDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		drive.rightDrive.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		drive.zeroGyro();
		followLoop.stop();
		drive.leftDrive.set(ControlMode.PercentOutput, 0);
		drive.rightDrive.set(ControlMode.PercentOutput, 0);
		drive.leftDriveSlave.set(ControlMode.PercentOutput, 0);
		drive.rightDriveSlave.set(ControlMode.PercentOutput, 0);
		
	}

}
