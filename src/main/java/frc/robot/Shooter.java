package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
    double hoodPosition;
    
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
        leftFlyWheelMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        leftFlyWheelMotor.setSelectedSensorPosition(0,0,0);
        leftFlyWheelMotor.setNeutralMode(NeutralMode.Brake);
        rightFlyWheelMotor.setInverted(true);
        rightFlyWheelMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        rightFlyWheelMotor.setSelectedSensorPosition(0,0,0);
        rightFlyWheelMotor.setNeutralMode(NeutralMode.Brake);

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

    public void stopFlyWheel() {
        
        leftFlyWheelMotor.set(ControlMode.PercentOutput, 0);
        rightFlyWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public void spinToRPM(double RPM) {
        leftFlyWheelMotor.set(ControlMode.Velocity, -RPM);
        rightFlyWheelMotor.set(ControlMode.Velocity, -RPM);
    }

    public double getLeftShooterRPM() {
        return -leftFlyWheelMotor.getSelectedSensorVelocity();
    }

    public double getRightShooterRPM() {
        return -rightFlyWheelMotor.getSelectedSensorVelocity();
    }

    public void displayShooterRPM() {
        SmartDashboard.putNumber("Shooter RPM", -leftFlyWheelMotor.getSelectedSensorVelocity());
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

    public void autoHood(double x, int valid) {
        hoodPosition = 2000;
        if(valid >= 1){
            double a = 0.00041;
            double b = 0.0230;
            double c = 0.1872;
            double d = -3.860;
            double e = -132.909;
            double f = 6184.52;
            hoodPosition = a*x*x*x*x*x + b*x*x*x*x + c*x*x*x + d*x*x + e*x + f;
        }
        SmartDashboard.putNumber("camera angle", x);
        SmartDashboard.putNumber("hood target position", hoodPosition);
        hoodMotor.set(ControlMode.Position, hoodPosition);
    }
    public void hoodHidden() {
        hoodMotor.set(ControlMode.Position, 0);
    }

    public double getTargetHoodPosition(){
        return hoodPosition;
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