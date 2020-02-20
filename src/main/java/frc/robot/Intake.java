package frc.robot;

import frc.robot.controllers.PlasmaTrigger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake {
    TalonSRX intakeMotor;
    TalonSRX indexerMotor;

    DoubleSolenoid foreBarPiston;

    double speed;

    Intake(int intake_motor_ID, int indexer_motor_ID, int intake_forward_ID, int intake_reverse_ID) {
        intakeMotor = new TalonSRX(intake_motor_ID);
        indexerMotor = new TalonSRX(indexer_motor_ID);

        foreBarPiston = new DoubleSolenoid(intake_forward_ID, intake_reverse_ID);

        limitCurrent(intakeMotor);
        limitCurrent(indexerMotor);
    };

    void intakeBall(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    void indexBall(double speed){
        indexerMotor.set(ControlMode.PercentOutput, speed);
    }

    void extendForeBar(){
        foreBarPiston.set(Value.kForward);
    }

    void retractForeBar(){
        foreBarPiston.set(Value.kReverse);
    }

    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }
}