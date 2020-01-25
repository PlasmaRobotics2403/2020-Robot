package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Drive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class Straight implements Action{
    double speed;
    double speedPValue;
    double minSpeed;
    double fullSpeedTime;
    double fullStartTime;

    Drive drive;

    public Straight(double speed, Drive drive){
        this.speed = Math.abs(speed);

		this.drive = drive;
		speedPValue = .005;
		minSpeed = .3 * this.speed / Math.abs(this.speed);
        fullSpeedTime = .12;
    }

    @Override
    public boolean isFinished() {
        return drive.getDistance() < 4;
    }

    @Override
    public void start() {
        drive.resetEncoders();
        while(Math.abs(drive.getDistance()) > 1){
            drive.resetEncoders();
            DriverStation.reportWarning("broke", false);
            }
            drive.zeroGyro();
        fullStartTime = Timer.getFPGATimestamp();
    }

    @Override
    public void update(){
        drive.autonTankDrive(speed,speed);
    }

    public void end() {
        drive.stopDrive();
        DriverStation.reportWarning("done", false);
    }
}