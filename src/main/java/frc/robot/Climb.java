package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;

public class Climb {
    VictorSPX leftClimbMotor;
    VictorSPX rightClimbMotor;

    Solenoid climbLatch;

    Climb(int left_climb_motor_ID, int right_climb_motor_ID, int climb_latch_ID) {
        leftClimbMotor = new VictorSPX(left_climb_motor_ID);
        rightClimbMotor = new VictorSPX(right_climb_motor_ID);

        climbLatch = new Solenoid(climb_latch_ID);

        leftClimbMotor.setInverted(false);
        rightClimbMotor.setInverted(true);

        //limitCurrent(leftClimbMotor);
        //limitCurrent(rightClimbMotor);
    };

    void spoolCable(double speed) {
        double spoolSpeed = speed * Constants.MAX_SPOOL_SPEED;

        leftClimbMotor.set(ControlMode.PercentOutput, spoolSpeed);
        rightClimbMotor.set(ControlMode.PercentOutput, spoolSpeed);
    }

    void releaseLatch() {
        climbLatch.set(false);
    }

    void engageLatch() {
        climbLatch.set(true);
    }


    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }
}