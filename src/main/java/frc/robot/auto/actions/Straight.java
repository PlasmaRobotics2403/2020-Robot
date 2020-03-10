package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Constants;
import frc.robot.Drive;
import frc.robot.Intake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class Straight implements Action{
    double speed;
    double distance; //inches

    Drive drive;
    Intake intake;

    boolean intaking;

    public Straight(double speed, double distance, Drive drive, boolean intaking, Intake intake){
        this.speed = Math.abs(speed);
        this.distance = distance;
        this.drive = drive;
        this.intaking = intaking;
        this.intake = intake;
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drive.getDistance()) > distance;
    }

    @Override
    public void start() {
        drive.resetEncoders();
        while(Math.abs(drive.getDistance()) > 1){
            drive.resetEncoders();
            DriverStation.reportWarning("broke", false);
        }
        drive.zeroGyro();
        if(intaking){
            intake.extendForeBar();
        }
    }

    @Override
    public void update(){
        drive.gyroStraight(speed, 0);
        if(intaking){
            intake.roller(Constants.MAX_ROLLER_SPEED);
            if(intake.getFrontIndexSensorState() == false){
                intake.advanceBall();
            }
            if(intake.getIntakePosition() > 55000) {
                intake.indexBall(0);
                intake.intakeBall(0);
                intake.resetAdvanceBall();
            }
        }
    }

    public void end() {
        if(intaking == true){
            //intake.roller(0);
        }
        drive.stopDrive();
        DriverStation.reportWarning("done", false);
    }
}