package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;

public class Climb {
    TalonSRX leftClimbMotor;
    VictorSPX rightClimbMotor;
    
    Solenoid climbLatch;

    Climb(int left_climb_motor_ID, int right_climb_motor_ID, int climb_latch_ID) {
        leftClimbMotor = new TalonSRX(left_climb_motor_ID);
        rightClimbMotor = new VictorSPX(right_climb_motor_ID);

        climbLatch = new Solenoid(climb_latch_ID);

        leftClimbMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        leftClimbMotor.setSelectedSensorPosition(0, 0, 0);

        leftClimbMotor.configClosedloopRamp(0, 300);
        leftClimbMotor.configOpenloopRamp(0, 300);

        leftClimbMotor.config_kF(0, 0.0, 300); //feed forward speed
        leftClimbMotor.config_kP(0, .1, 300); // used to get close to position
		leftClimbMotor.config_kI(0, 0.0, 300); // start with 0.001
		leftClimbMotor.config_kD(0, 0, 300); // (second) ~ 10 x kP
        leftClimbMotor.config_IntegralZone(0, 30, 300);

        leftClimbMotor.setInverted(false);
        rightClimbMotor.setInverted(true);

        limitCurrent(leftClimbMotor);
    }

    void spoolCable(double speed) {
        double spoolSpeed = speed * Constants.MAX_SPOOL_SPEED;

        leftClimbMotor.set(ControlMode.PercentOutput, spoolSpeed);
        rightClimbMotor.set(ControlMode.PercentOutput, spoolSpeed);
    }

    void releaseLatch() {
        climbLatch.set(true);
    }

    void engageLatch() {
        climbLatch.set(false);
    }

    public int getLeftEncoderValue(){
        return leftClimbMotor.getSelectedSensorPosition();
    }

    public void setPosition(int position){
        leftClimbMotor.set(ControlMode.Position, position);
        rightClimbMotor.set(ControlMode.Follower, leftClimbMotor.getDeviceID());
    }

    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }
}