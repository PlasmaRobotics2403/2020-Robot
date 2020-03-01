package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake {
    TalonSRX intakeMotor;
    TalonSRX indexMotor;
    VictorSPX rollerMotor;

    DigitalInput indexSensor;

    DoubleSolenoid foreBarPiston;

    double speed;

    Intake(int intake_motor_ID, int indexer_motor_ID, int intake_forward_ID, int intake_reverse_ID, int index_sensor_ID, int roller_motor_ID) {
        intakeMotor = new TalonSRX(intake_motor_ID);
        indexMotor = new TalonSRX(indexer_motor_ID);
        rollerMotor = new VictorSPX(roller_motor_ID);

        indexSensor = new DigitalInput(index_sensor_ID);

        foreBarPiston = new DoubleSolenoid(intake_forward_ID, intake_reverse_ID);

        limitCurrent(intakeMotor);
        limitCurrent(indexMotor);

        rollerMotor.setInverted(true);
    };

    void intakeBall(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    void roller(double speed) {
        rollerMotor.set(ControlMode.PercentOutput, speed);
    }

    void indexBall(double speed){
        indexMotor.set(ControlMode.PercentOutput, speed);
    }

    void extendForeBar(){
        foreBarPiston.set(Value.kReverse);
    }

    void retractForeBar(){
        foreBarPiston.set(Value.kForward);
    }

    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }

    public boolean getIndexSensorState() {
        return indexSensor.get();
    }
}