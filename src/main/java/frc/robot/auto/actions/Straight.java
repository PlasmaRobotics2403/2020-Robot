package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Constants;
import frc.robot.Drive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class Straight implements Action{
    double speed;
    double distance; //inches

    Drive drive;

    public Straight(double speed, double distance, Drive drive){
        this.speed = Math.abs(speed);
        this.distance = distance;
        this.drive = drive;
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
    }

    @Override
    public void update(){
        drive.gyroStraight(speed, 0);
    }

    public void end() {
        drive.stopDrive();
        DriverStation.reportWarning("done", false);
    }
}