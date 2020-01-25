package frc.robot;

import frc.robot.controllers.PlasmaTrigger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
    TalonSRX motorA;
    TalonSRX motorB;

    double speed;

    Intake(int motor_A_ID, int motor_B_ID) {
        motorA = new TalonSRX(motor_A_ID);
        motorB = new TalonSRX(motor_B_ID);

        limitCurrent(motorA);
        limitCurrent(motorB);
    };

    void intakeBall(PlasmaTrigger trigger) {
        double speed = trigger.getFilteredAxis();

        motorA.set(ControlMode.PercentOutput, speed);
        motorB.set(ControlMode.PercentOutput, speed);
    }

    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }
}