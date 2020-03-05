package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

        indexMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        indexMotor.setSelectedSensorPosition(0,0,0);

        indexMotor.config_kF(0, 0.667, 30); //feed forward speed
        indexMotor.config_kP(0, 11, 30); // used to get close to position
		indexMotor.config_kI(0, 0.0005, 30); // start with 0.001
		indexMotor.config_kD(0, 20, 30); // (second) ~ 10 x kP
        indexMotor.config_IntegralZone(0, 30, 30);

        indexSensor = new DigitalInput(index_sensor_ID);

        foreBarPiston = new DoubleSolenoid(intake_forward_ID, intake_reverse_ID);

        limitCurrent(intakeMotor);
        limitCurrent(indexMotor);

        rollerMotor.setInverted(true);
        indexMotor.setInverted(false);
        intakeMotor.setInverted(true);
    };

    public void intakeBall(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public void roller(double speed) {
        rollerMotor.set(ControlMode.PercentOutput, speed);
    }

    public void indexBall(double speed){
        indexMotor.set(ControlMode.PercentOutput, speed);
    }

    public void advanceBall(){
        indexMotor.set(ControlMode.Position, 21000);
        intakeMotor.set(ControlMode.Follower, indexMotor.getDeviceID());
        
    }

    public void resetAdvanceBall(){
        indexMotor.setSelectedSensorPosition(0, 0, 0);
    }

    public double getIntakePosition() {
        return indexMotor.getSelectedSensorPosition();
    }

    public void displayIndexPosition(){
        SmartDashboard.putNumber("index position", indexMotor.getSelectedSensorPosition());
    }

    public void extendForeBar(){
        foreBarPiston.set(Value.kReverse);
    }

    public void retractForeBar(){
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