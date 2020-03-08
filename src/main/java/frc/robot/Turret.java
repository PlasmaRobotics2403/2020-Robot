package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class Turret {
    public TalonSRX turretRotationMotor;

    public Turret (int TURRET_ROTATION_MOTOR_ID){

        turretRotationMotor = new TalonSRX(TURRET_ROTATION_MOTOR_ID);
        turretRotationMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);

        limitCurrent(turretRotationMotor);
        turretRotationMotor.setInverted(false);
    }

    public void turn(double turnVal) {
        if(turretRotationMotor.getSelectedSensorPosition() > -10000 && turretRotationMotor.getSelectedSensorPosition() < 10000){
            turnVal *= Constants.MAX_TURRET_SPEED;
        }
        //else if(turretRotationMotor.getSelectedSensorPosition() < -10000 && turnVal > 0){
        //    turnVal *= Constants.MAX_TURRET_SPEED;
        //}
        //else if(turretRotationMotor.getSelectedSensorPosition() > 10000 && turnVal < 0){
        //    turnVal *= Constants.MAX_TURRET_SPEED;
        //}
        else {
            turnVal = 0;
        }

        turretRotationMotor.set(ControlMode.PercentOutput, turnVal);

        SmartDashboard.putNumber("turretTurnSpeed", turnVal);
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