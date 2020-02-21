package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;


public class Shooter {
    TalonFX leftFlyWheelMotor;
    TalonFX rightFlyWheelMotor;
    TalonSRX hoodMotor;
    VictorSPX frontRollerMotor;
    TalonSRX backRollerMotor;
    
    Shooter(int LEFT_FLY_WHEEL_MOTOR_ID, int RIGHT_FLY_WHEEL_MOTOR_ID, int HOOD_MOTOR_ID, int FRONT_ROLLER_MOTOR_ID, int BACK_ROLLER_MOTOR_ID) {
        leftFlyWheelMotor = new TalonFX(LEFT_FLY_WHEEL_MOTOR_ID);
        rightFlyWheelMotor = new TalonFX(RIGHT_FLY_WHEEL_MOTOR_ID);
        hoodMotor = new TalonSRX(HOOD_MOTOR_ID);
        frontRollerMotor = new VictorSPX(FRONT_ROLLER_MOTOR_ID);
        backRollerMotor = new TalonSRX(BACK_ROLLER_MOTOR_ID);

        limitCurrent(leftFlyWheelMotor);
        limitCurrent(rightFlyWheelMotor);
        limitCurrent(hoodMotor);
        limitCurrent(backRollerMotor);

        leftFlyWheelMotor.setInverted(false);
        rightFlyWheelMotor.setInverted(true);
        hoodMotor.setInverted(false);
        frontRollerMotor.setInverted(false);
        backRollerMotor.setInverted(false);

    };

    public void shoot() {
        double speed = Constants.MAX_SHOOTER_SPEED;

        leftFlyWheelMotor.set(ControlMode.PercentOutput, -speed);
        rightFlyWheelMotor.set(ControlMode.PercentOutput, -speed);
    }

    public void stop() {
        leftFlyWheelMotor.set(ControlMode.PercentOutput, 0);
        rightFlyWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public void feedBalls(double speed) {
        speed *= Constants.MAX_BALL_FEED_SPEED;
        frontRollerMotor.set(ControlMode.PercentOutput, speed);
        backRollerMotor.set(ControlMode.PercentOutput, speed);
    }

    public void limitCurrent(TalonFX talon) {
        talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(), 1000);
    }

    public void limitCurrent(TalonSRX talon) {
		talon.configPeakCurrentDuration(0, 1000);
		talon.configPeakCurrentLimit(15, 1000);
		talon.configContinuousCurrentLimit(15, 1000);
		talon.enableCurrentLimit(true);
    }
};