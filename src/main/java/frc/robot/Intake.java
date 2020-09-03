package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
    TalonSRX indexMotor2;
    TalonSRX indexMotor;
    VictorSPX rollerMotor;

    DigitalInput frontIndexSensor;
    DigitalInput backIndexSensor;

    Solenoid foreBarPiston;

    double speed;
    int advanceCount;
    int ballCount;

    Intake(int index2_motor_ID, int indexer_motor_ID, int intake_solenoid_ID, int front_index_sensor_ID, int back_index_sensor_ID, int roller_motor_ID) {
        indexMotor2 = new TalonSRX(index2_motor_ID);
        indexMotor = new TalonSRX(indexer_motor_ID);
        rollerMotor = new VictorSPX(roller_motor_ID);
        advanceCount = 0;
        ballCount = 3;

        indexMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        indexMotor.setSelectedSensorPosition(0,0,0);
        indexMotor.configClosedloopRamp(0, 300);
        indexMotor.configOpenloopRamp(0, 300);

        indexMotor.config_kF(0, 0.0, 300); //feed forward speed
        indexMotor.config_kP(0, 0.1, 300); // used to get close to position
		indexMotor.config_kI(0, 0.0, 300); // start with 0.001
		indexMotor.config_kD(0, 0, 300); // (second) ~ 10 x kP
        indexMotor.config_IntegralZone(0, 30, 300);

        frontIndexSensor = new DigitalInput(front_index_sensor_ID);
        backIndexSensor = new DigitalInput(back_index_sensor_ID);

        foreBarPiston = new Solenoid(intake_solenoid_ID);

        limitCurrent(indexMotor2);
        limitCurrent(indexMotor);

        rollerMotor.setInverted(false);
        indexMotor.setInverted(true);
        indexMotor2.setInverted(false);
    };

    public void intakeBall(double speed) {
        indexMotor2.set(ControlMode.PercentOutput, speed);
    }

    public void roller(double speed) {
        rollerMotor.set(ControlMode.PercentOutput, speed);
    }

    public void indexBall(double speed){
        indexMotor.set(ControlMode.PercentOutput, speed);
    }

    public void advanceBall(){
        advanceCount ++;
        indexMotor.set(ControlMode.Position, 16000*advanceCount);
        SmartDashboard.putNumber("index position", indexMotor.getSelectedSensorPosition());
        indexMotor2.set(ControlMode.Follower, indexMotor.getDeviceID());
        
    }

    public void resetAdvanceBall(){
        indexMotor.setSelectedSensorPosition(0, 0, 0);
        advanceCount = 0;
    }

    public double getIntakePosition() {
        return indexMotor.getSelectedSensorPosition();
    }

    public void displayIndexPosition(){
        SmartDashboard.putNumber("index position", indexMotor.getSelectedSensorPosition());
    }

    public void extendForeBar(){
        foreBarPiston.set(true);
    }

    public void retractForeBar(){
        foreBarPiston.set(false);
    }

    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
    }

    public boolean getFrontIndexSensorState() {
        return frontIndexSensor.get();
    }

    public boolean getBackIndexSensorState() {
        return backIndexSensor.get();
    }

    public int getAutonBallCount() {
        return ballCount;
    }

    public void addAutonBallCount() {
        ballCount ++;
    }

    public void subtractBallCount() {
        ballCount --;
    }
}