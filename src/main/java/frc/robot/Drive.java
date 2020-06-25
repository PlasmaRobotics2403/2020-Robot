/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.controllers.PlasmaAxis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

// Wiplib 2020 says to use edu.wpi.first.wpilibj2
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//import edu.wpi.first.wpilibj.command.Command;

public class Drive {

    public TalonFX leftDrive;
    public TalonFX leftDriveSlave;
    public TalonFX rightDrive;
    public TalonFX rightDriveSlave;

    private final AHRS navX;
    private double gyroAngle;
    private double gyroPitch;

    public Drive(final int leftDriveID, final int leftDriveSlaveID, final int rightDriveID, final int rightDriveSlaveID){
      leftDrive = new TalonFX(leftDriveID);
      leftDriveSlave = new TalonFX(leftDriveSlaveID);
      rightDrive = new TalonFX(rightDriveID);
      rightDriveSlave = new TalonFX(rightDriveSlaveID);

      navX = new AHRS(SPI.Port.kMXP);

      


      leftDrive.configFactoryDefault();
      rightDrive.configFactoryDefault();
      leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);
		  rightDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);

      leftDrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
		  leftDrive.setSensorPhase(true);
		  leftDrive.configClosedloopRamp(0);
		  leftDrive.configNominalOutputForward(0, 30);
		  leftDrive.configNominalOutputReverse(0, 30);
		  leftDrive.configPeakOutputForward(1, 30);
		  leftDrive.configPeakOutputReverse(-1, 30);
		  leftDrive.config_kF(0, .35, 30); // should get close to distance defined with everything else zero
		  leftDrive.config_kP(0, 1.2, 30); // occilate around error
		  leftDrive.config_kI(0, 0.005, 30);
		  leftDrive.config_kD(0, 25, 30);
      leftDrive.config_IntegralZone(0, 0, 30);
      

		  rightDrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
		  rightDrive.setSensorPhase(true);
		  rightDrive.configClosedloopRamp(0);
		  rightDrive.configNominalOutputForward(0, 30);
		  rightDrive.configNominalOutputReverse(0, 30);
		  rightDrive.configPeakOutputForward(1, 30);
		  rightDrive.configPeakOutputReverse(-1, 30);
		  rightDrive.config_kF(0, .35, 30);
		  rightDrive.config_kP(0, 1.2, 30);
		  rightDrive.config_kI(0, 0.005, 30);
		  rightDrive.config_kD(0, 25, 30);
		  rightDrive.config_IntegralZone(0, 0, 30);
      
      leftDrive.setSelectedSensorPosition(0, 0, 0);
		  rightDrive.setSelectedSensorPosition(0, 0, 0);

		  leftDrive.set(ControlMode.Position, 0);
		  rightDrive.set(ControlMode.Position, 0);

		  DriverStation.reportError("left position: " + leftDrive.getSelectedSensorPosition(0), false);
		  DriverStation.reportError("right position: " + rightDrive.getSelectedSensorPosition(0), false);

		  currentLimit(leftDrive);
      currentLimit(rightDrive);
      currentLimit(leftDriveSlave);
      currentLimit(rightDriveSlave);

		  leftDrive.setInverted(false);
		  leftDriveSlave.setInverted(false);

		  rightDrive.setInverted(true);
      rightDriveSlave.setInverted(true);
      
      leftDrive.setNeutralMode(NeutralMode.Brake);
      rightDrive.setNeutralMode(NeutralMode.Brake);
      leftDriveSlave.setNeutralMode(NeutralMode.Brake);
      rightDriveSlave.setNeutralMode(NeutralMode.Brake);

      leftDrive.configClosedloopRamp(0.25);
      rightDrive.configClosedloopRamp(0.25);
      leftDriveSlave.configClosedloopRamp(0.25);
      rightDriveSlave.configClosedloopRamp(0.25);

      leftDrive.configOpenloopRamp(0.25);
      leftDriveSlave.configOpenloopRamp(0.25);
      rightDrive.configOpenloopRamp(0.25);
      rightDriveSlave.configOpenloopRamp(0.25);


      SpeedController leftFront = new TalonFX(3);
      SpeedControllerGroup autonLeftDrive = new SpeedControllerGroup(new PWMVictorSPX(Constants.L_DRIVE_ID), new PWMVictorSPX(Constants.L_DRIVE_SLAVE_ID));
    }

    public void FPSDrive(final PlasmaAxis forwardAxis, final PlasmaAxis turnAxis) {

      //double forwardVal = forwardAxis.getFilteredAxis() * Math.abs(forwardAxis.getFilteredAxis());
      //double turnVal = turnAxis.getFilteredAxis() * Math.abs(turnAxis.getFilteredAxis()) * Math.abs(turnAxis.getFilteredAxis());
  
      final double forwardVal = forwardAxis.getFilteredAxis() * Math.abs(forwardAxis.getFilteredAxis()) * Math.abs(forwardAxis.getFilteredAxis());
      final double turnVal = turnAxis.getFilteredAxis() * Math.abs(turnAxis.getFilteredAxis()) * Math.abs(turnAxis.getFilteredAxis());

      FPSDrive(forwardVal, turnVal);
    }
    
    public void resetEncoders() {
      // double dist = Math.abs(getDistance());
      leftDrive.setSelectedSensorPosition(0, 0, 0);
      rightDrive.setSelectedSensorPosition(0, 0, 0);
      DriverStation.reportWarning("resetting encoders", false);
      while (Math.abs(getDistance()) < -1 && Math.abs(getDistance()) > 1) {
        leftDrive.setSelectedSensorPosition(0, 0, 0);
        rightDrive.setSelectedSensorPosition(0, 0, 0);
        DriverStation.reportWarning("Stuck in loop", false);
      }
    }

    public double getDistance() {
      //return (toDistance(rightDrive) + toDistance(leftDrive)) / 2;
      return toDistance(leftDrive);
    }

    public double getLeftVelocity() {
      return leftDrive.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
      return rightDrive.getSelectedSensorVelocity();
    }
  
    public double getLeftDistance() {
      return toDistance(leftDrive);
    }

    public double getRightDistance() {
      return toDistance(rightDrive);
    }
  
    private static double toDistance(final TalonFX talon) {
      final double distance = talon.getSelectedSensorPosition(talon.getDeviceID()) * Constants.DRIVE_ENCODER_CONVERSION;
      // DriverStation.reportWarning(talon.getDeviceID() + " - distance: " + distance,
      // false);
      return distance;
    }
  
    public void updateGyro() {
      gyroAngle = navX.getYaw();
      gyroPitch = navX.getPitch();
    }
  
    public double getGyroAngle() {
      updateGyro();
      return gyroAngle;
    }
  
    public double getGyroPitch() {
      updateGyro();
      return gyroPitch;
    }
  
    public void zeroGyro() {
      navX.zeroYaw();
    }
  

    public void FPSDrive(final double forwardVal, double turnVal) {
  
      turnVal *= Constants.MAX_DRIVE_TURN;
  
      final double absForward = Math.abs(forwardVal);
      final double absTurn = Math.abs(turnVal);
  
      final int forwardSign = (forwardVal == 0) ? 0 : (int) (forwardVal / Math.abs(forwardVal));
      final int turnSign = (turnVal == 0) ? 0 : (int) (turnVal / Math.abs(turnVal));
  
      double speedL;
      double speedR;
  
      if (turnVal == 0) { // Straight forward
        speedL = forwardVal;
        speedR = forwardVal;
      } else if (forwardVal == 0) { // Pivot turn
        speedL = turnVal;
        speedR = -turnVal;
      } else if (forwardSign == 1 && turnSign == 1) { // Forward right
        speedL = forwardVal;
        speedR = (absForward - absTurn < 0) ? 0 : (absForward - (absTurn));
      } else if (forwardSign == 1 && turnSign == -1) { // Forward left
        speedL = (absForward - absTurn < 0) ? 0 : (absForward - (absTurn));
        speedR = forwardVal;
      } else if (forwardSign == -1 && turnSign == 1) { // Backward right
        speedL = (absForward - absTurn < 0) ? 0 : -(absForward - absTurn);
        speedR = forwardVal;
      } else if (forwardSign == -1 && turnSign == -1) { // Backward left
        speedL = forwardVal;
        speedR = (absForward - absTurn < 0) ? 0 : -(absForward - absTurn);
      } else {
        speedL = 0;
        speedR = 0;
        DriverStation.reportError("Bug @ fps drive code - no case triggered)", false);
      }
  
      speedL *= Constants.MAX_DRIVE_SPEED;
      speedR *= Constants.MAX_DRIVE_SPEED;
  
      leftDrive.set(ControlMode.PercentOutput, speedL);
      rightDrive.set(ControlMode.PercentOutput, speedR);

      SmartDashboard.putNumber("leftDriveSpeed", speedL);
      SmartDashboard.putNumber("rightDriveSpeed", speedR);
  
      leftDriveSlave.set(ControlMode.PercentOutput, speedL);
      rightDriveSlave.set(ControlMode.PercentOutput, speedR);

    }

    public void currentLimit(final TalonFX talon) {
      talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 30, 30,0));
    }
    
    public void leftWheelDrive(final double speed) {
      leftDrive.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      leftDriveSlave.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      //leftDriveSlaveFront.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
    }
  
    public void rightWheelDrive(final double speed) {
      rightDrive.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      rightDriveSlave.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      //rightDriveSlaveFront.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
    }

    public void autonTankDrive(final double left, final double right) {
      leftWheelDrive(left);
      rightWheelDrive(right);
    }

    public void gyroStraight(final double speed, final double angle) {
      if (getGyroAngle() > 0) {
        autonTankDrive(speed - 0.01 * (getGyroAngle() - angle), speed + 0.01 * (getGyroAngle() - angle));
      } else if (getGyroAngle() < 0) {
        autonTankDrive(speed + 0.01 * (getGyroAngle() + angle), speed - 0.01 * (getGyroAngle() + angle));
      } else {
        autonTankDrive(speed - 0.01 * (getGyroAngle() + angle), speed - 0.01 * (getGyroAngle() + angle));
      }
    }
  
    public void pivotToAngle(final double angle) {
      final double angleDiff = getGyroAngle() - angle;
      final double speed = (Math.abs(angleDiff) < 10) ? (Math.abs(angleDiff) / 10.0) * 0.15 + 0.15 : .3;
      if (angleDiff > 0) {
        autonTankDrive(-speed, speed);
      } else {
        autonTankDrive(speed, -speed);
      }
    }

    public void stopDrive() {
      autonTankDrive(0, 0);
      leftDrive.set(ControlMode.PercentOutput, 0);
      leftDriveSlave.set(ControlMode.PercentOutput, 0);
      //leftDriveSlaveFront.set(ControlMode.PercentOutput, 0);
      rightDrive.set(ControlMode.PercentOutput, 0);
      rightDriveSlave.set(ControlMode.PercentOutput, 0);
      //rightDriveSlaveFront.set(ControlMode.PercentOutput, 0);
    }

    public void spinMotor() {
      leftDrive.set(ControlMode.Follower, leftDriveSlave.getDeviceID());
    }


  private static double radiansPerSecondToTicksPer100ms(final double rad_s) {
    return rad_s / (Math.PI * 2.0) * 4096.0 / 10.0;
  }

  public enum DriveControlState {
    OPEN_LOOP, // open loop voltage control
    PATH_FOLLOWING, // velocity PID control
  }
}
