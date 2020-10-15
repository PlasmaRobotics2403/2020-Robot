package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

//import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class Turret {
    public TalonSRX turretRotationMotor;

    DigitalInput minLimit;
    DigitalInput maxLimit;

    boolean isCalibrated;
    boolean isTracking;

    public Turret (int TURRET_ROTATION_MOTOR_ID, int MIN_LIMIT_SWITCH_ID, int MAX_LIMIT_SWITCH_ID){

        turretRotationMotor = new TalonSRX(TURRET_ROTATION_MOTOR_ID);
        turretRotationMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
        turretRotationMotor.setSelectedSensorPosition(6100);

        isCalibrated = false;
        isTracking = true;

        limitCurrent(turretRotationMotor);
        turretRotationMotor.setInverted(false);
        turretRotationMotor.setSensorPhase(false);

        turretRotationMotor.config_kF(0, 0.0, 30); //feed forward speed
        turretRotationMotor.config_kP(0, 0.4, 30); // used to get close to position
		turretRotationMotor.config_kI(0, 0.003, 30); // start with 0.001
		turretRotationMotor.config_kD(0, 0, 30); // (second) ~ 10 x kP
        turretRotationMotor.config_IntegralZone(0, 500, 30);

        turretRotationMotor.configClosedloopRamp(0.2);
        turretRotationMotor.configOpenloopRamp(0);

        minLimit = new DigitalInput(MIN_LIMIT_SWITCH_ID);
        maxLimit = new DigitalInput(MAX_LIMIT_SWITCH_ID);

    }

    public void turn(double turnVal) {
        if(turretRotationMotor.getSelectedSensorPosition() > Constants.MIN_LIMIT_DISTANCE && turretRotationMotor.getSelectedSensorPosition() < Constants.MAX_LIMIT_DISTANCE){
            turnVal *= Constants.MAX_TURRET_SPEED;
        }
        else if(turretRotationMotor.getSelectedSensorPosition() < Constants.MIN_LIMIT_DISTANCE || !minLimit.get()){
            if(turnVal > 0){
                turnVal *= Constants.MAX_TURRET_SPEED;
            }
            else{
                turnVal = 0;
            }
        }
        else if(turretRotationMotor.getSelectedSensorPosition() > Constants.MAX_LIMIT_DISTANCE || !maxLimit.get()){
            if(turnVal < 0){
                turnVal *= Constants.MAX_TURRET_SPEED;
            }
            else {
                turnVal = 0;
            }
        }
        else {
            turnVal = 0;
        }
        

        turretRotationMotor.set(ControlMode.PercentOutput, turnVal);

        SmartDashboard.putNumber("turretTurnSpeed", turnVal);
    }

    public boolean getCalibrated(){
        return isCalibrated;
    }

    public void calibrate(){
        if(!maxLimit.get()){
            isCalibrated = true;
            turretRotationMotor.setSelectedSensorPosition(Constants.MAX_LIMIT_DISTANCE);
            turn(0);
        }
        else {
            turretRotationMotor.set(ControlMode.PercentOutput, 0.2);
        }
    }

    public double getVelocity(){
        return turretRotationMotor.getSelectedSensorVelocity();
    }

    private int angleToEncoderPosition(double angle){
        int position = ((int) ((angle/360.0)*Constants.ENCODER_TICKS_PER_ROTATION)) % Constants.ENCODER_TICKS_PER_ROTATION;
        DriverStation.reportWarning("first test: " + String.valueOf(position), false);
        if(position > Constants.MAX_LIMIT_DISTANCE){
            int maxDistance = position - Constants.MAX_LIMIT_DISTANCE;
            position -= Constants.ENCODER_TICKS_PER_ROTATION;
            if(position < Constants.MIN_LIMIT_DISTANCE){
                int minDistance = Constants.MIN_LIMIT_DISTANCE - position;
                return (maxDistance < minDistance) ? Constants.MAX_LIMIT_DISTANCE : Constants.MIN_LIMIT_DISTANCE;
            }
        }
        else if(position < Constants.MIN_LIMIT_DISTANCE){
            int minDistance = Constants.MIN_LIMIT_DISTANCE - position;
            position += Constants.ENCODER_TICKS_PER_ROTATION;
            if(position > Constants.MAX_LIMIT_DISTANCE){
                int maxDistance = position - Constants.MAX_LIMIT_DISTANCE;
                return (maxDistance < minDistance) ? Constants.MAX_LIMIT_DISTANCE : Constants.MIN_LIMIT_DISTANCE;
            }
        }
        DriverStation.reportWarning("second test: " + String.valueOf(position), false);
        return position;
    }

    public double getTurretAngle(){
        double position = turretRotationMotor.getSelectedSensorPosition();
        return position * 360 / Constants.ENCODER_TICKS_PER_ROTATION;
    }

    public void setTurretPosition(double angle){
        int position = angleToEncoderPosition(angle);
        turretRotationMotor.set(ControlMode.Position, position);
        SmartDashboard.putNumber("Turret Align Error", turretRotationMotor.getClosedLoopError());
    }

    public double getTurretPosition(){
        return turretRotationMotor.getSelectedSensorPosition();
    }

    public boolean displayMaxLimit(){
        return maxLimit.get();
    }

    public boolean displayMinLimit(){
        return minLimit.get();
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

    public void setIsTracking(boolean tracking){
        isTracking = tracking;
    }

    public boolean getIsTracking(){
        return isTracking;
    }
}