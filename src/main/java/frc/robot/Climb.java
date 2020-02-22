package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climb {
    VictorSPX leftClimbMotor;
    VictorSPX rightClimbMotor;

    DoubleSolenoid climbLatch;

    Climb(int left_climb_motor_ID, int right_climb_motor_ID, int climb_latch_ID, int climb_latch_plug_ID) {
        leftClimbMotor = new VictorSPX(left_climb_motor_ID);
        rightClimbMotor = new VictorSPX(right_climb_motor_ID);

        climbLatch = new DoubleSolenoid(climb_latch_ID, climb_latch_plug_ID);

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
        climbLatch.set(Value.kReverse);
    }

    void engageLatch() {
        climbLatch.set(Value.kForward);
    }


    public void limitCurrent(TalonSRX talon) {
        talon.configPeakCurrentDuration(0 , 1000);
        talon.configPeakCurrentDuration(45, 1000);
        talon.configContinuousCurrentLimit(45,1000);
        talon.enableCurrentLimit(true);
        talon.configClosedloopRamp(1);
    }
}