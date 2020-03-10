package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class Turret {
    public TalonSRX turretRotationMotor;

    DigitalInput minLimit;
    DigitalInput maxLimit;

    public Turret (int TURRET_ROTATION_MOTOR_ID, int MIN_LIMIT_SWITCH_ID, int MAX_LIMIT_SWITCH_ID){

        turretRotationMotor = new TalonSRX(TURRET_ROTATION_MOTOR_ID);
        turretRotationMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);

        limitCurrent(turretRotationMotor);
        turretRotationMotor.setInverted(false);

        turretRotationMotor.config_kF(0, 0.3, 30); //feed forward speed
        turretRotationMotor.config_kP(0, 0.2, 30); // used to get close to position
		turretRotationMotor.config_kI(0, 0.0, 30); // start with 0.001
		turretRotationMotor.config_kD(0, 0, 30); // (second) ~ 10 x kP
        turretRotationMotor.config_IntegralZone(0, 0, 30);

        minLimit = new DigitalInput(MIN_LIMIT_SWITCH_ID);
        maxLimit = new DigitalInput(MAX_LIMIT_SWITCH_ID);

    }

    public void turn(double turnVal) {
        if(turretRotationMotor.getSelectedSensorPosition() > -16500 && turretRotationMotor.getSelectedSensorPosition() < 9500){
            turnVal *= Constants.MAX_TURRET_SPEED;
        }
        else if(turretRotationMotor.getSelectedSensorPosition() < -16500 && turnVal > 0){
            turnVal *= Constants.MAX_TURRET_SPEED;
        }
        else if(turretRotationMotor.getSelectedSensorPosition() > 9500 && turnVal < 0){
            turnVal *= Constants.MAX_TURRET_SPEED;
        }
        else {
            turnVal = 0;
        }
        

        turretRotationMotor.set(ControlMode.PercentOutput, turnVal);

        SmartDashboard.putNumber("turretTurnSpeed", turnVal);
    }

    public void setTurretPosition(double position){
        turretRotationMotor.set(ControlMode.Position, position);
    }

    public double getTurretPosition(){
        return turretRotationMotor.getSelectedSensorPosition();
    }

    public void displayTurretPosition(){
        SmartDashboard.putNumber("Turret Position", turretRotationMotor.getSelectedSensorPosition());
    }
    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0, 1000);
        talon.configPeakCurrentLimit(15, 1000);
        talon.configContinuousCurrentLimit(15, 1000);
        talon.enableCurrentLimit(true);
    }

    public void resetTurretPosition(){
        turretRotationMotor.setSelectedSensorPosition(0, 0, 0);
    }

    public void stopTurning() {
        turretRotationMotor.set(ControlMode.PercentOutput, 0);
    }
}