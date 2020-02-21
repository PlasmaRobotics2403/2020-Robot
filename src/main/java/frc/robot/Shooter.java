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
<<<<<<< HEAD
    VictorSPX frontRollerMotor;
    TalonSRX backRollerMotor;
=======
    TalonSRX frontRollerMotor;
    //TalonSRX backRollerMotor;
>>>>>>> ddd4999917806225bc6b9ab69b997cfa141946dd
    
    Shooter(final int LEFT_FLY_WHEEL_MOTOR_ID, final int RIGHT_FLY_WHEEL_MOTOR_ID, final int HOOD_MOTOR_ID,
            final int FRONT_ROLLER_MOTOR_ID) {
        leftFlyWheelMotor = new TalonFX(LEFT_FLY_WHEEL_MOTOR_ID);
        rightFlyWheelMotor = new TalonFX(RIGHT_FLY_WHEEL_MOTOR_ID);
        hoodMotor = new TalonSRX(HOOD_MOTOR_ID);
<<<<<<< HEAD
        frontRollerMotor = new VictorSPX(FRONT_ROLLER_MOTOR_ID);
        backRollerMotor = new TalonSRX(BACK_ROLLER_MOTOR_ID);
=======
        frontRollerMotor = new TalonSRX(FRONT_ROLLER_MOTOR_ID);
        // backRollerMotor = new TalonSRX(BACK_ROLLER_MOTOR_ID);
>>>>>>> ddd4999917806225bc6b9ab69b997cfa141946dd

        limitCurrent(leftFlyWheelMotor);
        limitCurrent(rightFlyWheelMotor);
        limitCurrent(hoodMotor);
<<<<<<< HEAD
        limitCurrent(backRollerMotor);
=======
        limitCurrent(frontRollerMotor);
        // limitCurrent(backRollerMotor);
>>>>>>> ddd4999917806225bc6b9ab69b997cfa141946dd

        leftFlyWheelMotor.setInverted(false);
        rightFlyWheelMotor.setInverted(true);
        hoodMotor.setInverted(false);
        frontRollerMotor.setInverted(false);
        // backRollerMotor.setInverted(false);

    };

    public void shoot(double distance) {
        double speed = distance / 2;
        leftFlyWheelMotor.set(ControlMode.PercentOutput, -speed);
        rightFlyWheelMotor.set(ControlMode.PercentOutput, -speed);
    }

    public void stop() {
        leftFlyWheelMotor.set(ControlMode.PercentOutput, 0);
        rightFlyWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public void feedBalls(double speed) {;
        frontRollerMotor.set(ControlMode.PercentOutput, speed);
        // backRollerMotor.set(ControlMode.PercentOutput, speed);
    }

    public void limitCurrent(final TalonFX talon) {
        talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(), 1000);
    }

    public void limitCurrent(final TalonSRX talon) {
		talon.configPeakCurrentDuration(0, 1000);
		talon.configPeakCurrentLimit(15, 1000);
		talon.configContinuousCurrentLimit(15, 1000);
		talon.enableCurrentLimit(true);
    }
};