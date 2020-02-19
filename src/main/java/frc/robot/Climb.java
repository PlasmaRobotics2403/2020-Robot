package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climb {
    TalonSRX leftClimbMotor;
    TalonSRX rightClimbMotor;

    Climb(int left_climb_motor_ID, int right_climb_motor_ID) {
        leftClimbMotor = new TalonSRX(left_climb_motor_ID);
        rightClimbMotor = new TalonSRX(right_climb_motor_ID);

        limitCurrent(leftClimbMotor);
        limitCurrent(rightClimbMotor);
    };

    void spoolCable(double speed) {
        double spoolSpeed = speed * Constants.MAX_SPOOL_SPEED;

        leftClimbMotor.set(ControlMode.PercentOutput, spoolSpeed);
        rightClimbMotor.set(ControlMode.PercentOutput, spoolSpeed);
    }


    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }
}