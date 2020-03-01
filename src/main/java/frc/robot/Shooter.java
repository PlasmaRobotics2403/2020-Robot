package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;


public class Shooter {
    TalonFX leftFlyWheelMotor;
    TalonFX rightFlyWheelMotor;
    TalonSRX hoodMotor;
    VictorSPX frontRollerMotor;
    //TalonSRX backRollerMotor;

    double targetAngle;
    double errorRange;
    
    Shooter(final int LEFT_FLY_WHEEL_MOTOR_ID, final int RIGHT_FLY_WHEEL_MOTOR_ID, final int HOOD_MOTOR_ID,
            final int FRONT_ROLLER_MOTOR_ID) {
        leftFlyWheelMotor = new TalonFX(LEFT_FLY_WHEEL_MOTOR_ID);
        rightFlyWheelMotor = new TalonFX(RIGHT_FLY_WHEEL_MOTOR_ID);
        hoodMotor = new TalonSRX(HOOD_MOTOR_ID);
        frontRollerMotor = new VictorSPX(FRONT_ROLLER_MOTOR_ID);
        //backRollerMotor = new TalonSRX(BACK_ROLLER_MOTOR_ID);

        limitCurrent(leftFlyWheelMotor);
        limitCurrent(rightFlyWheelMotor);
        limitCurrent(hoodMotor);
        // limitCurrent(backRollerMotor);

        leftFlyWheelMotor.setInverted(false);
        leftFlyWheelMotor.setSelectedSensorPosition(0,0,0);
        rightFlyWheelMotor.setInverted(true);
        rightFlyWheelMotor.setSelectedSensorPosition(0,0,0);

        hoodMotor.setInverted(false);
        hoodMotor.setSelectedSensorPosition(0,0,0);
        hoodMotor.setNeutralMode(NeutralMode.Brake);

        hoodMotor.config_kF(0, 0.667, 30); //feed forward speed
        hoodMotor.config_kP(0, 21, 30); // used to get close to position
		hoodMotor.config_kI(0, 0.001, 30); // start with 0.001
		hoodMotor.config_kD(0, 200, 30); // (second) ~ 10 x kP
        hoodMotor.config_IntegralZone(0, 0, 30);

        frontRollerMotor.setInverted(false);
        //backRollerMotor.setInverted(false);

        targetAngle = 0;
        errorRange = 0;
    };

    public void shoot(double speed) {
        
        leftFlyWheelMotor.set(ControlMode.PercentOutput, -speed);
        rightFlyWheelMotor.set(ControlMode.PercentOutput, -speed);
    }

    public void spinToRPM(double RPM) {
        leftFlyWheelMotor.set(ControlMode.Velocity, Constants.FLY_WHEEL_RADIUS * RPM * Constants.RPM_TO_RAD_PER_SEC_CONVERSION);
        rightFlyWheelMotor.set(ControlMode.Velocity, Constants.FLY_WHEEL_RADIUS * RPM * Constants.RPM_TO_RAD_PER_SEC_CONVERSION);
    }

    public double getShooterRPM() {
        return -leftFlyWheelMotor.getSelectedSensorVelocity();
    }

    public double getShooterPercentOutput() {
        return -leftFlyWheelMotor.getMotorOutputPercent();
    }

    public void stop() {
        leftFlyWheelMotor.set(ControlMode.PercentOutput, 0);
        rightFlyWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public void feedBalls(double speed) {;
        frontRollerMotor.set(ControlMode.PercentOutput, -speed);
        // backRollerMotor.set(ControlMode.PercentOutput, speed);
    }

    //public void raiseHood() {
    //    hoodMotor.set(ControlMode.PercentOutput, .75);
    //}
    //public void lowerHood() {
    //    hoodMotor.set(ControlMode.PercentOutput, -.75);
    //}
    //public void freezeHood() {
    //    hoodMotor.set(ControlMode.PercentOutput, 0);
    //}

    public void autoHood(double distance) {
        targetAngle = (77) * (-0.0135 * distance + 90.002);//(-80 * distance) + 7800;
        SmartDashboard.putNumber("target angle", targetAngle);
        errorRange = targetAngle * Constants.ANGLE_ERROR_PERCENT;
        SmartDashboard.putNumber("angle error range", errorRange);
        hoodMotor.set(ControlMode.Position, targetAngle);
    }
    public void hoodHidden() {
        hoodMotor.set(ControlMode.Position, 0);
    }

    public double getTargetAngle() {
        return targetAngle;
    }
    public double getErrorRange() {
        return errorRange;
    }

    public void displayHoodPosition() {
        SmartDashboard.putNumber("hood position", hoodMotor.getSelectedSensorPosition());
    }
    public int getHoodPosition() {
        return hoodMotor.getSelectedSensorPosition();
    }

    public void limitCurrent(final TalonFX talon) {
        talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 35, 35, 0));
    }
    

    public void limitCurrent(final TalonSRX talon) {
		talon.configPeakCurrentDuration(0, 1000);
		talon.configPeakCurrentLimit(15, 1000);
        talon.configContinuousCurrentLimit(15, 1000);
		talon.enableCurrentLimit(true);
    }
};