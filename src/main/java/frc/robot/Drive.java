/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.controllers.PlasmaAxis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

// Wiplib 2020 says to use edu.wpi.first.wpilibj2
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//import edu.wpi.first.wpilibj.command.Command;

public class Drive {

    public TalonFX leftDrive;
    public TalonFX leftDriveSlave;
    public TalonFX rightDrive;
    public TalonFX rightDriveSlave;

    private int timer;

    private AHRS navX;
    private double gyroAngle;
    private double gyroPitch;

    public Drive(int leftDriveID, int leftDriveSlaveID, int rightDriveID, int rightDriveSlaveID){
      leftDrive = new TalonFX(leftDriveID);
      leftDriveSlave = new TalonFX(leftDriveSlaveID);
      rightDrive = new TalonFX(rightDriveID);
      rightDriveSlave = new TalonFX(rightDriveSlaveID);

      navX = new AHRS(SPI.Port.kMXP);
      
      leftDrive.configFactoryDefault();
      rightDrive.configFactoryDefault();
      leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);
		  rightDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);

      leftDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
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

		  rightDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
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

		  configSupplyLimit(leftDrive);
      configSupplyLimit(rightDrive);

		  leftDrive.setInverted(false);
		  leftDriveSlave.setInverted(false);

		  rightDrive.setInverted(true);
		  rightDriveSlave.setInverted(true);
    }

    public void FPSDrive(PlasmaAxis forwardAxis, PlasmaAxis turnAxis) {

      double forwardVal = forwardAxis.getFilteredAxis() * Math.abs(forwardAxis.getFilteredAxis());
      double turnVal = turnAxis.getFilteredAxis() * Math.abs(turnAxis.getFilteredAxis())
          * Math.abs(turnAxis.getFilteredAxis());
  
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
      return (toDistance(rightDrive) + toDistance(leftDrive)) / 2;
    }
  
    public double getLeftDistance() {
      return toDistance(leftDrive);
    }
  
    private static double toDistance(TalonFX talon) {
      double distance = talon.getSelectedSensorPosition(talon.getDeviceID()) / Constants.DRIVE_ENCODER_CONVERSION;
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
  

    public void FPSDrive(double forwardVal, double turnVal) {
  
      turnVal *= Constants.MAX_DRIVE_TURN;
  
      double absForward = Math.abs(forwardVal);
      double absTurn = Math.abs(turnVal);
  
      int forwardSign = (forwardVal == 0) ? 0 : (int) (forwardVal / Math.abs(forwardVal));
      int turnSign = (turnVal == 0) ? 0 : (int) (turnVal / Math.abs(turnVal));
  
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
  
      leftDrive.set(ControlMode.PercentOutput, speedL / 3);
      rightDrive.set(ControlMode.PercentOutput, speedR / 3);

      SmartDashboard.putNumber("leftDriveSpeed", speedL);
      SmartDashboard.putNumber("rightDriveSpeed", speedR);
  
      leftDriveSlave.set(ControlMode.PercentOutput, speedL / 3);
      rightDriveSlave.set(ControlMode.PercentOutput, speedR / 3);
  
      timer = 0;
  
      while (timer < 10) {
        timer++;
        SmartDashboard.putNumber("leftDriveSpeed", speedL);
        SmartDashboard.putNumber("rightDriveSpeed", speedR);
      }
  
      leftDrive.set(ControlMode.PercentOutput, speedL);
      rightDrive.set(ControlMode.PercentOutput, speedR);
  
      leftDriveSlave.set(ControlMode.PercentOutput, speedL);
      //leftDriveSlaveMid.set(ControlMode.PercentOutput, speedL);
      rightDriveSlave.set(ControlMode.PercentOutput, speedR);
      //rightDriveSlaveMid.set(ControlMode.PercentOutput, speedR);
  
    }

    public void configSupplyLimit(TalonFX talon) {
      talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(), 1000);
    }
    
    public void leftWheelDrive(double speed) {
      leftDrive.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      leftDriveSlave.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      //leftDriveSlaveFront.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
    }
  
    public void rightWheelDrive(double speed) {
      rightDrive.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      rightDriveSlave.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
      //rightDriveSlaveFront.set(ControlMode.PercentOutput, speed * Constants.MAX_AUTO_DRIVE_SPEED);
    }

    public void autonTankDrive(double left, double right) {
      leftWheelDrive(left);
      rightWheelDrive(right);
    }

    public void gyroStraight(double speed, double angle) {
      if (getGyroAngle() > 0) {
        autonTankDrive(speed - 0.01 * (getGyroAngle() - angle), speed - 0.01 * (getGyroAngle() - angle));
      } else if (getGyroAngle() < 0) {
        autonTankDrive(speed - 0.01 * (getGyroAngle() + angle), speed - 0.01 * (getGyroAngle() + angle));
      } else {
        autonTankDrive(speed - 0.01 * (getGyroAngle() + angle), speed - 0.01 * (getGyroAngle() + angle));
      }
    }
  
    public void pivotToAngle(double angle) {
      double angleDiff = getGyroAngle() - angle;
      double speed = (Math.abs(angleDiff) < 10) ? (Math.abs(angleDiff) / 10.0) * 0.15 + 0.15 : .3;
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

}