package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
//import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class Turret {
    public TalonSRX motor;

    public Turret (int motorID){

        motor = new TalonSRX(motorID);

        limitCurrent(motor);
    }

    public void turn (double turnVal) {
        turnVal *= Constants.MAX_TURRET_SPEED;

        motor.set(ControlMode.PercentOutput, turnVal);
        
        SmartDashboard.putNumber("turretTurnSpeed", turnVal);
    }

    public void limitCurrent(TalonSRX talon) {
		talon.configPeakCurrentDuration(0, 1000);
		talon.configPeakCurrentLimit(15, 1000);
		talon.configContinuousCurrentLimit(15, 1000);
		talon.enableCurrentLimit(true);
    }
    
    public void stopTurning() {
        motor.set(ControlMode.PercentOutput, 0);
    }
}