package frc.robot;

import frc.robot.controllers.PlasmaTrigger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
    TalonSRX intakeMotor;
    TalonSRX indexerMotor;

    double speed;

    Intake(int intake_motor_ID, int indexer_motor_ID) {
        intakeMotor = new TalonSRX(intake_motor_ID);
        indexerMotor = new TalonSRX(indexer_motor_ID);

        limitCurrent(intakeMotor);
        limitCurrent(indexerMotor);
    };

    void intakeBall(PlasmaTrigger trigger) {
        double speed = trigger.getFilteredAxis();

        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    void indexBall(double speed){
        double indexSpeed = speed * Constants.MAX_INDEX_SPEED;

        indexerMotor.set(ControlMode.PercentOutput, indexSpeed);
    }

    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }
}