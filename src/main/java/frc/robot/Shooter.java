package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class Shooter {
    TalonFX motorA;
    TalonFX motorB;

    double speed;

    private DoubleSolenoid leftSolenoid = new DoubleSolenoid(0, 1);
    private DoubleSolenoid rightSolenoid = new DoubleSolenoid(2, 3);

    Shooter(int motor_A_ID, int motor_B_ID) {
        motorA = new TalonFX(motor_A_ID);
        motorB = new TalonFX(motor_B_ID);

        limitCurrent(motorA);
        limitCurrent(motorB);

        motorA.setInverted(false);
        motorB.setInverted(true);

    };

    void shoot() {
        double speed = Constants.MAX_SHOOTER_SPEED;

        motorA.set(ControlMode.PercentOutput, -speed); 
        motorB.set(ControlMode.PercentOutput, -speed);
    }

    void stop() {
        motorA.set(ControlMode.PercentOutput, 0);
        motorB.set(ControlMode.PercentOutput, 0);
    }

    void extendHood() {
        leftSolenoid.set(Value.kForward);
        rightSolenoid.set(Value.kForward);
    }

    void retractHood() {
        leftSolenoid.set(Value.kReverse);
        rightSolenoid.set(Value.kReverse);
    }

    public void limitCurrent(TalonFX talon) {
        talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(), 1000);
    }
};